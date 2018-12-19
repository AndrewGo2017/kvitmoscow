package ru.sber.kvitmoscow.handler.bill.pdf.template;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfPTable;

public class BaseTemplate {
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


    protected BaseTemplate(int fontSize){
        FontFactory.register(FONT_TIMES_PATH, FONT_TIMES_ALIAS);
        FontFactory.register(FONT_TIMES_BOLD_PATH, FONT_TIMES_BOLD_ALIAS);

        font3 =  FontFactory.getFont(FONT_TIMES_ALIAS, FONT_ENCODING, true,  FONT3_SIZE + fontSize);
        font7 = FontFactory.getFont(FONT_TIMES_ALIAS, FONT_ENCODING, true, FONT7_SIZE + fontSize );
        font10 =  FontFactory.getFont(FONT_TIMES_ALIAS, FONT_ENCODING, true, FONT10_SIZE + fontSize );

        font7Bd = FontFactory.getFont(FONT_TIMES_BOLD_ALIAS, FONT_ENCODING, true, FONT7_SIZE + fontSize);
        font10Bd = FontFactory.getFont(FONT_TIMES_BOLD_ALIAS, FONT_ENCODING, true, FONT10_SIZE + fontSize);
    }

    protected double toDouble(String str) {
        String newStr = str.replace(",", ".").replace(" ", "");
        return Double.parseDouble(newStr);
    }

    protected void setTableWidth(PdfPTable table) throws DocumentException {
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
}
