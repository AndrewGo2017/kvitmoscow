package ru.sber.kvitmoscow.example;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sber.kvitmoscow.service.UserSettingService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class ExampleHandler {
    @Autowired
    private UserSettingService userSettingService;

    public ByteArrayOutputStream handle(int userSettingId) throws IOException {
        int templateId = userSettingService.get(userSettingId).getTemplate().getId();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (templateId == 1){
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Лист1");
            Row row = sheet.createRow(0);

            row.createCell(0).setCellValue("LS");
            row.createCell(1).setCellValue("ADR");
            row.createCell(2).setCellValue("FIO");
            row.createCell(3).setCellValue("PERIOD");
            row.createCell(4).setCellValue("SUM");

            workbook.write(baos);
        }

        return baos;
    }
}
