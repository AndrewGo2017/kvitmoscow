package ru.sber.kvitmoscow.handler.bill.pdf.template;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.NonNull;
import ru.sber.kvitmoscow.handler.bill.pdf.PdfCellBuilder;
import ru.sber.kvitmoscow.handler.bill.qr.QrHandler;
import ru.sber.kvitmoscow.handler.bill.qr.QrStructure;
import ru.sber.kvitmoscow.handler.model.*;
import ru.sber.kvitmoscow.model.UserSetting;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Template10_2 extends BaseTemplate {

    private String periodFromLastLine = "_";

    private List<FileRow> fileRowList;
    private List<SumColEntity> sumColumnList;
    private List<SumAddColEntity> sumAddColList;
    private List<CounterColEntity> counterColumnList;
    private List<CounterAddColEntity> counterAddColList;

    public Template10_2(List<FileRow> fileRowList, UserSetting payReqs, MainColEntity mainColumns, List<SumColEntity> sumColumnList, List<SumAddColEntity> sumAddColList, List<UniqueColEntity> uniqueColumnList, List<CounterColEntity> counterColumnList, List<CounterAddColEntity> counterAddColList, List<String> columnNameListFromFile) {
        super(payReqs, mainColumns, columnNameListFromFile, uniqueColumnList);
        this.fileRowList = fileRowList;
        this.sumColumnList = sumColumnList;
        this.sumAddColList = sumAddColList;
        this.counterColumnList = counterColumnList;
        this.counterAddColList = counterAddColList;
    }

    public ByteArrayOutputStream handle() throws Exception {

        Document document = new Document();
        if (payReqs.getSheetPosition().getId() == 2){
            document.setPageSize(PageSize.A4.rotate());
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        int billQuantity = 2;
        if (payReqs.getBillQuantity() != null && payReqs.getBillQuantity() > 0){
            billQuantity = payReqs.getBillQuantity();
        }

        int rowCount = 0;

        try {
            for (FileRow row  : fileRowList) {
                rowCount++;

                PdfPTable tableL0 =  getTableL0(2.5f, 7.5f);

                PdfPTable tableL1 = getTableL1(row, 4.5f, 1, 4.5f);

                CommonReqs commonReqs = getQrStructure(mainColumns, row, columnNameListFromFile, uniqueColumnList);

                //main reqs
                PdfPTable tableL2 = getTableL2( row, commonReqs, 3, 7);

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
                Image imgQR = Image.getInstance(QrHandler.handle(
                        new QrStructure(payReqs.getOrgName(), payReqs.getOrgPayAcc(), payReqs.getOrgBank(), payReqs.getOrgBic(), payReqs.getOrgCorAcc(), payReqs.getOrgInn(), payReqs.getOrgKpp(), commonReqs.getLs(), commonReqs.getSum(), commonReqs.getFio(), commonReqs.getAdr(), payReqs.getQrAddInfo(), null, null, null, null)
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


        document.close();

        return baos;
    }

    public String getPeriodFromLastLine() {
        return periodFromLastLine;
    }
}
