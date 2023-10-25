package com.deltaVelorum.coursify.chapitre.services;

import java.util.List;

public interface IService<T> {
    void createTableIfNotExist();
    void add(T instance);
    void update(T instance);
    void delete(int id);
    T getOne(int id);
    List<T> getAll();
}
