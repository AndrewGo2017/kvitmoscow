package ru.sber.kvitmoscow.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class FileSumAddFieldTo extends BaseTo {
    private Integer userSetting;

    private String name;

    private String value;

    private Boolean isBold;

    public FileSumAddFieldTo(Integer id, Integer userSetting, String name, String value, Boolean isBold) {
        super(id);
        this.userSetting = userSetting;
        this.name = name;
        this.value = value;
        this.isBold = isBold;
    }
}
