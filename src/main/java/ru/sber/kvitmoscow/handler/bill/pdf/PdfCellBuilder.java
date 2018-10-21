package ru.sber.kvitmoscow.handler.bill.pdf;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;

public class PdfCellBuilder {
    PdfPCell cell;

    public PdfCellBuilder() {
        this.cell = new PdfPCell();
    }

    public PdfCellBuilder(String value) {
        Phrase phrase = new Phrase(value);
        this.cell = new PdfPCell(phrase);
    }

    public PdfCellBuilder(String value, Font font) {
        Phrase phrase = new Phrase(value, font);
        this.cell = new PdfPCell(phrase);
    }

    public PdfCellBuilder(Element element) {
        this.cell = new PdfPCell();
        this.cell.addElement(element);
    }

    public PdfCellBuilder(Image image) {
        this.cell = new PdfPCell();
        this.cell.addElement(image);
    }

    public PdfCellBuilder border(int val){
        cell.setBorder(val);
        return this;
    }

    public PdfCellBuilder borderWidth(int val){
        cell.setBorderWidth(val);
        return this;
    }

    public PdfCellBuilder horizontalAlignment(int val){
        cell.setHorizontalAlignment(val);
        return this;
    }

    public PdfCellBuilder verticalAlignment(int val){
        cell.setVerticalAlignment(val);
        return this;
    }

    public PdfCellBuilder rowSpan(int val){
        cell.setRowspan(val);
        return this;
    }

    public PdfCellBuilder colSpan(int val){
        cell.setColspan(val);
        return this;
    }

    public PdfCellBuilder padding(int val){
        cell.setPadding(val);
        return this;
    }

    public PdfPCell build(){
        return cell;
    }
}
