package ru.sber.kvitmoscow.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class FileCounterFieldTo extends BaseTo {
    private Integer userSetting;

    private String name;

    private String mesure;

    private String tariff;

    private String current;

    private String previous;

    private String consumption;

    public FileCounterFieldTo(Integer id, Integer userSetting, String name, String mesure, String tariff, String current, String previous, String consumption) {
        super(id);
        this.userSetting = userSetting;
        this.name = name;
        this.mesure = mesure;
        this.tariff = tariff;
        this.current = current;
        this.previous = previous;
        this.consumption = consumption;
    }
}
