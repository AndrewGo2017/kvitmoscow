package ru.sber.kvitmoscow.service;

import ru.sber.kvitmoscow.model.FileCounterAddField;
import ru.sber.kvitmoscow.model.FileSumAddField;
import ru.sber.kvitmoscow.to.FileCounterAddFieldTo;

public interface FileCounterAddFieldService extends BaseToService<FileCounterAddField, FileCounterAddFieldTo>, BaseServiceWithUserSetting<FileCounterAddField> {
}
