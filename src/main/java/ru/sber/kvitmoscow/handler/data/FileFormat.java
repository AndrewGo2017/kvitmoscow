package ru.sber.kvitmoscow.handler.data;

public enum FileFormat {
    ExcelOld("XLS"),
    ExcelNew("XLSX"),
    Text("TXT"),
    CSV("CSV");

    private String value;

    FileFormat(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
