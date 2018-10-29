package ru.sber.kvitmoscow.handler.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CounterAddColEntity {
    public String header;
    public String colName;
    public Integer counterIndex;
    public Integer counterAddIndex;
}
