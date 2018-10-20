package ru.sber.kvitmoscow.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class FileMainFieldTo extends BaseTo {
    private static String LS_NAME = "Лицевой счет";
    private static String ADR_NAME = "Адрес";
    private static String FIO_NAME = "ФИО";
    private static String PERIOD_NAME = "Период";
    private static String SUM_NAME = "Сумма платежа";

    private Integer userSetting;

    private String ls;

    private String adr;

    private String fio;

    private String period;

    private String sum;

    private String lsName;

    private String adrName;

    private String fioName;

    private String periodName;

    private String sumName;

    public FileMainFieldTo(Integer id, Integer userSetting, String ls, String adr, String fio, String period, String sum, String lsName, String adrName, String fioName, String periodName, String sumName) {
        super(id);
        this.userSetting = userSetting;
        this.ls = ls;
        this.adr = adr;
        this.fio = fio;
        this.period = period;
        this.sum = sum;
        this.lsName = lsName;
        this.adrName = adrName;
        this.fioName = fioName;
        this.periodName = periodName;
        this.sumName = sumName;
    }

    public static FileMainFieldTo getDefaultMainFieldToBySettingId(int settingId, int templateId) {
        if (templateId == 1){
            return new FileMainFieldTo(settingId, "LS", "ADR", "FIO", "PERIOD", "SUM", LS_NAME, ADR_NAME, FIO_NAME, PERIOD_NAME, SUM_NAME);
        } else{
            return new FileMainFieldTo(settingId, "0", "1", "2", "3", "4", LS_NAME, ADR_NAME, FIO_NAME, PERIOD_NAME, SUM_NAME);
        }
    }

    public static FileMainFieldTo getDefaultMainFieldTo() {
        return new FileMainFieldTo(null, "", "", "", "", "", LS_NAME, ADR_NAME, FIO_NAME, PERIOD_NAME, SUM_NAME);
    }
}
