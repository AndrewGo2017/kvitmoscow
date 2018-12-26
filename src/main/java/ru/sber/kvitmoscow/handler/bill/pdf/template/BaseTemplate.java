package ru.sber.kvitmoscow.handler.bill.pdf.template;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import ru.sber.kvitmoscow.handler.bill.pdf.PdfCellBuilder;
import ru.sber.kvitmoscow.handler.bill.pdf.PdfParagraphBuilder;
import ru.sber.kvitmoscow.handler.model.*;
import ru.sber.kvitmoscow.model.UserSetting;

import java.util.List;

class BaseTemplate {
    protected static String FONT_TIMES_PATH = "static/font/times.ttf";
    protected static String FONT_TIMES_ALIAS = "mytime";
    protected static String FONT_TIMES_BOLD_PATH = "static/font/timesbd.ttf";
    protected static String FONT_TIMES_BOLD_ALIAS = "mytimebd";
    protected static String FONT_ENCODING = "Cp1251";
    protected static int FONT3_SIZE = 4;
    protected static int FONT7_SIZE = 8;
    protected static int FONT10_SIZE = 11;

    protected Font font3;
    protected Font font7;
    protected Font font10;
    protected Font font7Bd;
    protected Font font10Bd;

    protected UserSetting payReqs;
    protected MainColEntity mainColumns;
    protected List<String> columnNameListFromFile;
    protected List<UniqueColEntity> uniqueColumnList;


    protected BaseTemplate(UserSetting payReqs, MainColEntity mainColumns, List<String> columnNameListFromFile, List<UniqueColEntity> uniqueColumnList){
        this.payReqs = payReqs;
        this.mainColumns = mainColumns;
        this.columnNameListFromFile = columnNameListFromFile;
        this.uniqueColumnList = uniqueColumnList;

        FontFactory.register(FONT_TIMES_PATH, FONT_TIMES_ALIAS);
        FontFactory.register(FONT_TIMES_BOLD_PATH, FONT_TIMES_BOLD_ALIAS);

        font3 =  FontFactory.getFont(FONT_TIMES_ALIAS, FONT_ENCODING, true,  FONT3_SIZE + payReqs.getFontSize());
        font7 = FontFactory.getFont(FONT_TIMES_ALIAS, FONT_ENCODING, true, FONT7_SIZE + payReqs.getFontSize() );
        font10 =  FontFactory.getFont(FONT_TIMES_ALIAS, FONT_ENCODING, true, FONT10_SIZE + payReqs.getFontSize() );

        font7Bd = FontFactory.getFont(FONT_TIMES_BOLD_ALIAS, FONT_ENCODING, true, FONT7_SIZE + payReqs.getFontSize());
        font10Bd = FontFactory.getFont(FONT_TIMES_BOLD_ALIAS, FONT_ENCODING, true, FONT10_SIZE + payReqs.getFontSize());
    }

    protected double toDouble(String str) {
        String newStr = str.replace(",", ".").replace(" ", "");
        return Double.parseDouble(newStr);

    }

    protected void setTableWidth(PdfPTable table) throws DocumentException {
        if (table == null) return;

        int colCount = table.getNumberOfColumns();
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

    protected PdfPTable getNewTableOfNewSize(PdfPTable table) throws DocumentException {
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

    protected PdfPTable getTableL0(float columnA, float columnB) throws DocumentException {
        PdfPTable tableL0 = new PdfPTable(2);
        tableL0.setPaddingTop(0);
        tableL0.setHorizontalAlignment(0);
        tableL0.setWidths(new float[]{columnA, columnB});
        tableL0.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableL0.getDefaultCell().setPadding(0);

        return tableL0;
    }

    protected PdfPTable getTableL1(FileRow row, float columnA, float columnB, float columnC ) throws DocumentException {
        PdfPTable tableL1 = new PdfPTable(3);
        tableL1.setHorizontalAlignment(0);
        tableL1.setWidths(new float[]{columnA, columnB, columnC});
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

        tableL1.addCell(new PdfCellBuilder("(инн / кпп получателя платежа)", font7).border(0).borderWidth(0).horizontalAlignment(1).build());
        tableL1.addCell(new PdfCellBuilder(" ", font7).border(0).borderWidth(0).horizontalAlignment(0).build());
        tableL1.addCell(new PdfCellBuilder("(номер счета получателя)", font7).border(0).borderWidth(0).horizontalAlignment(1).build());

        tableL1.addCell(new PdfCellBuilder(payReqs.getOrgBank(), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
        tableL1.addCell(new PdfCellBuilder(" ", font10).borderWidth(0).build());
        tableL1.addCell(new PdfCellBuilder(payReqs.getOrgBic(), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());

        tableL1.addCell(new PdfCellBuilder("(наименование банка)", font7).border(0).borderWidth(0).horizontalAlignment(1).build());
        tableL1.addCell(new PdfCellBuilder(" ", font7).border(0).borderWidth(0).build());
        tableL1.addCell(new PdfCellBuilder("(бик)", font7).border(0).borderWidth(0).horizontalAlignment(1).build());

        if (!mainColumns.getPeriod().isEmpty() || !payReqs.getOrgCorAcc().isEmpty()) {
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

        return tableL1;
    }

    protected PdfPTable getTableL2(FileRow row, CommonReqs qrStructure, float columnA, float columnB) throws DocumentException {
        PdfPTable tableL2 = new PdfPTable(2);
        tableL2.setHorizontalAlignment(0);
        tableL2.setWidths(new float[]{columnA, columnB});
        tableL2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        tableL2.getDefaultCell().setPadding(1);
        tableL2.getDefaultCell().setHorizontalAlignment(0);

        if (!mainColumns.getContract().isEmpty()) {
            tableL2.addCell(new Phrase(mainColumns.getContractName(), font10));
            tableL2.addCell(new PdfCellBuilder(qrStructure.getContract(), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
        }

        if (!mainColumns.getLs().isEmpty()) {
            tableL2.addCell(new Phrase(mainColumns.getLsName(), font10));
            tableL2.addCell(new PdfCellBuilder(qrStructure.getLs(), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
        }

        if (!mainColumns.getFio().isEmpty()) {
            tableL2.addCell(new Phrase(mainColumns.getFioName(), font10));
            tableL2.addCell(new PdfCellBuilder(qrStructure.getFio(), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
        }

        if (!mainColumns.getAdr().isEmpty()) {
            tableL2.addCell(new Phrase(mainColumns.getAdrName(), font10));
            tableL2.addCell(new PdfCellBuilder(qrStructure.getAdr(), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
        }

        if (!mainColumns.getPurpose().isEmpty()) {
            tableL2.addCell(new Phrase(mainColumns.getPurposeName(), font10));
            tableL2.addCell(new PdfCellBuilder(qrStructure.getPurpose(), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
        }

        //unique
        for (int i = 0; i < uniqueColumnList.size(); i++) {
            String name = uniqueColumnList.get(i).name;
            String value = row.getRowData().get(columnNameListFromFile.indexOf(uniqueColumnList.get(i).value));

            tableL2.addCell(new Phrase(name, font10));
            tableL2.addCell(new PdfCellBuilder(value, font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
        }

        //sum
        if (!mainColumns.getSum().equals("")) {
            tableL2.addCell(new Phrase(mainColumns.getSumName(), font10));
            tableL2.addCell(new PdfCellBuilder(Double.toString(qrStructure.getSum()), font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
        }

        return tableL2;
    }

    protected CommonReqs getQrStructure(MainColEntity mainColumns, FileRow row, List<String> columnNameListFromFile, List<UniqueColEntity> uniqueColumnList){
        String contract = "";
        if (!mainColumns.getContract().isEmpty()) {
            contract = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getContract()));
        }

        String ls = "";
        if (!mainColumns.getLs().isEmpty()) {
            ls = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getLs()));
        }
        String fio = "";
        if (!mainColumns.getFio().isEmpty()) {
            fio = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getFio()));
        }
        String adr = "";
        if (!mainColumns.getAdr().isEmpty()) {
            adr = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getAdr()));
        }

        String purpose = "";
        if (!mainColumns.getPurpose().isEmpty()) {
            purpose = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getPurpose()));
        }

        double sum = 0d;
        if (!mainColumns.getSum().equals("")) {
            sum = toDouble(row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getSum())));
        }

        return new CommonReqs(contract, ls, fio, adr, purpose, sum);
    }
}
