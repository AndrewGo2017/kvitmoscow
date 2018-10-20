package ru.sber.kvitmoscow.handler.file.data;

import ru.sber.kvitmoscow.handler.file.model.FileRow;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextFileData implements FileData {
    private int maxIndex;

    private List<String> columnNameListFromFile;

    private static String DELIMITER = ";";

    InputStream inputStream;

    public TextFileData(InputStream inputStream, List<String> columnNameListFromSettingsWithNoEmpty) throws Exception {
        this.inputStream = inputStream;
        this.maxIndex = columnNameListFromSettingsWithNoEmpty.stream().mapToInt(Integer::parseInt).max().orElse(0);
    }

    private InputStream cloneInputStream() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) > -1 ) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        this.inputStream =  new ByteArrayInputStream(baos.toByteArray());
        return new ByteArrayInputStream(baos.toByteArray());
    }

    @Override
    public List<FileRow> handle() throws Exception {
        List<FileRow> fileRowList = new ArrayList<>();
        InputStream newInputStream = cloneInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(newInputStream))) {
            String line;

            int rowCounter = 0;
            while ((line = reader.readLine()) != null) {
                FileRow fileRow = new FileRow();
                fileRow.setRowIndex(rowCounter);
                List<String> cellDataList = new ArrayList<>(Arrays.asList(line.split(DELIMITER)));
                fileRow.setRowData(cellDataList);
                fileRowList.add(fileRow);
                rowCounter++;
            }
        }
        return fileRowList;
    }

    @Override
    public List<String> getColumnNameListFromFile() throws Exception {
        if (columnNameListFromFile == null) {
            InputStream newInputStream = cloneInputStream();
            columnNameListFromFile = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(newInputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    int lastColumnNum = line.split(DELIMITER).length;
                    for (int i = 0; i < lastColumnNum; i++) {
                        columnNameListFromFile.add(Integer.toString(i));
                    }
                    break;
                }
            }
        }
        return columnNameListFromFile;
    }

    private void checkStructure(List<String> columnNameListFromSettingsWithNoEmpty) throws Exception {
        List<Integer> noColumnList = new ArrayList<>();
        InputStream newInputStream = cloneInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(newInputStream))) {
            String line;
            int lastColumnNum = 0;
            int rowCounter = 0;
            while ((line = reader.readLine()) != null) {
                if (maxIndex > line.split(DELIMITER).length - 1) {
                    noColumnList.add(rowCounter + 1);
                }
                rowCounter++;
            }
        }
        if (noColumnList.size() > 0) {
            throw new Exception("неверное количество полей в строках : " + noColumnList.toString());
        }
    }
}
