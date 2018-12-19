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

    @Column(name = "kbk")
    private String kbk;

    @Column(name = "oktmo")
    private String oktmo;

    @Column(name = "contract")
    private String contract;

    @Column(name = "purpose")
    private String purpose;

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

    @Column(name = "kbk_name")
    private String kbkName;

    @Column(name = "oktmo_name")
    private String oktmoName;

    @Column(name = "contract_name")
    private String contractName;

    @Column(name = "purpose_name")
    private String purposeName;

    public FileMainField(Integer id, UserSetting userSetting, String ls, String adr, String fio, String period, String sum, String kbk, String oktmo, String contract, String purpose, String lsName, String adrName, String fioName, String periodName, String sumName, String kbkName, String oktmoName, String contractName, String purposeName) {
        super(id);
        this.userSetting = userSetting;
        this.ls = ls;
        this.adr = adr;
        this.fio = fio;
        this.period = period;
        this.sum = sum;
        this.kbk = kbk;
        this.oktmo = oktmo;
        this.contract = contract;
        this.purpose = purpose;
        this.lsName = lsName;
        this.adrName = adrName;
        this.fioName = fioName;
        this.periodName = periodName;
        this.sumName = sumName;
        this.kbkName = kbkName;
        this.oktmoName = oktmoName;
        this.contractName = contractName;
        this.purposeName = purposeName;
    }
}