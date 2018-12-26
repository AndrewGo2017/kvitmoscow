package ru.sber.kvitmoscow.handler;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.sber.kvitmoscow.Authorization;
import ru.sber.kvitmoscow.handler.bill.pdf.template.Template10_2;
import ru.sber.kvitmoscow.handler.bill.pdf.template.Template1_7;
import ru.sber.kvitmoscow.handler.conversion.ConversionRegisterInHandler;
import ru.sber.kvitmoscow.handler.data.ExcelFileData;
import ru.sber.kvitmoscow.handler.data.FileData;
import ru.sber.kvitmoscow.handler.data.FileFormat;
import ru.sber.kvitmoscow.handler.data.TextFileData;
import ru.sber.kvitmoscow.handler.model.*;
import ru.sber.kvitmoscow.model.FileCounterAddField;
import ru.sber.kvitmoscow.model.FileSumAddField;
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
    private static String TEMPLATE_10_2 = "ШАБЛОН 10_2";

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

    @Autowired
    private FileCounterAddFieldService fileCounterAddFieldService;

    private String lastMask;

    public ByteArrayOutputStream handle(MultipartFile file, int userSettingId, int functionId) throws Exception {
        //get user columns from settings
        List<String> columnNameListFromSettings = new ArrayList<>();

        MainColEntity mainColumns = new MainColEntity();
        List<UniqueColEntity> uniqueColumnList = new ArrayList<>();
        List<SumColEntity> sumColumnList = new ArrayList<>();
        List<CounterColEntity> counterColumnList = new ArrayList<>();
        List<SumAddColEntity> sumAddColumnList = new ArrayList<>();
        List<CounterAddColEntity> counterAddColumnList = new ArrayList<>();

        fileMainFieldService.getAllByUserSettingId(userSettingId).forEach(f -> {
            String ls = f.getLs() == null ? "" : f.getLs();
            String fio = f.getFio() == null ? "" : f.getFio();
            String adr = f.getAdr() == null ? "" : f.getAdr();
            String period = f.getPeriod() == null ? "" : f.getPeriod();
            String sum = f.getSum() == null ? "" : f.getSum();
            String kbk = f.getKbk() == null ? "" : f.getKbk();
            String oktmo = f.getOktmo() == null ? "" : f.getOktmo();
            String contract = f.getContract() == null ? "" : f.getContract();
            String purpose = f.getPurpose() == null ? "" : f.getPurpose();

            String lsName = f.getLsName() == null ? "" : f.getLsName();
            String fioName = f.getFioName() == null ? "" : f.getFioName();
            String adrName = f.getAdrName() == null ? "" : f.getAdrName();
            String periodName = f.getPeriodName() == null ? "" : f.getPeriodName();
            String sumName = f.getSumName() == null ? "" : f.getSumName();
            String kbkName = f.getKbkName() == null ? "" : f.getKbkName();
            String oktmoName = f.getOktmoName() == null ? "" : f.getOktmoName();
            String contractName = f.getContractName() == null ? "" : f.getContractName();
            String purposeName = f.getPurposeName() == null ? "" : f.getPurposeName();

            columnNameListFromSettings.add(ls);
            columnNameListFromSettings.add(fio);
            columnNameListFromSettings.add(adr);
            columnNameListFromSettings.add(period);
            columnNameListFromSettings.add(sum);
            columnNameListFromSettings.add(kbk);
            columnNameListFromSettings.add(oktmo);
            columnNameListFromSettings.add(contract);
            columnNameListFromSettings.add(purpose);

            mainColumns.setLs(ls);
            mainColumns.setAdr(adr);
            mainColumns.setFio(fio);
            mainColumns.setPeriod(period);
            mainColumns.setSum(sum);
            mainColumns.setKbk(kbk);
            mainColumns.setOktmo(oktmo);
            mainColumns.setContract(contract);
            mainColumns.setPurpose(purpose);

            mainColumns.setLsName(lsName);
            mainColumns.setAdrName(adrName);
            mainColumns.setFioName(fioName);
            mainColumns.setPeriodName(periodName);
            mainColumns.setSumName(sumName);
            mainColumns.setKbkName(kbkName);
            mainColumns.setOktmoName(oktmoName);
            mainColumns.setContractName(contractName);
            mainColumns.setPurposeName(purposeName);
        });

        fileUniqueFieldService.getAllByUserSettingId(userSettingId).forEach(f -> {
            columnNameListFromSettings.add(f.getValue());

            uniqueColumnList.add(new UniqueColEntity(f.getName(), f.getValue()));
        });

        fileSumFieldService.getAllByUserSettingId(userSettingId).forEach(f -> {
            columnNameListFromSettings.add(f.getName());
            columnNameListFromSettings.add(f.getValue());

            sumColumnList.add(new SumColEntity(f.getName(), f.getValue(), f.getIsBold()));
        });

        fileCounterFieldService.getAllByUserSettingId(userSettingId).forEach(f -> {
            columnNameListFromSettings.add(f.getName());
            columnNameListFromSettings.add(f.getValue());

            counterColumnList.add(new CounterColEntity(f.getName(), f.getValue()));
        });

        List<FileSumAddField> fileSumAddFields = fileSumAddFieldService.getAllByUserSettingId(userSettingId);
        IntStream.range(0, fileSumAddFields.size())
                .forEach(idx -> {
                    IntStream.range(1, sumColumnList.size() + 1)
                            .forEach(index -> {
                                columnNameListFromSettings.add(fileSumAddFields.get(idx).getValue() + index);
                                sumAddColumnList.add(new SumAddColEntity(fileSumAddFields.get(idx).getName(), fileSumAddFields.get(idx).getValue() + index, index, idx + 1, fileSumAddFields.get(idx).getIsBold()));
                            });
                });

        List<FileCounterAddField> fileCounterAddFields = fileCounterAddFieldService.getAllByUserSettingId(userSettingId);
        IntStream.range(0, fileCounterAddFields.size())
                .forEach(idx -> {
                    IntStream.range(1, counterColumnList.size() + 1)
                            .forEach(index -> {
                                columnNameListFromSettings.add(fileCounterAddFields.get(idx).getValue() + index);
                                counterAddColumnList.add(new CounterAddColEntity(fileCounterAddFields.get(idx).getName(), fileCounterAddFields.get(idx).getValue() + index, index, idx + 1));
                            });
                });

        List<String> columnNameListFromSettingsWithNoEmpty = columnNameListFromSettings.stream().filter(e -> !e.isEmpty()).collect(Collectors.toList());

        InputStream inputStream = file.getInputStream();

        String fileName = Objects.requireNonNull(file.getOriginalFilename()).toUpperCase();
        String extension = FilenameUtils.getExtension(fileName);

        UserSetting userSetting = userSettingService.get(userSettingId);
        int fileTypeId = userSetting.getFileType().getId();

        if (fileTypeId == 1) {
            if (!extension.equals(FileFormat.ExcelNew.getValue()) && !extension.equals(FileFormat.ExcelOld.getValue())) {
                throw new Exception("Выбран неверный тип файла. Ожидаемый тип Excel (.xls или .xlsx)");
            }
        } else if (fileTypeId == 2) {
            if (!extension.equals(FileFormat.Text.getValue()) && !extension.equals(FileFormat.CSV.getValue())) {
                throw new Exception("Выбран неверный тип файла. Ожидаемый тип Text (.txt или .csv)");
            }
        }  else {
            throw new Exception("Ошибка функции. Обратитесь к админестратору");
        }

        FileData fileData = null;
        if (extension.equals(FileFormat.ExcelNew.getValue()) || extension.equals(FileFormat.ExcelOld.getValue())) {
            fileData = new ExcelFileData(inputStream, extension, columnNameListFromSettingsWithNoEmpty);
        } else if (extension.equals(FileFormat.Text.getValue()) || extension.equals(FileFormat.CSV.getValue())) {
            fileData = new TextFileData(inputStream, columnNameListFromSettingsWithNoEmpty);
        } else {
            throw new Exception("Ошибка формата. Обратитесь к админестратору");
        }

        //get pay reqs ???????
        String templateName = userSetting.getFileTemplate().getName();
        List<UserSetting> userSettings = userSettingService.getAllByUserId(Authorization.id());
        UserSetting payReqs = userSettings.get(0);

        List<FileRow> fileRowList = fileData.handle();
        List<String> columnNameListFromFile = fileData.getColumnNameListFromFile();
        ByteArrayOutputStream baos = null;
        if (functionId == 1) {
            if (templateName.toUpperCase().equals(TEMPLATE_10_2)) {
                Template10_2 pdfHandler = new Template10_2(fileRowList, payReqs, mainColumns, sumColumnList, sumAddColumnList, uniqueColumnList, counterColumnList, counterAddColumnList, columnNameListFromFile);
                baos = pdfHandler.handle();
                lastMask = pdfHandler.getPeriodFromLastLine();
            } else {
                Template1_7 pdfHandler = new Template1_7(fileRowList, payReqs, mainColumns, uniqueColumnList, columnNameListFromFile);
                baos = pdfHandler.handle();
            }
        } else {
            if (templateName.toUpperCase().equals(TEMPLATE_10_2)) {
                ConversionRegisterInHandler conversionRegisterInHandler = new ConversionRegisterInHandler();
                baos = conversionRegisterInHandler.handle(fileRowList, mainColumns, columnNameListFromFile);
                lastMask = conversionRegisterInHandler.getPeriodFromFirstLine();
            } else {
                throw new Exception(templateName + " не поддерживает данную функцию.");
            }
        }

        return baos;
    }

    public String getLastMask() {
        return lastMask;
    }
}
