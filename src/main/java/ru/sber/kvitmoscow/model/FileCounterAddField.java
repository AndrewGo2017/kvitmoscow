package ru.sber.kvitmoscow.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "file_counter_add_fields")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class FileCounterAddField extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "user_setting_id")
    private UserSetting userSetting;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    public FileCounterAddField(Integer id, UserSetting userSetting, String name, String value) {
        super(id);
        this.userSetting = userSetting;
        this.name = name;
        this.value = value;
    }
}