package ru.sber.kvitmoscow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "file_main_fields")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class FileMainField extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_settings_id")
    private UserSetting userSetting;

    @Column(name = "ls")
    private String ls;

    @Column(name = "adr")
    private String adr;

    @Column(name = "fio")
    private String fio;

    @Column(name = "period")
    private String period;

    @Column(name = "sum")
    private String sum;

    @Column(name = "ls_name")
    private String lsName;

    @Column(name = "adr_name")
    private String adrName;

    @Column(name = "fio_name")
    private String fioName;

    @Column(name = "period_name")
    private String periodName;

    @Column(name = "sum_name")
    private String sumName;

    public FileMainField(Integer id, UserSetting userSetting, String ls, String adr, String fio, String period, String sum, String lsName, String adrName, String fioName, String periodName, String sumName) {
        super(id);
        this.userSetting = userSetting;
        this.ls = ls;
        this.adr = adr;
        this.fio = fio;
        this.period = period;
        this.sum = sum;
        this.lsName = lsName;
        this.adrName = adrName;
        this.fioName = fioName;
        this.periodName = periodName;
        this.sumName = sumName;
    }
}