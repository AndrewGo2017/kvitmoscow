package ru.sber.kvitmoscow.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class FileSumFieldTo extends BaseTo {
    private Integer userSetting;

    private String name;

    private String current;

    private String debt;

    private String total;

    public FileSumFieldTo(Integer id, Integer userSetting, String name, String current, String debt, String total) {
        super(id);
        this.userSetting = userSetting;
        this.name = name;
        this.current = current;
        this.debt = debt;
        this.total = total;
    }
}
