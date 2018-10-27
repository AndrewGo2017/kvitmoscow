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

    private String value;

    public FileSumFieldTo(Integer id, Integer userSetting, String name, String value) {
        super(id);
        this.userSetting = userSetting;
        this.name = name;
        this.value = value;
    }
}
