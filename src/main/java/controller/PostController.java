package controller;
import IoUtils.PostIO;
import IoUtils.RegionIO;
import model.Post;
import model.Region;
import model.Role;
import repository.impl.PostRepositoryImpl;
import repository.impl.RegionRepositoryImpl;
import view.ForConsole;
import view.PostView;
import view.RegionView;

import java.io.FileNotFoundException;
import java.util.*;

public class PostController {
    public static void startM() throws Exception {
        Scanner sc = new Scanner(System.in);
        boolean isExit = false;
        do {
            PostView.show();
            String resp = sc.next();
            switch (resp)
            {
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
                    addListElementsForPost();
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
        RegionRepositoryImpl lR = new RegionRepositoryImpl();
        PostRepositoryImpl pR = new PostRepositoryImpl();
        boolean isExit = false;
        PostView.create();
        String name = sc.next();
        int demonId = PostIO.getMaxId(pR.getAll());
        Post newPost = new Post(demonId + 1, name);
        newPost.setRegions(new ArrayList<>());
        try {
            do {
                PostView.showAddRegion();
                int id = sc.nextInt();
                switch (id){
                    case 1:
                        try {
                            List<Region> regions = lR.getAll();
                            RegionView.showRegionsList(RegionIO.delRegion(regions, newPost.getRegions()));
                            RegionView.editId();
                            int RegionId = sc.nextInt();
                            int maxId = RegionIO.getMaxId(regions);
                            if (RegionId > 0 && RegionId <= maxId) {
                                Region Region = lR.getById(RegionId);
                                if (!Region.getName().equals(RegionView.deleted) &&
                                        !RegionIO.containRegion(newPost.getRegions(), Region)) {
                                    newPost.getRegions().add(Region);
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
        newPost.setCreated(new Date().toString());
        newPost.setUpdated(newPost.getCreated());
        newPost.setRole(Role.ADMIN);
        pR.save(newPost);
    }

    public static void edit() throws Exception {
        Scanner sc = new Scanner(System.in);
        PostRepositoryImpl pR = new PostRepositoryImpl();
        boolean isExit = false;
        List<Post> posts = pR.getAll();
        PostView.showPostsList(posts);
        int demonId = PostIO.getMaxId(posts);
        try {
            do {
                PostView.editId();
                int id = sc.nextInt();
                Post post = pR.getById(id);
                if (id > 0 && demonId >= id && !post.getRole().equals(Role.MODERATOR)){
                    PostView.editName();
                    String content = sc.next();
                    post.setContent(content);
                    pR.update(post);
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
        PostRepositoryImpl pR = new PostRepositoryImpl();
        boolean isExit = false;
        List<Post> posts = pR.getAll();
        PostView.showPostsList(posts);
        int demonId = PostIO.getMaxId(posts);
        try {
            do {
                PostView.editId();
                int id = sc.nextInt();
                Post post = pR.getById(id);
                if (id > 0 && demonId >= id && !post.getRole().equals(Role.ADMIN)){
                    pR.deleteById(id);
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

    public static void show() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        PostRepositoryImpl pR = new PostRepositoryImpl();
        RegionRepositoryImpl lR = new RegionRepositoryImpl();
        boolean isExit = false;
        List<Post> posts = pR.getAll();
        PostView.showPostsList(posts);
        int demonId = PostIO.getMaxId(posts);
        try{
            do {
                PostView.editId();
                int id = sc.nextInt();
                Post post = pR.getById(id);
                if (id > 0 && demonId >= id && !post.getRole().equals(Role.MODERATOR)){
                    if (!post.getRegions().isEmpty()) {
                        PostView.showPost(post);
                        RegionView.showRegionsList(RegionIO.notDelRegion(lR.getAll(), post.getRegions()));
                        isExit = true;
                    }
                    else{
                        PostView.showPost(post);
                        PostView.listEmpty();
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

    public static void addListElementsForPost() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        PostRepositoryImpl pR = new PostRepositoryImpl();
        RegionRepositoryImpl lR = new RegionRepositoryImpl();
        boolean isExit = false;
        List<Post> posts = pR.getAll();
        List<Region> Regions = lR.getAll();
        PostView.showPostsList(posts);
        int demonId = PostIO.getMaxId(posts);
        int maxId = RegionIO.getMaxId(Regions);
        try{
            do {
                PostView.editId();
                int id = sc.nextInt();
                Post post = pR.getById(id);
                if (id > 0 && demonId >= id && !post.getRole().equals(Role.MODERATOR)) {
                    do {
                        PostView.showPost(post);
                        System.out.println(ForConsole.BORDER.getMessage());
                        System.out.println("This post contain Regions:");
                        if (!post.getRegions().isEmpty()) {
                            RegionView.showRegionsList(RegionIO.notDelRegion(Regions, post.getRegions()));
                        } else {
                            PostView.listEmpty();
                        }
                        System.out.println(ForConsole.BORDER.getMessage());
                        System.out.println("You can add this Regions:");
                        List<Region> tmpList = new ArrayList<>();
                        Regions.forEach((a) -> {
                            if (!RegionIO.containRegion(post.getRegions(), a)) {
                                tmpList.add(a);
                            }
                        });
                        RegionView.showRegionsList(tmpList);
                        PostView.showCancel();
                        RegionView.editId();
                        int RegionId = sc.nextInt();
                        Region newRegion = lR.getById(RegionId);
                        if (RegionId > 0 && RegionId <= maxId && RegionIO.containRegion(tmpList, newRegion)) {
                            post.getRegions().add(newRegion);
                        } else if (RegionId == 0) {
                            pR.update(post);
                            isExit = true;
                        } else {
                            System.out.println("Id not exist !!!!");
                        }
                    }
                    while (!isExit);
                } else {
                    System.out.println("Id not exist !!!!");
                }
            }while (!isExit);
        }
        catch (InputMismatchException nfe) {
            System.out.println("It's not a number !!!");
        }
    }
}
