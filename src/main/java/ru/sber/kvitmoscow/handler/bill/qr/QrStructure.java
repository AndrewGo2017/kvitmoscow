package ru.sber.kvitmoscow.handler.bill.qr;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QrStructure {
    String name;
    String pAcc;
    String bank;
    String bic;
    String cAcc;
    String inn;
    String kpp;
    String ls;

    Double sum;

    String fio;
    String adr;

    String addInfo;

    String kbk;
    String oktmo;
    String contract;
    String purpose;

}
