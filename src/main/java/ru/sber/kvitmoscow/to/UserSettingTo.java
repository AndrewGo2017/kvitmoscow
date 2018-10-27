package ru.sber.kvitmoscow.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class UserSettingTo extends BaseTo{
    private Integer user;

    private Integer fileType;

    private Integer template;

    private Integer fileTemplate;

    private Integer sheetPosition;

    private String name;

    private String fileMask;

    private String qrAddInfo;

    private Integer billQuantity;

    private String orgName;

    private String orgInn;

    private String orgKpp;

    private String orgPayAcc;

    private String orgBank;

    private String orgBic;

    private String orgCorAcc;

    private String orgAddInfo;

    public UserSettingTo(Integer id, Integer user, Integer fileType, Integer template, Integer fileTemplate, Integer sheetPosition, String name, String fileMask, String qrAddInfo, Integer billQuantity, String orgName, String orgInn, String orgKpp, String orgPayAcc, String orgBank, String orgBic, String orgCorAcc, String orgAddInfo) {
        super(id);
        this.user = user;
        this.fileType = fileType;
        this.template = template;
        this.fileTemplate = fileTemplate;
        this.sheetPosition = sheetPosition;
        this.name = name;
        this.fileMask = fileMask;
        this.qrAddInfo = qrAddInfo;
        this.billQuantity = billQuantity;
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
