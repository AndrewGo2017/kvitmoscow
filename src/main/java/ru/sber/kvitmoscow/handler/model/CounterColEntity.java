package ru.sber.kvitmoscow.handler.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CounterColEntity {
    public String name;
    public String measure;
    public String consumption;
    public String current;
    public String previous;
    public String tariff;
}
