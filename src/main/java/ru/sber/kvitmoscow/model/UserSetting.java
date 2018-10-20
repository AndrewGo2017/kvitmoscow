package ru.sber.kvitmoscow.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user_settings")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class UserSetting extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NonNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "file_type_id")
    @NonNull
    private FileType fileType;

    @ManyToOne
    @JoinColumn(name = "template_id")
    @NonNull
    private Template template;

    @ManyToOne
    @JoinColumn(name = "file_template_id")
    @NonNull
    private FileTemplate fileTemplate;

    @Column(name = "name")
    @NotBlank(message = "Поле Наименование настроек не может быть пустым!")
    private String name;

    @Column(name = "org_name")
    @NotBlank(message = "Поле Наименование организации не может быть пустым!")
    private String orgName;

    @Column(name = "org_inn")
    @NotBlank(message = "Поле ИНН не может быть пустым!")
    private String orgInn;

    @Column(name = "org_kpp")
    private String orgKpp;

    @Column(name = "org_pay_acc")
    @NotBlank(message = "Поле Р/с не может быть пустым!")
    private String orgPayAcc;

    @Column(name = "org_bank")
    @NotBlank(message = "Поле Банк не может быть пустым!")
    private String orgBank;

    @Column(name = "org_bic")
    private String orgBic;

    @Column(name = "org_cor_acc")
    private String orgCorAcc;

    @Column(name = "org_add_info")
    private String orgAddInfo;

    public UserSetting(Integer id, User user, FileType fileType, Template template, FileTemplate fileTemplate, @NotBlank(message = "Поле Наименование настроек не может быть пустым!") String name, @NotBlank(message = "Поле Наименование организации не может быть пустым!") String orgName, @NotBlank(message = "Поле ИНН не может быть пустым!") String orgInn, String orgKpp, @NotBlank(message = "Поле Р/с не может быть пустым!") String orgPayAcc, @NotBlank(message = "Поле Банк не может быть пустым!") String orgBank, String orgBic, String orgCorAcc, String orgAddInfo) {
        super(id);
        this.user = user;
        this.fileType = fileType;
        this.template = template;
        this.fileTemplate = fileTemplate;
        this.name = name;
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
