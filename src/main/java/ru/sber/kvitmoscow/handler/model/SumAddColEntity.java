package ru.sber.kvitmoscow.handler.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SumAddColEntity {
    public String header;
    public String colName;
    public Integer sumIndex;
    public Integer sumAddIndex;
    public Boolean isBold;
}
