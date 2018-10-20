package ru.sber.kvitmoscow.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class FileUniqueFieldTo extends BaseTo {
    @ManyToOne
    @JoinColumn(name = "user_settings_id")
    private Integer userSetting;

    @Column(name = "")
    private String name;

    @Column(name = "")
    private String value;

    public FileUniqueFieldTo(Integer id, Integer userSetting, String name, String value) {
        super(id);
        this.userSetting = userSetting;
        this.name = name;
        this.value = value;
    }
}
