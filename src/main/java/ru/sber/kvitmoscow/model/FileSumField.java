package ru.sber.kvitmoscow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "file_sum_fields")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class FileSumField extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_settings_id")
    private UserSetting userSetting;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    public FileSumField(Integer id, UserSetting userSetting, String name, String value) {
        super(id);
        this.userSetting = userSetting;
        this.name = name;
        this.value = value;
    }
}
