package controller;


import IoUtils.PostIO;
import IoUtils.RegionIO;
import IoUtils.UserIO;
import model.Post;
import model.Region;
import model.Role;
import model.User;
import repository.impl.PostRepositoryImpl;
import repository.impl.RegionRepositoryImpl;
import repository.impl.UserRepositoryImpl;
import view.ForConsole;
import view.PostView;
import view.RegionView;
import view.UserView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserController {
    public static void startM() throws Exception {
        Scanner sc = new Scanner(System.in);
        boolean isExit = false;
        do {
            UserView.show();
            String resp = sc.next();
            switch (resp) {
                case "1":
                    create();
                    break;
                case "2":
                    edit();
                    break;
                case "3":
                    delete();
                    break;
                case "4":
                    show();
                    break;
                case "5":
                    addListElementsForUser();
                    break;
                case "6":
                    isExit = true;
                    MainController.startM();
                    break;
                case "7":
                    isExit = true;
                    break;
                default:
                    System.out.println(ForConsole.ERROR_INPUT.getMessage());
                    break;
            }
        } while (!isExit);
        sc.close();
    }

    public static void create() throws Exception {
        Scanner sc = new Scanner(System.in);
        UserRepositoryImpl wR = new UserRepositoryImpl();
        PostRepositoryImpl pR = new PostRepositoryImpl();
        boolean isExit = false;
        UserView.showFirstName();
        String fName = sc.next();
        int id_ = UserIO.getMaxId(wR.getAll());
        User newUser = new User(id_ + 1, fName, "");
        UserView.showLastName();
        String lName = sc.next();
        newUser.setLastName(lName);
        newUser.setPosts(new ArrayList<>());
        try {
            do {
                UserView.showCreateList();
                int ca = sc.nextInt();
                switch (ca){
                    case 1:
                        try {
                            List<Post> posts = pR.getAll();
                            PostView.showPostsList(PostIO.delPosts(posts, newUser.getPosts()));
                            PostView.editId();
                            int pId = sc.nextInt();
                            int maxId = PostIO.getMaxId(posts);
                            if (pId > 0 && pId <= maxId) {
                                Post post = pR.getById(pId);
                                if (!post.getRole().equals(Role.MODERATOR) &&
                                        !PostIO.containPost(newUser.getPosts(), post)) {
                                    newUser.getPosts().add(post);
                                }
                                else {
                                    System.out.println("Id not exist !!!!");
                                }
                            }
                            else {
                                System.out.println("Id not exist !!!!");
                            }
                        } catch (NullPointerException e) {
                            System.out.println("Id not exist !!!!");
                        }
                        break;
                    case 2:
                        isExit = true;
                        break;
                }
            }while (!isExit);
        } catch (InputMismatchException nfe) {
            System.out.println("It's not a number !!!");
        }
        //Если последний ID был удален то он остается в списке с пометкой dell
        //в этом случае когда добавляем новый экземпляр списка нам надо удалить
        //экземпляр с пометкой dell, здесь это и делается + часный случай когда
        //в списке пусто до добавления (maxID = 0 )
        if (id_ != 0){
            if (wR.getById(id_).getLastName().equals(UserView.deleted)){
                wR.save(newUser);
                wR.getById(id_);
            }
            else {
                wR.save(newUser);
            }
        }
        else {
            wR.save(newUser);
        }
    }

    public static void edit() throws Exception {
        Scanner sc = new Scanner(System.in);
        UserRepositoryImpl wR = new UserRepositoryImpl();
        boolean isExit = false;
        List<User> Users = wR.getAll();
        UserView.showUsersList(Users);
        int demonId = UserIO.getMaxId(Users);
        try {
            do {
                UserView.editId();
                int id = sc.nextInt();
                User User = wR.getById(id);
                if (id > 0 && demonId >= id && !User.getLastName().equals(UserView.deleted)){
                    UserView.showFirstName();
                    String fName = sc.next();
                    User.setFirstName(fName);
                    UserView.showLastName();
                    String lName = sc.next();
                    User.setLastName(lName);
                    wR.update(User);
                    isExit = true;
                }
                else {
                    System.out.println("Id not exist !!!!");
                }
            }
            while (!isExit);
        }
        catch (InputMismatchException nfe) {
            System.out.println("It's not a number !!!");
        }
    }

    public static void delete() throws Exception {
        Scanner sc = new Scanner(System.in);
        UserRepositoryImpl wR = new UserRepositoryImpl();
        boolean isExit = false;
        List<User> Users = wR.getAll();
        UserView.showUsersList(Users);
        try {
            do {
                UserView.editId();
                try{
                    int id = sc.nextInt();
                    int maxId = UserIO.getMaxId(Users);
                    User User = wR.getById(id);
                    if (id > 0 && id <= maxId && !User.getLastName().equals(RegionView.deleted)) {
                        wR.deleteById(id);
                        isExit = true;
                    }
                    else {
                        System.out.println("Id not exist !!!!");
                    }
                } catch (NullPointerException e) {
                    System.out.println("Id not exist !!!!");
                }
            }while (!isExit);
        }
        catch (InputMismatchException nfe){
            System.out.println("It's not a number !!!");
        }
    }

    public static void show() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        UserRepositoryImpl wR = new UserRepositoryImpl();
        PostRepositoryImpl pR = new PostRepositoryImpl();
        RegionRepositoryImpl lR = new RegionRepositoryImpl();
        boolean isExit = false;
        List<User> Users = wR.getAll();
        List<Post> posts = pR.getAll();
        List<Region> Regions = lR.getAll();
        UserView.showUsersList(Users);
        int demonId = UserIO.getMaxId(Users);
        try{
            do {
                UserView.editId();
                int id = sc.nextInt();
                User User = wR.getById(id);
                if (id > 0 && demonId >= id && !User.getLastName().equals(UserView.deleted)
                        && UserIO.containUser(Users, User)){
                    if (!User.getPosts().isEmpty()) {
                        UserView.showUser(User);
                        //Проверка что посты у писателя содержатся в общем списке постов (не удалены)
                        List<Post> tmpPostList = PostIO.notDelPosts(posts, User.getPosts());
                        tmpPostList.forEach((a) -> {
                            PostView.showPost(a);
                            RegionView.showRegionsList(RegionIO.notDelRegion(Regions, a.getRegions()));
                        });
                        isExit = true;
                    }
                    else{
                        UserView.showUser(User);
                        UserView.listEmpty();
                    }
                }
                else {
                    System.out.println("Id not exist !!!!");
                }
            }
            while (!isExit);
        }
        catch (InputMismatchException nfe) {
            System.out.println("It's not a number !!!");
        }
    }

    public static void addListElementsForUser() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        PostRepositoryImpl pR = new PostRepositoryImpl();
        boolean isExit = false;
        List<User> Users = userRepository.getAll();
        List<Post> posts = pR.getAll();
        UserView.showUsersList(Users);
        int demonId = UserIO.getMaxId(Users);
        int maxId = PostIO.getMaxId(posts);
        try{
            do {
                UserView.editId();
                int id = sc.nextInt();
                User User = userRepository.getById(id);
                if (id > 0 && demonId >= id && !User.getLastName().equals(UserView.deleted)
                        && UserIO.containUser(Users, User)) {
                    do {
                        if (!User.getPosts().isEmpty()) {
                            UserView.showUser(User);
                            System.out.println(ForConsole.BORDER.getMessage());
                            System.out.println("This User contains:");
                            PostView.showPostsList(PostIO.notDelPosts(posts, User.getPosts()));
                            System.out.println(ForConsole.BORDER.getMessage());
                            System.out.println("You can add this posts:");
                            PostView.showPostsList(PostIO.delPosts(posts, User.getPosts()));
                        } else {
                            UserView.showUser(User);
                            UserView.listEmpty();
                            System.out.println("You can add this posts:");
                            PostView.showPostsList(posts);
                        }

                        PostView.showCancel();
                        PostView.editId();
                        int postId = sc.nextInt();
                        Post newPost = pR.getById(postId);
                        if (postId > 0 && postId <= maxId && !PostIO.containPost(User.getPosts(), newPost)) {
                            UserIO.addPost(User.getPosts(), newPost);
                        } else if (postId == 0) {
                            userRepository.update(User);
                            isExit = true;
                        } else {
                            System.out.println("Id not exist !!!!");
                        }
                    }while (!isExit);
                }
                else {
                    System.out.println("Id not exist !!!!");
                }
            }
            while (!isExit);
        }
        catch (InputMismatchException nfe) {
            System.out.println("It's not a number !!!");
        }
    }
}
