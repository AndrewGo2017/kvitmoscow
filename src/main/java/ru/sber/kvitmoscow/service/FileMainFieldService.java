package ru.sber.kvitmoscow.service;

import ru.sber.kvitmoscow.model.FileMainField;
import ru.sber.kvitmoscow.to.FileMainFieldTo;

import java.util.List;

public interface FileMainFieldService extends BaseToService<FileMainField, FileMainFieldTo> {
    List<FileMainField> getAllByUserSettingId(int userSettingId);

    void deleteAllBySettingId(int settingId);
}
