package com.example.projektjava.repository;

public interface CRUDRepository<T> {

    int create(T entity);
    T read(int index);
    void update(int index, T entity);
    void delete(int index);

}
