package ru.sber.kvitmoscow.service;

import ru.sber.kvitmoscow.model.FileUniqueField;
import ru.sber.kvitmoscow.to.FileUniqueFieldTo;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface FileUniqueFieldService extends BaseToService<FileUniqueField, FileUniqueFieldTo>, BaseServiceWithUserSetting<FileUniqueField> {
}
