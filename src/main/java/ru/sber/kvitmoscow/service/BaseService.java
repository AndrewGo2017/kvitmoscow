package ru.sber.kvitmoscow.service;

import ru.sber.kvitmoscow.model.BaseEntity;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    T save(T entity);

    boolean delete (int id);

    T get (int id);

    List<T> getAll();
}
