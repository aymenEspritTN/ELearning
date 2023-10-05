package com.DeltaVelorum.Coursify.Chapitre;

import java.util.List;

public interface IService<T> {
    public void createTableIfNotExist();
    public void add(T instance);
    public void update(T instance);
    public void delete(int id);
    public T getOne(int id);
    public List<T> getAll();
}
