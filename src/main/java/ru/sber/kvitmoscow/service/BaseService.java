package ru.sber.kvitmoscow.service;

import ru.sber.kvitmoscow.model.BaseEntity;

import java.util.List;

public interface BaseService<Entity extends BaseEntity> {
    Entity save(Entity entity);

    boolean delete (int id);

    Entity get (int id);

    List<Entity> getAll();
}
