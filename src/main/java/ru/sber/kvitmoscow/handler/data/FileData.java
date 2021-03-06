package ru.sber.kvitmoscow.handler.data;

import ru.sber.kvitmoscow.handler.model.FileRow;

import java.util.List;

public interface FileData {
    List<FileRow> handle() throws Exception;

    List<String> getColumnNameListFromFile() throws Exception;
}
