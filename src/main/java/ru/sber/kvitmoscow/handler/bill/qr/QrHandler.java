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

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(ver).append(del)
                .append("Name=").append(qrStructure.name).append(del)
                .append("PersonalAcc=").append(qrStructure.pAcc).append(del)
                .append("BankName=").append(qrStructure.bank).append(del)
                .append("BIC=").append(qrStructure.bic).append(del )
                .append("CorrespAcc=").append(qrStructure.cAcc).append(del)
                .append("PayeeINN=").append(qrStructure.inn).append(del)
                .append("KPP=").append(qrStructure.kpp).append(del)
                .append("PersAcc=").append(qrStructure.ls).append(del)
                .append("Sum=").append((qrStructure.sum * 100)).append(del)
                .append("LastName=").append(qrStructure.fio).append(del)
                .append("PayerAddress=").append(qrStructure.adr).append(del)
                ;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "windows-1251");

        BitMatrix bitMatrix = qrCodeWriter.encode(stringBuilder.toString(), BarcodeFormat.QR_CODE, 150, 150, hints);

//        Path path = FileSystems.getDefault().getPath("D:\\1.png");
//        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;



    }

}
