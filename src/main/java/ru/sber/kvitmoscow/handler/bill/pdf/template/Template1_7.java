package ru.sber.kvitmoscow.handler.bill.pdf.template;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ru.sber.kvitmoscow.handler.bill.pdf.PdfCellBuilder;
import ru.sber.kvitmoscow.handler.bill.pdf.PdfParagraphBuilder;
import ru.sber.kvitmoscow.handler.bill.qr.QrHandler;
import ru.sber.kvitmoscow.handler.bill.qr.QrStructure;
import ru.sber.kvitmoscow.handler.model.*;
import ru.sber.kvitmoscow.model.UserSetting;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class Template1_7 extends BaseTemplate {
    private List<FileRow> fileRowList;
    private UserSetting payReqs;
    private MainColEntity mainColumns;
    private List<UniqueColEntity> uniqueColumnList;
    private List<String> columnNameListFromFile;

    public Template1_7(List<FileRow> fileRowList, UserSetting payReqs, MainColEntity mainColumns, List<UniqueColEntity> uniqueColumnList, List<String> columnNameListFromFile) {
        super(payReqs.getFontSize());
        this.fileRowList = fileRowList;
        this.payReqs = payReqs;
        this.mainColumns = mainColumns;
        this.uniqueColumnList = uniqueColumnList;
        this.columnNameListFromFile = columnNameListFromFile;
    }

    public ByteArrayOutputStream handle() throws Exception {
        Document document = new Document();
        if (payReqs.getSheetPosition().getId() == 2) {
            document.setPageSize(PageSize.A4.rotate());
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        int billQuantity = 2;
        if (payReqs.getBillQuantity() != null && payReqs.getBillQuantity() > 0) {
            billQuantity = payReqs.getBillQuantity();
        }

        int rowCount = 0;

        try {
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

                tableL1.addCell(new PdfCellBuilder(new PdfParagraphBuilder(" " + payReqs.getOrgName(), font7Bd).build()).border(0).colSpan(3).padding(0).build());
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

                if (!mainColumns.getPeriod().isEmpty() && !payReqs.getOrgCorAcc().isEmpty()) {
                    if (!mainColumns.getPeriod().isEmpty()) {
                        String period = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getPeriod()));
                        tableL1.addCell(new PdfCellBuilder(period, font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());

                    } else {
                        tableL1.addCell(new Phrase());
                    }

                    tableL1.addCell(new PdfCellBuilder(" ", font7).border(0).borderWidth(0).horizontalAlignment(0).build());

                    if (!payReqs.getOrgCorAcc().isEmpty()) {
                        tableL1.addCell(new PdfCellBuilder(payReqs.getOrgCorAcc(), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
                    } else {
                        tableL1.addCell(new Phrase());
                    }

                    if (!mainColumns.getPeriod().isEmpty()) {
                        tableL1.addCell(new PdfCellBuilder(mainColumns.getPeriodName(), font7).border(0).borderWidth(0).horizontalAlignment(1).build());

                    } else {
                        tableL1.addCell(new Phrase());
                    }
                    tableL1.addCell(new Phrase(" ", font7));

                    if (!payReqs.getOrgCorAcc().isEmpty()) {
                        tableL1.addCell(new PdfCellBuilder("(кор счет)", font7).border(0).borderWidth(0).horizontalAlignment(1).build());
                    } else {
                        tableL1.addCell(new Phrase());
                    }
                }

                if (!mainColumns.getKbk().isEmpty() && !mainColumns.getOktmo().isEmpty()) {
                    String kbk = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getKbk()));
                    String oktmo = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getOktmo()));

                    tableL1.addCell(new PdfCellBuilder(kbk, font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
                    tableL1.addCell(new PdfCellBuilder(" ", font10).borderWidth(0).build());
                    tableL1.addCell(new PdfCellBuilder(oktmo, font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());

                    tableL1.addCell(new PdfCellBuilder("(кбк)", font7).border(0).borderWidth(0).horizontalAlignment(1).build());
                    tableL1.addCell(new PdfCellBuilder(" ", font7).border(0).borderWidth(0).horizontalAlignment(0).build());
                    tableL1.addCell(new PdfCellBuilder("(октмо)", font7).border(0).borderWidth(0).horizontalAlignment(1).build());
                }


                //main reqs
                PdfPTable tableL2 = new PdfPTable(2);
                tableL2.setHorizontalAlignment(0);
                tableL2.setWidths(new float[]{3, 7});
                tableL2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                tableL2.getDefaultCell().setPadding(1);
                tableL2.getDefaultCell().setHorizontalAlignment(0);

                String contract = "";
                if (!mainColumns.getContract().isEmpty()) {
                    tableL2.addCell(new Phrase(mainColumns.getContractName(), font10));
                    contract = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getContract()));
                    tableL2.addCell(new PdfCellBuilder(contract, font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());

                }

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

                String purpose = "";
                if (!mainColumns.getPurpose().isEmpty()) {
                    tableL2.addCell(new Phrase(mainColumns.getPurposeName(), font10));
                    purpose = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getPurpose()));
                    tableL2.addCell(new PdfCellBuilder(purpose, font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
                }

                //unique
                for (int i = 0; i < uniqueColumnList.size(); i++) {
                    String name = uniqueColumnList.get(i).name;
                    String value = row.getRowData().get(columnNameListFromFile.indexOf(uniqueColumnList.get(i).value));

                    tableL2.addCell(new Phrase(name, font10));
                    tableL2.addCell(new PdfCellBuilder(value, font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
                }

                //sum
                double sum = 0d;
                if (!mainColumns.getSum().equals("")) {
                    tableL2.addCell(new Phrase(mainColumns.getSumName(), font10));
                    sum = toDouble(row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getSum())));
                    tableL2.addCell(new PdfCellBuilder(Double.toString(sum), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
                }


                //QR
                Image imgQR = Image.getInstance(new QrHandler().handle(
                        new QrStructure(payReqs.getOrgName(), payReqs.getOrgPayAcc(), payReqs.getOrgBank(), payReqs.getOrgBic(), payReqs.getOrgCorAcc(), payReqs.getOrgInn(), payReqs.getOrgKpp(), ls, sum, fio, adr, payReqs.getQrAddInfo(), mainColumns.getKbk(), mainColumns.getOktmo(), contract, purpose)
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
}
