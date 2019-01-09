package ru.sber.kvitmoscow.handler.bill.pdf.template;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ru.sber.kvitmoscow.handler.bill.pdf.PdfCellBuilder;
import ru.sber.kvitmoscow.handler.bill.qr.QrHandler;
import ru.sber.kvitmoscow.handler.bill.qr.QrStructure;
import ru.sber.kvitmoscow.handler.model.*;
import ru.sber.kvitmoscow.model.UserSetting;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class Template1_7 extends BaseTemplate {
    private List<FileRow> fileRowList;

    public Template1_7(List<FileRow> fileRowList, UserSetting payReqs, MainColEntity mainColumns, List<UniqueColEntity> uniqueColumnList, List<String> columnNameListFromFile) {
        super(payReqs, mainColumns, columnNameListFromFile, uniqueColumnList);
        this.fileRowList = fileRowList;
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

                PdfPTable tableL0 = getTableL0(2.5f, 7.5f);

                PdfPTable tableL1 = getTableL1(row, 4.5f, 1, 4.5f);

                String kbk = "";
                String oktmo = "";
                if (!mainColumns.getKbk().isEmpty() && !mainColumns.getOktmo().isEmpty()) {
                    kbk = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getKbk()));
                    oktmo = row.getRowData().get(columnNameListFromFile.indexOf(mainColumns.getOktmo()));

                    tableL1.addCell(new PdfCellBuilder(kbk, font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());
                    tableL1.addCell(new PdfCellBuilder(" ", font10).borderWidth(0).build());
                    tableL1.addCell(new PdfCellBuilder(oktmo, font10).border(Rectangle.BOTTOM).horizontalAlignment(1).build());

                    tableL1.addCell(new PdfCellBuilder("("+mainColumns.getKbkName().toLowerCase()+")", font7).border(0).borderWidth(0).horizontalAlignment(1).build());
                    tableL1.addCell(new PdfCellBuilder(" ", font7).border(0).borderWidth(0).horizontalAlignment(0).build());
                    tableL1.addCell(new PdfCellBuilder("("+mainColumns.getOktmoName().toLowerCase()+")", font7).border(0).borderWidth(0).horizontalAlignment(1).build());
                }


                CommonReqs commonReqs = getQrStructure(mainColumns, row, columnNameListFromFile, uniqueColumnList);

                //main reqs
                PdfPTable tableL2 = getTableL2(row, commonReqs, 3, 7);


                //QR
                Image imgQR = Image.getInstance(QrHandler.handle(
                        new QrStructure(payReqs.getOrgName(), payReqs.getOrgPayAcc(), payReqs.getOrgBank(), payReqs.getOrgBic(), payReqs.getOrgCorAcc(), payReqs.getOrgInn(), payReqs.getOrgKpp(), commonReqs.getLs(), commonReqs.getSum(), commonReqs.getFio(), commonReqs.getAdr(), payReqs.getQrAddInfo(), kbk, oktmo, commonReqs.getContract(), commonReqs.getPurpose())
                ));
                imgQR.setWidthPercentage(70);
                imgQR.setAlignment(1);
                PdfPCell splitter3 = new PdfCellBuilder(" ").border(Rectangle.RIGHT).padding(0).build();

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

        document.close();

        return baos;
    }
}
