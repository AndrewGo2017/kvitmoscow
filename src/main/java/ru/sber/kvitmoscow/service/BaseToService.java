package ru.sber.kvitmoscow.service;

import ru.sber.kvitmoscow.model.BaseEntity;
import ru.sber.kvitmoscow.to.BaseTo;

public interface BaseToService<T1 extends BaseEntity, T2 extends BaseTo> extends BaseService<T1> {
    T1 save(T2 entity);
}
