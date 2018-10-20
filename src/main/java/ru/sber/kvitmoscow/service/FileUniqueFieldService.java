package ru.sber.kvitmoscow.service;

import ru.sber.kvitmoscow.model.FileUniqueField;
import ru.sber.kvitmoscow.to.FileUniqueFieldTo;

import java.util.List;

public interface FileUniqueFieldService extends BaseToService<FileUniqueField, FileUniqueFieldTo> {
    List<FileUniqueField> getAllByUserSettingId(int userSettingId);
}
