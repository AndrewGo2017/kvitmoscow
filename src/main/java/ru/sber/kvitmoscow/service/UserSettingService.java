package ru.sber.kvitmoscow.service;

import ru.sber.kvitmoscow.model.UserSetting;
import ru.sber.kvitmoscow.to.UserSettingTo;

import java.util.List;

public interface UserSettingService extends BaseToService<UserSetting, UserSettingTo> {
    List<UserSetting> getAllByUserId(int userId);

    void fillDictionariesWithDefaultData(int userSettingId, int templateId);
}
