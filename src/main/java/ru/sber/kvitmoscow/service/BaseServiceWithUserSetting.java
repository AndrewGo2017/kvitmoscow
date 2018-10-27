package ru.sber.kvitmoscow.service;

import ru.sber.kvitmoscow.model.BaseEntity;

import java.util.List;

public interface BaseServiceWithUserSetting <Entity extends BaseEntity> {
    List<Entity> getAllByUserSettingId(int userSettingId);
}
