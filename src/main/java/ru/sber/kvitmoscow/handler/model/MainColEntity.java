package ru.sber.kvitmoscow.handler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MainColEntity {
    public String ls;
    public String adr;
    public String fio;
    public String period;
    public String sum;

    public String lsName;
    public String adrName;
    public String fioName;
    public String periodName;
    public String sumName;
}
