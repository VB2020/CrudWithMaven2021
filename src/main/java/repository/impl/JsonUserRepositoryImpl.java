package repository.impl;

import model.User;
import repository.UserRepository;

import java.io.FileNotFoundException;
import java.util.List;

public class JsonUserRepositoryImpl implements UserRepository
{
    @Override
    public User getById(Integer integer) throws FileNotFoundException {
        return null;
    }

    @Override
    public List<User> getAll() throws FileNotFoundException {
        return null;
    }

    @Override
    public void save(User user) throws FileNotFoundException {

    }

    @Override
    public void deleteById(Integer integer) throws Exception {

    }

    @Override
    public void update(User user) throws Exception {

    }
}
