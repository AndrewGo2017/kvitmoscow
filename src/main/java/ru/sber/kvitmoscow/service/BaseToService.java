package ru.sber.kvitmoscow.service;

import ru.sber.kvitmoscow.model.BaseEntity;
import ru.sber.kvitmoscow.to.BaseTo;

import javax.security.auth.message.AuthException;

public interface BaseToService<Entity extends BaseEntity, To extends BaseTo> extends BaseService<Entity> {
    Entity save(To entity) ;
}
