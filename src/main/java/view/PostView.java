package view;

import model.Post;
import model.Role;

import java.util.List;

public class PostView {
    public static final String deleted = "This post was deleted";
    public static void show(){
        System.out.println(ForConsole.BORDER.getMessage());
        String mainMessage = "Choose an action with posts:\n" +
                " 1. Create\n" +
                " 2. Edit content\n" +
                " 3. Delete\n" +
                " 4. Show post by id\n" +
                " 5. Create or edit regions list for post by ID\n" +
                " 6. Main menu\n" +
                " 7. Exit";
        System.out.println(mainMessage);
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void showAddRegion(){
        System.out.println(ForConsole.BORDER.getMessage());
        String mainMessage = "Add region in regions list for post ?\n" +
                " 1. Yes\n" +
                " 2. No" ;
        System.out.println(mainMessage);
        System.out.println(ForConsole.BORDER.getMessage());

    }

    public static void create(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter post content: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void idNotEx(){
        System.out.println("This Id not exist");
    }

    public static void editId(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter post id: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void editName(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Enter new post content: ");
        System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void showCancel(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Press '0' for exit");
        //System.out.println(ForConsole.BORDER.getMessage());
    }

    public static void showPostsList(List<Post> posts){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Posts list:");
        System.out.println(ForConsole.BORDER.getMessage());
        posts.stream().filter((post) -> !post.getRole().equals(Role.ADMIN)).forEach(
                (post) -> System.out.println("Id: " + post.getId() + " | Content: " + post.getContent() +
                        " | Created: " + post.getCreated() + " | Updated: " + post.getUpdated())
        );
    }

    public static void showPost(Post post){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Post:");
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("Id: " + post.getId() + " | Content: " + post.getContent() +
                " | Created: " + post.getCreated() + " | Updated: " + post.getUpdated());
    }

    public static void listEmpty(){
        System.out.println(ForConsole.BORDER.getMessage());
        System.out.println("region list for this post is empty");
    }
}
