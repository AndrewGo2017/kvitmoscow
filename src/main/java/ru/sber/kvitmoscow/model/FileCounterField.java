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

    @Column(name = "mesure")
    private String mesure;

    @Column(name = "tariff")
    private String tariff;

    @Column(name = "current")
    private String current;

    @Column(name = "previous")
    private String previous;

    @Column(name = "consumption")
    private String consumption;

    public FileCounterField(Integer id, UserSetting userSetting, String name, String mesure, String tariff, String current, String previous, String consumption) {
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
