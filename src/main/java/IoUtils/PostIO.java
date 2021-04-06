package IoUtils;


import model.Post;
import model.Role;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PostIO {
    public static int getMaxId(List<Post> posts){
        int maxId;
        if(posts.isEmpty()){
            maxId = 0;
        }
        else {
            posts.sort(Comparator.comparing(Post::getId));
            maxId = posts.get(posts.size() - 1).getId();;
        }
        return maxId;
    }

    public static boolean containPost(List<Post> postList, Post post){
        AtomicBoolean flag = new AtomicBoolean(false);
        postList.forEach((any_post) -> {
            if (any_post.getId() == post.getId()){
                flag.set(true);
            }
        });
        return flag.get();
    }

    public static List<Post> delPosts(List<Post> posts1, List<Post> posts2){
        List<Post> result_list = new ArrayList<>();
        posts1.stream().filter((any_post) -> !containPost(posts2, any_post)).filter((any_post) -> !any_post.getRole().equals(Role.ADMIN))
                .forEach(result_list::add);
        return result_list;
    }

    public static List<Post> notDelPosts(List<Post> l1, List<Post> l2){
        List<Post> res = new ArrayList<>();
        l1.stream().filter((any_post) -> containPost(l2, any_post)).filter((any_post) -> !any_post.getRole().equals(Role.MODERATOR))
                .forEach(res::add);
        return res;
    }
}
