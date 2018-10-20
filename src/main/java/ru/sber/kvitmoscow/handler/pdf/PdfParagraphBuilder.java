package ru.sber.kvitmoscow.handler.pdf;

import com.itextpdf.text.*;
public class PdfParagraphBuilder {
    private Paragraph par;

    public PdfParagraphBuilder() {
        this.par = new Paragraph();
    }

    public PdfParagraphBuilder(String value) {
        this.par = new Paragraph(value);
    }

    public PdfParagraphBuilder(String value, Font font) {
        this.par = new Paragraph(value, font);
    }

    public PdfParagraphBuilder padding(int val){
        par.setPaddingTop(val);
        return this;
    }

    public PdfParagraphBuilder spacingAfter(int val){
        par.setSpacingAfter(val);
        return this;
    }

    public PdfParagraphBuilder spacingBefore(int val){
        par.setSpacingBefore(val);
        return this;
    }

    public Paragraph build(){
        return par;
    }
}
