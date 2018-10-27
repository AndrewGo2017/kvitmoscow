package ru.sber.kvitmoscow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "file_counter_fields")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class FileCounterField extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "user_settings_id")
    private UserSetting userSetting;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    public FileCounterField(Integer id, UserSetting userSetting, String name, String value) {
        super(id);
        this.userSetting = userSetting;
        this.name = name;
        this.value = value;
    }
}
