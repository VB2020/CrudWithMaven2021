package view;

import model.User;

import java.util.List;

public class UserView {
    public static final String deleted = "This user was deleted";

    public static void show(){
        System.out.println(ForConsole.BORDER.getMessage());
        String mainMessage = "Choose an action with user:\n" +
                " 1. Create\n" +
                " 2. Edit name\n" +
                " 3. Delete\n" +
                " 4. Show user by id\n" +
                " 5. Create or edit posts list for user by ID\n" +
                " 6. Main menu\n" +
                " 7. Exit";
        System.out.println(mainMessage);
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void showFirstName(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter first name: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void showLastName(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter last name: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void showCreateList(){
        System.out.println(ForConsole.BORDER.getMessage());
        String mainMessage = "Add post in posts list for user ?\n" +
                " 1. Yes\n" +
                " 2. No" ;
        System.out.println(mainMessage);
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void editId(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter user id: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void showUsersList(List<User> users){
        System.out.println(ForConsole.BORDER.getMessage());
        users.stream().filter((user) -> !user.getLastName().equals(UserView.deleted)).forEach(
                (a) -> System.out.println("Id: " + a.getId() + " | Firstname: " + a.getFirstName() +
                        " | Lastname: " + a.getLastName() )
        );
    }

    public static void showUser(User a){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("User:");
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Id: " + a.getId() + " | Firstname: " + a.getFirstName() +
                        " | Lastname: " + a.getLastName());
    }

    public static void listEmpty(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Posts list for this user is empty");
    }






}
