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
    private static String CBC_NAME = "КБК";
    private static String OKTMO_NAME = "ОКТМО";
    private static String CONTRACT_NAME = "Договор";
    private static String PURPOSE_NAME = "Назначение";
    

    private Integer userSetting;

    private String ls;

    private String adr;

    private String fio;

    private String period;

    private String sum;

    private String kbk;

    private String oktmo;

    private String contract;

    private String purpose;

    private String lsName;

    private String adrName;

    private String fioName;

    private String periodName;

    private String sumName;

    private String kbkName;

    private String oktmoName;

    private String contractName;

    private String purposeName;

    public static FileMainFieldTo getDefaultMainFieldToBySettingId(int settingId, int templateId) {
        if (templateId == 1){
            return new FileMainFieldTo(settingId, "LS", "ADR", "FIO", "PERIOD", "SUM", "CBC", "OKTMO", "CONTRACT", "PURPOSE",  LS_NAME, ADR_NAME, FIO_NAME, PERIOD_NAME, SUM_NAME, CBC_NAME, OKTMO_NAME, CONTRACT_NAME, PURPOSE_NAME);
        } else{
            return new FileMainFieldTo(settingId, "0", "1", "2", "3", "4", "5", "6", "7", "8", LS_NAME, ADR_NAME, FIO_NAME, PERIOD_NAME, SUM_NAME, CBC_NAME, OKTMO_NAME, CONTRACT_NAME, PURPOSE_NAME);
        }
    }

    public static FileMainFieldTo getDefaultMainFieldTo(int settingId) {
        return new FileMainFieldTo(settingId
                , "", "", "", "", "", "", "", "", "", LS_NAME, ADR_NAME, FIO_NAME, PERIOD_NAME, SUM_NAME, CBC_NAME, OKTMO_NAME, CONTRACT_NAME, PURPOSE_NAME);
    }
}
