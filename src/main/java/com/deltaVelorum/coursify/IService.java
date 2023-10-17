package com.deltaVelorum.coursify;

import java.util.List;

public interface IService<T> {
    void createTableIfNotExist();
    void add(T instance);
    void update(T instance);
    void delete(int id);
    T getOne(int id);
    List<T> getAll();
}
