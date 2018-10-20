package ru.sber.kvitmoscow.handler.file.data;

import ru.sber.kvitmoscow.handler.file.model.FileRow;

import java.util.List;

public interface FileData {
    List<FileRow> handle() throws Exception;

    List<String> getColumnNameListFromFile() throws Exception;
}
