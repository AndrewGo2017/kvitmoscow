package ru.sber.kvitmoscow.handler.bill.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.decimal4j.util.DoubleRounder;
import ru.sber.kvitmoscow.handler.bill.qr.QrHandler;
import ru.sber.kvitmoscow.handler.bill.qr.QrStructure;
import ru.sber.kvitmoscow.handler.model.*;
import ru.sber.kvitmoscow.model.UserSetting;

import javax.persistence.criteria.Predicate;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PdfHandler {
    private static String FONT_TIMES_PATH = "static/font/times.ttf";
    private static String FONT_TIMES_ALIAS = "mytime";
    private static String FONT_TIMES_BOLD_PATH = "static/font/timesbd.ttf";
    private static String FONT_TIMES_BOLD_ALIAS = "mytimebd";
    private static String FONT_ENCODING = "Cp1251";
    private static int FONT3_SIZE = 4;
    private static int FONT7_SIZE = 8;
    private static int FONT10_SIZE = 11;

    private String periodFromLastLine = "_";

    public ByteArrayOutputStream handle(
            List<FileRow> fileRowList,
            UserSetting payReqs,
            MainColEntity mainColumns,
            List<SumColEntity> sumColumnList,
            List<SumAddColEntity> sumAddColList,
            List<UniqueColEntity> uniqueColumnList,
            List<CounterColEntity> counterColumnList,
            List<CounterAddColEntity> counterAddColList,
            List<String> columnNameListFromFile
    ) throws Exception {

        Document document = new Document();
        if (payReqs.getSheetPosition().getId() == 2){
            document.setPageSize(PageSize.A4.rotate());
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        FontFactory.register(FONT_TIMES_PATH, FONT_TIMES_ALIAS);
        FontFactory.register(FONT_TIMES_BOLD_PATH, FONT_TIMES_BOLD_ALIAS);

        Font font3 =  FontFactory.getFont(FONT_TIMES_ALIAS, FONT_ENCODING, true,  FONT3_SIZE + payReqs.getFontSize());
        Font font7 = FontFactory.getFont(FONT_TIMES_ALIAS, FONT_ENCODING, true, FONT7_SIZE + payReqs.getFontSize() );
        Font font10 =  FontFactory.getFont(FONT_TIMES_ALIAS, FONT_ENCODING, true, FONT10_SIZE + payReqs.getFontSize() );

        Font font7Bd = FontFactory.getFont(FONT_TIMES_BOLD_ALIAS, FONT_ENCODING, true, FONT7_SIZE + payReqs.getFontSize());
        Font font10Bd = FontFactory.getFont(FONT_TIMES_BOLD_ALIAS, FONT_ENCODING, true, FONT10_SIZE + payReqs.getFontSize() );

        int billQuantity = 2;
        if (payReqs.getBillQuantity() != null && payReqs.getBillQuantity() > 0){
            billQuantity = payReqs.getBillQuantity();
        }

        int rowCount = 0;

        try {
            for (FileRow row  : fileRowList) {
                rowCount++;

                PdfPTable tableL0 = new PdfPTable(2);
                tableL0.setPaddingTop(0);
                tableL0.setHorizontalAlignment(0);
                tableL0.setWidths(new float[]{2.5f, 7.5f});
                tableL0.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                tableL0.getDefaultCell().setPadding(0);

                PdfPTable tableL1 = new PdfPTable(3);
                tableL1.setHorizontalAlignment(0);
                tableL1.setWidths(new float[]{4.5f, 1f, 4.5f});
                tableL1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                tableL1.getDefaultCell().setPadding(1);
                tableL1.setHorizontalAlignment(1);

                //header

                tableL1.addCell(new PdfCellBuilder(new PdfParagraphBuilder(" " + payReqs.getOrgName() , font7Bd).build()).border(0).colSpan(3).padding(0).build());
                tableL1.addCell(new PdfCellBuilder(new PdfParagraphBuilder(" " + payReqs.getOrgAddInfo(), font7).build()).border(0).colSpan(3).padding(0).build());


                //pay reqs
                tableL1.addCell(new PdfCellBuilder(payReqs.getOrgInn() + " / " + payReqs.getOrgKpp(), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
                tableL1.addCell(new PdfCellBuilder(" ", font10).borderWidth(0).build());
                tableL1.addCell(new PdfCellBuilder(payReqs.getOrgPayAcc(), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());

                tableL1.addCell(new PdfCellBuilder("(инн / кпп получателя плателя)", font7).border(0).borderWidth(0).horizontalAlignment(1).build());
                tableL1.addCell(new PdfCellBuilder(" ", font7).border(0).borderWidth(0).horizontalAlignment(0).build());
                tableL1.addCell(new PdfCellBuilder("(номер счета получателя)", font7).border(0).borderWidth(0).horizontalAlignment(1).build());

                tableL1.addCell(new PdfCellBuilder(payReqs.getOrgBank(), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
                tableL1.addCell(new PdfCellBuilder(" ", font10).borderWidth(0).build());
                tableL1.addCell(new PdfCellBuilder(payReqs.getOrgBic(), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());

                tableL1.addCell(new PdfCellBuilder("(наименование банка)", font7).border(0).borderWidth(0).horizontalAlignment(1).build());
                tableL1.addCell(new PdfCellBuilder(" ", font7).border(0).borderWidth(0).build());
                tableL1.addCell(new PdfCellBuilder("(бик)", font7).border(0).borderWidth(0).horizontalAlignment(1).build());

                if (!mainColumns.getPeriod().isEmpty()) {
                    String period = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getPeriod()));
                    periodFromLastLine = period;
                    tableL1.addCell(new PdfCellBuilder(period, font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());

                } else {
                    periodFromLastLine = "_";
                    tableL1.addCell(new Phrase());
                }

                tableL1.addCell(new PdfCellBuilder(" ", font7).border(0).borderWidth(0).horizontalAlignment(0).build());
                tableL1.addCell(new PdfCellBuilder(payReqs.getOrgCorAcc(), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());

                if (!mainColumns.getPeriod().isEmpty()) {
                    tableL1.addCell(new PdfCellBuilder(mainColumns.getPeriodName(), font7).border(0).borderWidth(0).horizontalAlignment(1).build());

                } else {
                    tableL1.addCell(new Phrase());
                }
                tableL1.addCell(new Phrase(" ", font7));
                tableL1.addCell(new PdfCellBuilder("(кор счет)", font7).border(0).borderWidth(0).horizontalAlignment(1).build());


                //main reqs
                PdfPTable tableL2 = new PdfPTable(2);
                tableL2.setHorizontalAlignment(0);
                tableL2.setWidths(new float[]{3, 7});
                tableL2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                tableL2.getDefaultCell().setPadding(1);
                tableL2.getDefaultCell().setHorizontalAlignment(0);

                String ls = "";
                if (!mainColumns.getLs().isEmpty()) {
                    tableL2.addCell(new Phrase(mainColumns.getLsName(), font10));
                    ls = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getLs()));
                    tableL2.addCell(new PdfCellBuilder(ls, font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());

                }
                String fio = "";
                if (!mainColumns.getFio().isEmpty()) {
                    tableL2.addCell(new Phrase(mainColumns.getFioName(), font10));
                    fio = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getFio()));
                    tableL2.addCell(new PdfCellBuilder(fio, font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
                }
                String adr = "";
                if (!mainColumns.getAdr().isEmpty()) {
                    tableL2.addCell(new Phrase(mainColumns.getAdrName(), font10));
                    adr = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getAdr()));
                    tableL2.addCell(new PdfCellBuilder(adr, font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
                }

                //unique
                for (int i = 0; i < uniqueColumnList.size(); i++) {
                    String name = uniqueColumnList.get(i).name;
                    String value = row.getRowData().get(columnNameListFromFile.indexOf(uniqueColumnList.get(i).value));

                    tableL2.addCell(new Phrase(name, font10));
                    tableL2.addCell(new PdfCellBuilder(value, font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
                }

                //sum
                Double sum = 0d;
                if (sumColumnList.size() > 0){
                    if (!mainColumns.getSum().equals("")){
                        tableL2.addCell(new Phrase(mainColumns.getSumName(), font10));
                        sum = toDouble( row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getSum())) );
                        tableL2.addCell(new PdfCellBuilder(sum.toString(), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
                    }
                }

                //counters
                PdfPTable tableL3 = null;
                if (counterColumnList.size() > 0) {
                    for (int i = 0; i < counterColumnList.size(); i++) {
                        CounterColEntity counterColEntity = counterColumnList.get(i);

                        String name = !counterColEntity.name.equals("") ? row.getRowData().get(columnNameListFromFile.indexOf(counterColEntity.name)) : "";
                        String value = !counterColEntity.value.equals("") ?  row.getRowData().get(columnNameListFromFile.indexOf(counterColEntity.value)) : "";

                        if (i == 0) {
                            int counterColCount = 2;
                            int maxIndex = counterAddColList.stream().map(e -> e.counterAddIndex).mapToInt(e -> e).filter(e -> e >= 0).max().orElse(0);
                            counterColCount += maxIndex;

                            tableL3 = new PdfPTable(counterColCount);
                            setTableWidth(tableL3);

                            tableL3.getDefaultCell().setHorizontalAlignment(1);
                            tableL3.getDefaultCell().setVerticalAlignment(1);
                            tableL3.setHorizontalAlignment(0);

                            tableL3.addCell(new Phrase("Счетчик", font7Bd));
                            tableL3.addCell(new Phrase("Пред показания", font7Bd));

                            List<Integer> indexes = new ArrayList<>();
                            for (CounterAddColEntity counterAddCol : counterAddColList){
                                if (!indexes.contains(counterAddCol.counterAddIndex)){
                                    tableL3.addCell(new Phrase(counterAddCol.header, font7Bd));
                                    indexes.add(counterAddCol.counterAddIndex);
                                }
                            }
                        }
                        tableL3.addCell(new Phrase(name, font7));
                        tableL3.addCell(new Phrase(value, font7));
                        for (CounterAddColEntity counterAddCol : counterAddColList){
                            if (counterAddCol.counterIndex == i + 1){
                                int colIndex = columnNameListFromFile.indexOf(counterAddCol.colName);
                                String addCounter = "";
                                if (colIndex != -1){
                                    addCounter = row.getRowData().get(colIndex);
                                }
                                tableL3.addCell(new Phrase(addCounter, font7));
                            }
                        }
                    }
                }

                //sum reqs
                PdfPTable tableL4 = null;
                if (sumColumnList.size() > 0) {
                    for (int i = 0; i < sumColumnList.size(); i++) {
                        SumColEntity sumColEntity = sumColumnList.get(i);
                        if (sumColEntity.isBold == null )
                            sumColEntity.isBold = false;

                        row.getRowData().get(columnNameListFromFile.indexOf(sumColEntity.name));
                        String name = row.getRowData().get(columnNameListFromFile.indexOf(sumColEntity.name));
//                        double current = toDouble(row.getRowData().get(columnNameListFromFile.indexOf(sumColEntity.current)));
                        String current = row.getRowData().get(columnNameListFromFile.indexOf(sumColEntity.current));

                        if (i == 0) {
                            int sumColCount = 2;
                            int maxIndex = sumAddColList.stream().map(e -> e.sumAddIndex).mapToInt(e -> e).filter(e -> e >= 0).max().orElse(0);
                            sumColCount += maxIndex;

                            tableL4 = new PdfPTable(sumColCount);
                            setTableWidth(tableL4);

                            tableL4.getDefaultCell().setHorizontalAlignment(1);
                            tableL4.getDefaultCell().setVerticalAlignment(1);
                            tableL4.setHorizontalAlignment(0);

                            tableL4.addCell(new Phrase("Статья", font7Bd));
                            tableL4.addCell(new Phrase("Начислено", font7Bd));

                            List<Integer> indexes = new ArrayList<>();
                            for (SumAddColEntity sumAddCol : sumAddColList){
                                if (!indexes.contains(sumAddCol.sumAddIndex)){
                                    tableL4.addCell(new Phrase(sumAddCol.header, font7Bd));
                                    indexes.add(sumAddCol.sumAddIndex);
                                }
                            }
                        }

                        tableL4.addCell(new Phrase(name, sumColEntity.isBold ? font7Bd : font7));
                        tableL4.addCell(new Phrase(current, sumColEntity.isBold ? font7Bd : font7));
                        for (SumAddColEntity sumAddCol : sumAddColList){
                            if (sumAddCol.isBold == null)
                                sumAddCol.isBold = false;
                            if (sumAddCol.sumIndex == i + 1){
                                int colIndex = columnNameListFromFile.indexOf(sumAddCol.colName);
                                String addSum = "";
                                if (colIndex != -1){
                                    addSum = row.getRowData().get(colIndex);
                                }
                                tableL4.addCell(new Phrase(addSum, sumAddCol.isBold ? font7Bd : font7));
                            }
                        }
                    }
                }

                //QR
                Image imgQR = Image.getInstance(new QrHandler().handle(
                        new QrStructure(payReqs.getOrgName(), payReqs.getOrgPayAcc(), payReqs.getOrgBank(), payReqs.getOrgBic(), payReqs.getOrgCorAcc(), payReqs.getOrgInn(), payReqs.getOrgKpp(), ls, sum, fio, adr, payReqs.getQrAddInfo())
                ));
                imgQR.setWidthPercentage(70);
                imgQR.setAlignment(1);
                PdfPCell splitter3 = new PdfCellBuilder(" ").border(Rectangle.RIGHT).padding(0).build();
                Paragraph splitter2 = new Paragraph(new Phrase(" "));

                tableL0.setWidthPercentage(97);

                tableL0.addCell(new PdfCellBuilder(imgQR).border(Rectangle.RIGHT).rowSpan(1).horizontalAlignment(1).verticalAlignment(1).padding(0).build());
                tableL0.addCell(tableL1);

                tableL0.addCell(splitter3);
                tableL0.addCell(tableL2);

                tableL0.addCell(splitter3);
                tableL0.addCell(splitter2);

                tableL0.addCell(splitter3);
                if (tableL3 != null){
                    tableL0.addCell(getNewTableOfNewSize(tableL3));
                } else {
                    tableL0.addCell(splitter2);
                }

                tableL0.addCell(splitter3);
                tableL0.addCell(splitter2);

                tableL0.addCell(splitter3);
                if (tableL4 != null) {
                    tableL0.addCell(getNewTableOfNewSize(tableL4));
                } else{
                    tableL0.addCell(splitter2);
                }


                PdfPTable borderTable = new PdfPTable(1);
                borderTable.setWidthPercentage(100);
                borderTable.addCell(new PdfCellBuilder(tableL0).borderWidth(1).build());

                document.add(borderTable);

                PdfPTable emptyTable = new PdfPTable(1);
                emptyTable.addCell(new PdfCellBuilder(" ", font3).border(0).build());
                document.add(emptyTable);

                if (rowCount % billQuantity == 0)
                    document.newPage();

            }
        } catch (Exception e) {
            throw new Exception("ряд  " + rowCount + " ; " + e.toString());
        }

        //close pdf
        document.close();

        return baos;
    }

    private double toDouble(String str) {
        String newStr = str.replace(",", ".").replace(" ", "");
        return Double.parseDouble(newStr);
    }

    public String getPeriodFromLastLine() {
        return periodFromLastLine;
    }

    private void setTableWidth(PdfPTable table) throws DocumentException {
        if (table == null) return;

        int colCount = table.getNumberOfColumns();
        PdfPTable newTable = table;
        if (colCount == 4) {
            table.setWidths(new int[]{4, 2, 2, 2});
            table.setWidthPercentage(60);
        } else if (colCount == 3) {
            table.setWidths(new int[]{4, 2, 2});
            table.setWidthPercentage(50);
        } else if (colCount == 2) {
            table.setWidths(new int[]{4, 2});
            table.setWidthPercentage(40);
        } else{
            table.setWidthPercentage(97);
        }
    }

    private PdfPTable getNewTableOfNewSize(PdfPTable table) throws DocumentException {
        int colNum = table.getNumberOfColumns();

        PdfPTable pdfPTable = new PdfPTable(2);
        pdfPTable.getDefaultCell().setBorder(0);
        pdfPTable.setWidthPercentage(100);

        if (colNum > 1 &&  colNum < 3){
            pdfPTable.setWidths(new int[] {6,4});
            pdfPTable.addCell(table);
            pdfPTable.completeRow();
        } else if (colNum == 3){
            pdfPTable.setWidths(new int[] {8,2});
            pdfPTable.addCell(table);
            pdfPTable.completeRow();
//        } else if (colNum == 4){
//            pdfPTable.setWidths(new int[] {8,2});
//            pdfPTable.addCell(table);
//            pdfPTable.completeRow();
        } else {
            pdfPTable.setWidths(new int[] {1,0});
            pdfPTable.addCell(table);
            pdfPTable.completeRow();
        }

        return pdfPTable;
    }
}
