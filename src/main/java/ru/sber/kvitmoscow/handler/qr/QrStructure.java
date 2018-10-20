package ru.sber.kvitmoscow.handler.qr;

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
}
