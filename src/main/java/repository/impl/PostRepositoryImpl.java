package repository.impl;


import IoUtils.IoUtils;
import model.Post;
import model.Role;
import repository.PostRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class PostRepositoryImpl implements PostRepository {
    File fileName = new File("./src/main/java/resource/posts.json");

    @Override
    public Post getById(Integer id) throws FileNotFoundException {
        List<Post> posts = getAllInternal();
        return posts.stream().filter(any_post -> any_post.getId() == id).findFirst().orElse(new Post());
    }

    @Override
    public List<Post> getAll() throws FileNotFoundException {
        if (Objects.isNull(IoUtils.readFromFile(fileName))){
            return new ArrayList<>();
        }
        else{
            return IoUtils.readFromFile(fileName);
        }
    }

    @Override
    public void save(Post post) throws FileNotFoundException {
        List<Post> posts = getAllInternal();
        AtomicBoolean flag = new AtomicBoolean(false);
        try{
            posts.forEach((post1) -> {
                if (post1.getId() == post.getId()) {
                    post1.setId(post.getId());
                    post1.setContent(post.getContent());
                    post1.setUpdated(new Date().toString());
                    post1.setRegions(post.getRegions());
                    post1.setRole(Role.USER);
                    flag.set(true);
                }
            });
            if (!flag.get()){
                posts.add(post);
            }
            IoUtils.writeToFile(posts, fileName);
        }
        catch (Exception er){
            System.out.println("Id not exist");
        }

    }

    @Override
    public void deleteById(Integer id) throws Exception {
        List<Post> posts = getAllInternal();
        posts.forEach((post) ->
        {
            if (post.getId() == id) {
                post.setUpdated(new Date().toString());
                post.setRole(Role.USER);
            }
        });
        IoUtils.writeToFile(posts, fileName);
    }

    @Override
    public void update(Post post) throws FileNotFoundException {
        List<Post> posts = getAllInternal();
         try{
            posts.forEach((post1) -> {
                if (post1.getId() == post.getId()) {
                    post1.setId(post.getId());
                    post1.setContent(post.getContent());
                    post1.setUpdated(new Date().toString());
                    post1.setRegions(post.getRegions());
                    post1.setRole(Role.ADMIN);
                }
            });
                posts.add(post);
            IoUtils.writeToFile(posts, fileName);
        }
        catch (Exception er){
            System.out.println("Id not exist");
        }

    }

    private List<Post> getAllInternal() throws FileNotFoundException {
        if (Objects.isNull(IoUtils.readFromFile(fileName))){
            return new ArrayList<>();
        }
        else{
            return IoUtils.readFromFile(fileName);
        }
    }


}

