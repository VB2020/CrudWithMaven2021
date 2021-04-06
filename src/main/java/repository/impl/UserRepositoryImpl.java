package repository.impl;




import IoUtils.IoUtils;
import model.User;
import repository.UserRepository;
import view.UserView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserRepositoryImpl implements UserRepository {
    File fileName = new File("./src/main/java/resource/users.json");
    @Override
    public User getById(Integer id) throws FileNotFoundException {
        List<User> users = getAllInternal();
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(new User());
    }

    @Override
    public List<User> getAll() throws FileNotFoundException {
        if (Objects.isNull(IoUtils.readFromFile(fileName))){
            return new ArrayList<>();
        }
        else{
            return IoUtils.readFromFile(fileName);
        }
    }

    @Override
    public void save(User user) throws FileNotFoundException {
        List<User> users = getAllInternal();
        AtomicBoolean flag = new AtomicBoolean(false);
        try{
            users.forEach((user1) -> {
                if (user1.getId() == user.getId()) {
                    user1.setId(user.getId());
                    user1.setFirstName(user.getFirstName());
                    user1.setLastName(user.getLastName());
                    user1.setPosts(user.getPosts());
                    flag.set(true);
                }
            });
            if (!flag.get()){
                users.add(user);
            }
            IoUtils.writeToFile(users, fileName);
        }
        catch (Exception er){
            System.out.println("Id not exist");
        }

    }

    @Override
    public void deleteById(Integer id) throws Exception {
        List<User> users = getAllInternal();
        users.forEach((user) ->
        {
            if (user.getId() == id) {
                user.setLastName(UserView.deleted);
            }
        });
        IoUtils.writeToFile(users, fileName);
    }

    @Override
    public void update(User user) throws FileNotFoundException {
        List<User> users = getAllInternal();
         try{
            users.forEach((user1) -> {
                if (user1.getId() == user1.getId()) {
                    user1.setId(user1.getId());
                    user1.setFirstName(user1.getFirstName());
                    user1.setLastName(user1.getLastName());
                    user1.setPosts(user1.getPosts());
                }
            });
            users.add(user);
            IoUtils.writeToFile(users, fileName);
        }
        catch (Exception er){
            System.out.println("Id not exist");
        }
    }

    public List<User> getAllInternal() throws FileNotFoundException {
        if (Objects.isNull(IoUtils.readFromFile(fileName))){
            return new ArrayList<>();
        }
        else{
            return IoUtils.readFromFile(fileName);
        }
    }
}
