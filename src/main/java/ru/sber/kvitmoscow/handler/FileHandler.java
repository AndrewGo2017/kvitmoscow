package ru.sber.kvitmoscow.handler;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.sber.kvitmoscow.Authorization;
import ru.sber.kvitmoscow.handler.conversion.ConversionRegisterInHandler;
import ru.sber.kvitmoscow.handler.data.ExcelFileData;
import ru.sber.kvitmoscow.handler.data.FileData;
import ru.sber.kvitmoscow.handler.data.TextFileData;
import ru.sber.kvitmoscow.handler.bill.pdf.PdfHandler;
import ru.sber.kvitmoscow.handler.model.*;
import ru.sber.kvitmoscow.model.UserSetting;
import ru.sber.kvitmoscow.service.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class FileHandler {
    @Autowired
    private UserSettingService userSettingService;

    @Autowired
    private FileMainFieldService fileMainFieldService;

    @Autowired
    private FileUniqueFieldService fileUniqueFieldService;

    @Autowired
    private FileSumFieldService fileSumFieldService;

    @Autowired
        private FileCounterFieldService fileCounterFieldService;

        @Autowired
        private FileSumAddFieldService fileSumAddFieldService;

        private String lastMask;

        public ByteArrayOutputStream handle(MultipartFile file, int userSettingId, int functionId) throws Exception {
            //get user columns from settings
            List<String> columnNameListFromSettings = new ArrayList<>();

            MainColEntity mainColumns = new MainColEntity();
            List<UniqueColEntity> uniqueColumnList = new ArrayList<>();
            List<SumColEntity> sumColumnList = new ArrayList<>();
            List<CounterColEntity> counterColumnList = new ArrayList<>();
            List<SumAddColEntity> sumAddColumnList = new ArrayList<>();

            fileMainFieldService.getAllByUserSettingId(userSettingId).forEach(f -> {
                columnNameListFromSettings.add(f.getLs());
                columnNameListFromSettings.add(f.getFio());
                columnNameListFromSettings.add(f.getAdr());
                columnNameListFromSettings.add(f.getPeriod());
                columnNameListFromSettings.add(f.getSum());

                mainColumns.setLs(f.getLs());
                mainColumns.setAdr(f.getAdr());
                mainColumns.setFio(f.getFio());
                mainColumns.setPeriod(f.getPeriod());
                mainColumns.setSum(f.getSum());

                mainColumns.setLsName(f.getLsName());
                mainColumns.setAdrName(f.getAdrName());
                mainColumns.setFioName(f.getFioName());
                mainColumns.setPeriodName(f.getPeriodName());
                mainColumns.setSumName(f.getSumName());
            });

            fileUniqueFieldService.getAllByUserSettingId(userSettingId).forEach(f -> {
                columnNameListFromSettings.add(f.getValue());

                uniqueColumnList.add(new UniqueColEntity(f.getName(), f.getValue()));
            });

            fileSumFieldService.getAllByUserSettingId(userSettingId).forEach(f -> {
                columnNameListFromSettings.add(f.getName());
                columnNameListFromSettings.add(f.getValue());

                sumColumnList.add(new SumColEntity(f.getName(), f.getValue()));
            });

            fileSumAddFieldService.getAllByUserSettingId(userSettingId).forEach(f -> {
            IntStream.range(1, sumColumnList.size() + 1)
                    .forEach(index -> {
                        columnNameListFromSettings.add(f.getValue() + index);
                        sumAddColumnList.add(new SumAddColEntity(f.getName(), f.getValue() + index, index, f.getIsBold()));
                    });
        });


        fileCounterFieldService.getAllByUserSettingId(userSettingId).forEach(f -> {
            columnNameListFromSettings.add(f.getName());
            columnNameListFromSettings.add(f.getValue());

            counterColumnList.add(new CounterColEntity(f.getName(), f.getValue()));
        });

        List<String> columnNameListFromSettingsWithNoEmpty = columnNameListFromSettings.stream().filter(e -> !e.isEmpty()).collect(Collectors.toList());

        InputStream inputStream = file.getInputStream();

        String fileName = Objects.requireNonNull(file.getOriginalFilename()).toUpperCase();
        String extension = FilenameUtils.getExtension(fileName);


        FileData fileData = null;
        if (extension.equals("XLS") || extension.equals("XLSX")){
            fileData = new ExcelFileData(inputStream, extension, columnNameListFromSettingsWithNoEmpty);
        } else if (extension.equals("TXT") || extension.equals("CSV")) {
            fileData = new TextFileData(inputStream, columnNameListFromSettingsWithNoEmpty);
        }

        //get pay reqs ???????
        List<UserSetting> userSettings = userSettingService.getAllByUserId(Authorization.id());
        UserSetting payReqs = userSettings.get(0);

        List<FileRow> fileRowList = fileData.handle();
        List<String> columnNameListFromFile = fileData.getColumnNameListFromFile();
        ByteArrayOutputStream baos = null;
        if (functionId == 1){
            PdfHandler pdfHandler = new PdfHandler();
            baos = pdfHandler.handle(fileRowList, payReqs, mainColumns, sumColumnList, sumAddColumnList, uniqueColumnList,  counterColumnList, columnNameListFromFile);
            lastMask = pdfHandler.getPeriodFromLastLine();

        } else  {
            baos = new ConversionRegisterInHandler().handle(fileRowList, mainColumns, columnNameListFromFile);
        }

        return baos;
    }

    public String getLastMask() {
        return lastMask;
    }
}
