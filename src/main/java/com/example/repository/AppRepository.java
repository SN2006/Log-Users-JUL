package com.example.repository;

import java.util.List;
import java.util.Optional;

public interface AppRepository<T> {
    String create(T t);
    Optional<List<T>> read();
    Optional<T> readById(Long id);
    String update(T t);
    String delete(Long id);
}
