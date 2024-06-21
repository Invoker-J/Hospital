package uz.pdp.hospital.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BaseService<T,I> {
    Optional<T> findById(I id);
    T save(T t);
    void delete(I i);
    List<T> findAll();
    T update(T t, I i);

}
