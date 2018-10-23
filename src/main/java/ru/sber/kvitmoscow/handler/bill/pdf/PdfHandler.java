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

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfHandler {
    public ByteArrayOutputStream handle(
            List<FileRow> fileRowList,
            UserSetting payReqs,
            MainColEntity mainColumns,
            List<SumColEntity> sumColumnList,
            List<UniqueColEntity> uniqueColumnList,
            List<CounterColEntity> counterColumnList,
            List<String> columnNameListFromFile
    ) throws Exception {

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        FontFactory.register("static/font/times.ttf", "mytime");
        FontFactory.register("static/font/timesbd.ttf", "mytimebd");

        Font font3 =  FontFactory.getFont("mytime", "Cp1251", true,  4 ); //new Font(baseFont, 3 + 1);
        Font font7 = FontFactory.getFont("mytime", "Cp1251", true, 8 );
        Font font10 =  FontFactory.getFont("mytime", "Cp1251", true, 11 );

        Font font7Bd = FontFactory.getFont("mytimebd", "Cp1251", true, 8 );
        Font font10Bd = FontFactory.getFont("mytimebd", "Cp1251", true, 11 );


        int rowCount = 0;

//        try {
            for (FileRow row : fileRowList) {
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
                    tableL1.addCell(new PdfCellBuilder(period, font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());

                } else {
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
                    row.getRowData().get(columnNameListFromFile.indexOf(uniqueColumnList.get(i).name));
                    String name = row.getRowData().get(columnNameListFromFile.indexOf(uniqueColumnList.get(i).name));
                    String value = row.getRowData().get(columnNameListFromFile.indexOf(uniqueColumnList.get(i).value));

                    tableL2.addCell(new Phrase(name, font10));
                    tableL2.addCell(new PdfCellBuilder(value, font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
                }

                //sum
                Double sum = 0d;
                if (sumColumnList.size() > 0){
                    if (!mainColumns.getSum().equals("")){
                        tableL2.addCell(new Phrase(mainColumns.getSumName(), font10));
                        sum = Double.parseDouble( row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getSum())) );
                        tableL2.addCell(new PdfCellBuilder(sum.toString(), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
                    }
                }

                //counters
                PdfPTable tableL3 = null;
                boolean isMeasureEmpty = true;
                boolean isTariffEmpty = true;
                boolean isCurrentEmpty = true;
                boolean isPreviousEmpty = true;
                boolean isConsumptionEmpty = true;
                if (counterColumnList.size() > 0) {
                    for (int i = 0; i < counterColumnList.size(); i++) {
                        int counterColCount = 1;
                        CounterColEntity counterColEntity = counterColumnList.get(i);

                        String name = row.getRowData().get(columnNameListFromFile.indexOf(counterColEntity.name));
                        String measure = row.getRowData().get(columnNameListFromFile.indexOf(counterColEntity.measure));
                        String tariff = row.getRowData().get(columnNameListFromFile.indexOf(counterColEntity.tariff));
                        String current = row.getRowData().get(columnNameListFromFile.indexOf(counterColEntity.current));
                        String previous = row.getRowData().get(columnNameListFromFile.indexOf(counterColEntity.previous));
                        String consumption = row.getRowData().get(columnNameListFromFile.indexOf(counterColEntity.consumption));

                        isMeasureEmpty = measure.isEmpty();
                        isTariffEmpty = tariff.isEmpty();
                        isCurrentEmpty = current.isEmpty();
                        isPreviousEmpty = previous.isEmpty();
                        isConsumptionEmpty = consumption.isEmpty();

                        if (i == 0) {
                            int couterCol = 1;
                            if (!isMeasureEmpty) counterColCount++;
                            if (!isTariffEmpty) counterColCount++;
                            if (!isCurrentEmpty) counterColCount++;
                            if (!isPreviousEmpty) counterColCount++;
                            if (!isConsumptionEmpty) counterColCount++;

                            tableL3 = new PdfPTable(counterColCount);

                            tableL3.getDefaultCell().setHorizontalAlignment(1);
                            tableL3.getDefaultCell().setVerticalAlignment(1);
                            tableL3.setWidthPercentage(100);
                            tableL3.setHorizontalAlignment(0);

                            tableL3.addCell(new Phrase("Счетчик", font7Bd));

                            if (!isMeasureEmpty) tableL3.addCell(new Phrase("Ед изм", font7Bd));
                            if (!isTariffEmpty) tableL3.addCell(new Phrase("Тариф", font7Bd));
                            if (!isCurrentEmpty) tableL3.addCell(new Phrase("Тек пок", font7Bd));
                            if (!isPreviousEmpty) tableL3.addCell(new Phrase("Пред пок", font7Bd));
                            if (!isConsumptionEmpty) tableL3.addCell(new Phrase("Расход", font7Bd));
                        }

                        tableL3.addCell(new Phrase(name, font7));
                        if (!isMeasureEmpty) tableL3.addCell(new Phrase(measure, font7));
                        if (!isTariffEmpty) tableL3.addCell(new Phrase(tariff, font7));
                        if (!isCurrentEmpty) tableL3.addCell(new Phrase(current, font7));
                        if (!isPreviousEmpty) tableL3.addCell(new Phrase(previous, font7));
                        if (!isConsumptionEmpty) tableL3.addCell(new Phrase(consumption, font7));
                    }
                }

                //sum reqs
                PdfPTable tableL4 = null;
                double totalSum = 0;
                double totalSumPlusDebt = 0;
                boolean isDebtColEmpty = true;
                boolean isTotalColEmpty = true;
                if (sumColumnList.size() > 0) {
                    for (int i = 0; i < sumColumnList.size(); i++) {
                        SumColEntity sumColEntity = sumColumnList.get(i);

                        row.getRowData().get(columnNameListFromFile.indexOf(sumColEntity.name));
                        String name = row.getRowData().get(columnNameListFromFile.indexOf(sumColEntity.name));
                        double current = toDouble(row.getRowData().get(columnNameListFromFile.indexOf(sumColEntity.current)));
                        totalSum += current;
                        double debt = 0;
                        isDebtColEmpty = sumColEntity.debt.isEmpty();
                        isTotalColEmpty = sumColEntity.total.isEmpty();
                        if (!isDebtColEmpty)
                            debt = toDouble(row.getRowData().get(columnNameListFromFile.indexOf(sumColEntity.debt)));
                        double total = 0;
                        if (!isTotalColEmpty) {
                            total = toDouble(row.getRowData().get(columnNameListFromFile.indexOf(sumColEntity.total)));
                            totalSumPlusDebt += (current + debt);
                        }

                        if (i == 0) {
                            int sumColCount = 2;
                            if (!isDebtColEmpty) sumColCount++;
                            if (!isTotalColEmpty) sumColCount++;

                            tableL4 = new PdfPTable(sumColCount);
                            if (sumColCount == 4) {
                                tableL4.setWidths(new int[]{4, 2, 2, 2});
                                tableL4.setWidthPercentage(60);
                            } else if (sumColCount == 3) {
                                tableL4.setWidths(new int[]{4, 2, 2});
                                tableL4.setWidthPercentage(50);
                            } else if (sumColCount == 2) {
                                tableL4.setWidths(new int[]{4, 2});
                                tableL4.setWidthPercentage(40);
                            }

                            tableL4.setWidthPercentage(100);
                            tableL4.getDefaultCell().setHorizontalAlignment(1);
                            tableL4.getDefaultCell().setVerticalAlignment(1);
                            tableL4.setHorizontalAlignment(0);

                            tableL4.addCell(new Phrase("Статья", font7Bd));
                            tableL4.addCell(new Phrase("Начислено", font7Bd));
                            if (!isDebtColEmpty) tableL4.addCell(new Phrase("Долг", font7Bd));
                            if (!isTotalColEmpty) tableL4.addCell(new Phrase("Всего", font7Bd));
                        }

                        tableL4.addCell(new Phrase(name, font7));
                        tableL4.addCell(new Phrase(Double.toString(current), font7));
                        if (!isDebtColEmpty) tableL4.addCell(new Phrase(Double.toString(debt), font7));
                        if (!isTotalColEmpty) tableL4.addCell(new Phrase(Double.toString(total), font7));
                    }

                    tableL4.addCell(new PdfCellBuilder( "Итого к оплате", font7Bd).border(0).horizontalAlignment(1).verticalAlignment(1).build());
                    tableL4.addCell(new PdfCellBuilder(Double.toString(DoubleRounder.round(totalSum, 2)), font7Bd).border(0).horizontalAlignment(1).verticalAlignment(1).build());

                    if (!isDebtColEmpty) {
                        tableL4.addCell(new PdfCellBuilder().border(0).borderWidth(0).build());
                    }

                    if (!isTotalColEmpty) {
                        tableL4.addCell(new PdfCellBuilder(Double.toString(DoubleRounder.round(totalSumPlusDebt, 2)), font7Bd).border(0).horizontalAlignment(1).verticalAlignment(1).build());
                    }
                }

                //QR
                Image imgQR = Image.getInstance(new QrHandler().handle(
                        new QrStructure(payReqs.getOrgName(), payReqs.getOrgPayAcc(), payReqs.getOrgBank(), payReqs.getOrgBic(), payReqs.getOrgCorAcc(), payReqs.getOrgInn(), payReqs.getOrgKpp(), ls, sum, fio, adr)
                ));
                imgQR.scaleAbsolute(80, 80);

                PdfPCell splitter3 = new PdfCellBuilder(" ").border(Rectangle.RIGHT).padding(0).build();
                Paragraph splitter2 = new Paragraph(new Phrase(" "));

                tableL0.setWidthPercentage(97);

                tableL0.addCell(new PdfCellBuilder(imgQR).border(Rectangle.RIGHT).rowSpan(1).horizontalAlignment(1).verticalAlignment(1).padding(15).build());
                tableL0.addCell(tableL1);

                tableL0.addCell(splitter3);
                tableL0.addCell(tableL2);

                tableL0.addCell(splitter3);
                tableL0.addCell(splitter2);

                tableL0.addCell(splitter3);
                tableL0.addCell(tableL3);

                tableL0.addCell(splitter3);
                tableL0.addCell(splitter2);

                tableL0.addCell(splitter3);
                tableL0.addCell(tableL4);

                PdfPTable borderTable = new PdfPTable(1);
                borderTable.setWidthPercentage(100);
                borderTable.addCell(new PdfCellBuilder(tableL0).borderWidth(1).build());

                document.add(borderTable);

                PdfPTable emptyTable = new PdfPTable(1);
                emptyTable.addCell(new PdfCellBuilder(" ", font3).border(0).build());
                document.add(emptyTable);

                if (rowCount % 2 == 0)
                    document.newPage();

            }
//        } catch (Exception e) {
//            throw new Exception("row " + rowCount + ";\n" + e.getMessage());
//        }

        //close pdf
        document.close();

        return baos;
    }

    private double toDouble(String str) {
        String newStr = str.replace(",", ".").replace(" ", "");
        return Double.parseDouble(newStr);
    }
}
