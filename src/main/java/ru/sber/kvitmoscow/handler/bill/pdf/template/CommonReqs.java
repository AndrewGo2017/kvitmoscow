package ru.sber.kvitmoscow.handler.bill.pdf.template;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
class CommonReqs {
    String contract;
    String ls;
    String fio;
    String adr;
    String purpose;
    Double sum;
}
