package ee.bredbrains.phonebook.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class BaseService<R extends JpaRepository<T, Long>, T> {
    protected final R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }

    public T save(T instance) {
        return repository.save(instance);
    }
}
