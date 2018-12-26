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
    public static byte[] handle(QrStructure qrStructure) throws IOException, WriterException {
        String ver = "ST00012";
        String del = "|";

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(ver).append(del)
                .append("Name=").append(qrStructure.name).append(del)
                .append("PersonalAcc=").append(qrStructure.pAcc).append(del)
                .append("BankName=").append(qrStructure.bank).append(del)
                .append("BIC=").append(qrStructure.bic).append(del )
                .append("CorrespAcc=").append(qrStructure.cAcc.isEmpty() ? "0" : qrStructure.cAcc).append(del)
                .append("PayeeINN=").append(qrStructure.inn).append(del);

        if (qrStructure.kpp != null && !qrStructure.kpp.isEmpty()){
            stringBuilder.append("KPP=").append(qrStructure.kpp).append(del);
        }
        if (qrStructure.ls != null && !qrStructure.ls.isEmpty()){
            stringBuilder.append("PersAcc=").append(qrStructure.ls).append(del);
        }
        stringBuilder.append("Sum=").append((long)(qrStructure.sum * 100)).append(del);
        if (qrStructure.fio != null && !qrStructure.fio.isEmpty()){
            stringBuilder.append("LastName=").append(qrStructure.fio).append(del);
        }
        if (qrStructure.kbk != null && !qrStructure.kbk.isEmpty()){
            stringBuilder.append("CBC=").append(qrStructure.kbk).append(del);
        }
        if (qrStructure.oktmo != null && !qrStructure.oktmo.isEmpty()){
            stringBuilder.append("OKTMO=").append(qrStructure.oktmo).append(del);
        }
        if (qrStructure.contract != null && !qrStructure.contract.isEmpty()){
            stringBuilder.append("Contract=").append(qrStructure.contract).append(del);
        }
        if (qrStructure.purpose != null && !qrStructure.purpose.isEmpty()){
            stringBuilder.append("Purpose=").append(qrStructure.purpose).append(del);
        }
        if (qrStructure.adr != null && !qrStructure.adr.isEmpty()){
            stringBuilder.append(qrStructure.addInfo).append(del);
        }

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(stringBuilder.toString(), BarcodeFormat.QR_CODE, 150, 150, hints);

//        Path path = FileSystems.getDefault().getPath("D:\\1.png");
//        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;

    }

}
