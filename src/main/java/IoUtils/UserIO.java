package IoUtils;
import model.Post;
import model.User;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserIO {
    public static int getMaxId(List<User> users){
        int maxId;
        if(users.isEmpty()){
            maxId = 0;
        }
        else {
            users.sort(Comparator.comparing(User::getId));
            maxId = users.get(users.size() - 1).getId();;
        }
        return maxId;
    }

    public static boolean containUser(List<User> users, User user){
        AtomicBoolean is_contain = new AtomicBoolean(false);
        users.forEach((user1) -> {
            if (user1.getId() == user.getId()){
                is_contain.set(true);
            }
        });
        return is_contain.get();
    }

    public static void addPost(List<Post> posts, Post post){
        Post newPost = new Post(post.getId(), post.getContent(), post.getRole());
        posts.add(newPost);
    }
}
