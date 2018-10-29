package ru.sber.kvitmoscow.handler.conversion;

import ru.sber.kvitmoscow.handler.model.FileRow;
import ru.sber.kvitmoscow.handler.model.MainColEntity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ConversionRegisterInHandler {
    private static String DELIMITER = ";";
    private static String NEW_LINE = "\r\n";

    private String periodFromFirstLine = "_";

    public ByteArrayOutputStream handle(List<FileRow> fileRows, MainColEntity mainColumns, List<String> columnNameListFromFile) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        StringBuilder stringBuilder = new StringBuilder();
        int rowIndex = 1;
        for (FileRow row : fileRows) {
            String ls = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getLs()));
            stringBuilder.append(ls).append(DELIMITER);
            String fio = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getFio()));
            stringBuilder.append(fio).append(DELIMITER);
            String adr = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getAdr()));
            stringBuilder.append(adr).append(DELIMITER);
            String period = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getPeriod()));
            if (rowIndex == 1)
                periodFromFirstLine = period;

            stringBuilder.append(period).append(DELIMITER);
            String sum = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getSum()));
            stringBuilder.append(sum).append(DELIMITER);
            stringBuilder.append(NEW_LINE);

            rowIndex++;
        }
        baos.write(stringBuilder.toString().getBytes("Cp1251"));
        return  baos;
    }

    public String getPeriodFromFirstLine() {
        return periodFromFirstLine;
    }
}
