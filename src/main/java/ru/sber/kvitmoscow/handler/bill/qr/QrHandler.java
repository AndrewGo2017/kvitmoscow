package ru.sber.kvitmoscow.handler.bill.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QrHandler {
    public byte[] handle(QrStructure qrStructure) throws IOException, WriterException {
        String ver = "ST00012";
        String del = "|";

        String text = ver + del +
                "Name=" + qrStructure.name + del +
                "PersonalAcc=" + qrStructure.pAcc + del +
                "BankName=" + qrStructure.bank + del +
                "BIC=" + qrStructure.bic + del +
                "CorrespAcc=" + qrStructure.cAcc + del +
                "PayeeINN=" + qrStructure.inn + del +
                "KPP=" + qrStructure.kpp +
                "PersAcc=" + qrStructure.ls;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "windows-1251");

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 120, 120, hints);

//        Path path = FileSystems.getDefault().getPath("D:\\1.png");
//        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;



    }

}
