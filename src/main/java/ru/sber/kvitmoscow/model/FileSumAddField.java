package ru.sber.kvitmoscow.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "file_sum_add_fields")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FileSumAddField extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "user_setting_id")
    private UserSetting userSetting;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @JsonProperty
    @Column(name = "is_bold")
    private Boolean isBold;

    public FileSumAddField(Integer id, UserSetting userSetting, String name, String value, Boolean isBold) {
        super(id);
        this.userSetting = userSetting;
        this.name = name;
        this.value = value;
        this.isBold = isBold;
    }

    public UserSetting getUserSetting() {
        return userSetting;
    }

    public void setUserSetting(UserSetting userSetting) {
        this.userSetting = userSetting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getBold() {
        return isBold;
    }

    public void setBold(Boolean bold) {
        isBold = bold;
    }

    public Boolean getIsBold() {
        return isBold;
    }

    public void setIsBold(Boolean isBold) {
        this.isBold = isBold;
    }
}
