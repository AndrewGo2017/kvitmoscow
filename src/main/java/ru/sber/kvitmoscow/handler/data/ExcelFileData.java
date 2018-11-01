package ru.sber.kvitmoscow.handler.data;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.sber.kvitmoscow.handler.model.FileRow;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ExcelFileData implements FileData {
    private List<String> columnNameListFromSettingsWithNoEmpty;

    private Sheet sheet;

    private List<String> columnNameListFromFile;

    public ExcelFileData(InputStream inputStream, String extension, List<String> columnNameListFromSettingsWithNoEmpty) throws Exception {
        this.columnNameListFromSettingsWithNoEmpty = columnNameListFromSettingsWithNoEmpty;

        Workbook workbook = null;
        if (extension.equals("XLS")) {
            workbook = new HSSFWorkbook(inputStream);
            this.sheet = workbook.getSheetAt(0);
        } else if (extension.equals("XLSX")) {
            workbook = new XSSFWorkbook(inputStream);
            this.sheet = workbook.getSheetAt(0);
        } else {
            throw new Exception("что-то пошло не так ...");
        }
    }

    public List<FileRow> handle() throws Exception {
        DataFormatter formatter = new DataFormatter();

        checkStructure(sheet, columnNameListFromSettingsWithNoEmpty);

        List<FileRow> fileRowList = new ArrayList<>();
        int rowCounter = 0;
        for (Row row : sheet){
            String firstColValue = formatter.formatCellValue(row.getCell(0)).trim();
            if (firstColValue.isEmpty()) break;

            if (rowCounter == 0) {
                rowCounter++;
                continue;
            }

            FileRow fileRow = new FileRow();
            fileRow.setRowIndex(rowCounter);
            List<String> cellDataList = new ArrayList<>();
            for (Cell cell : row){
                cellDataList.add(formatter.formatCellValue(cell).isEmpty() ? "-" : formatter.formatCellValue(cell));
            }
            fileRow.setRowData(cellDataList);
            fileRowList.add(fileRow);
        }

        return fileRowList;
    }

    private void checkStructure(Sheet sheet, List<String> columnNameListFromSettingsWithNoEmpty) throws Exception {
        List<String> noColumnList = new ArrayList<>();
        columnNameListFromSettingsWithNoEmpty.forEach(s -> {
            if (!getColumnNameListFromFile().contains(s)) {
                noColumnList.add(s);
            }
        });

        if (noColumnList.size() > 0) {
            throw new Exception("не найдены поля : " + noColumnList.toString());
        }
    }

    @Override
    public List<String> getColumnNameListFromFile() {
        if (columnNameListFromFile == null) {
            int lastColumnNum = sheet.getRow(0).getLastCellNum();
            columnNameListFromFile = new ArrayList<>();
            for (int i = 0; i < lastColumnNum; i++) {
                columnNameListFromFile.add(sheet.getRow(0).getCell(i).getStringCellValue());
            }
        }
        return columnNameListFromFile;
    }
}
