package ru.sber.kvitmoscow.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class UserSettingTo extends BaseTo{
    private Integer user;

    private Integer fileType;

    private Integer template;

    private Integer fileTemplate;

    private String name;

    private String fileMask;

    private String orgName;

    private String orgInn;

    private String orgKpp;

    private String orgPayAcc;

    private String orgBank;

    private String orgBic;

    private String orgCorAcc;

    private String orgAddInfo;

    public UserSettingTo(Integer id, Integer user, Integer fileType, Integer template, Integer fileTemplate, String name, String fileMask, String orgName, String orgInn, String orgKpp, String orgPayAcc, String orgBank, String orgBic, String orgCorAcc, String orgAddInfo) {
        super(id);
        this.user = user;
        this.fileType = fileType;
        this.template = template;
        this.fileTemplate = fileTemplate;
        this.name = name;
        this.fileMask = fileMask;
        this.orgName = orgName;
        this.orgInn = orgInn;
        this.orgKpp = orgKpp;
        this.orgPayAcc = orgPayAcc;
        this.orgBank = orgBank;
        this.orgBic = orgBic;
        this.orgCorAcc = orgCorAcc;
        this.orgAddInfo = orgAddInfo;
    }
}
