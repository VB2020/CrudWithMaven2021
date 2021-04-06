package repository;
import java.io.FileNotFoundException;
import java.util.List;

public interface GenericRepository<T, ID>
{
    T getById(ID id) throws FileNotFoundException;
    List<T> getAll() throws FileNotFoundException;
    void save(T t) throws FileNotFoundException;
    void deleteById(ID id) throws Exception;
    void update(T t) throws Exception;
}
