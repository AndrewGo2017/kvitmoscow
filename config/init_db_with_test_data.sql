insert into roles (name)
values ('USER');

insert into  users(role_id, name, password, email)
values (1, '1', '1', 'emaiil');

insert into functions(name)
values ('Формирование квитации'),('Формирование реестра задолженности');

insert into sheet_position(name)
values ('вертикальная'), ('горизонтальная');

insert into file_types(name)
values ('excel (.xls/.xlsx)'), ('text (.txt/.csv)');

insert into templates(name)
values ('Шаблон 1');

insert into file_templates(name)
values ('Шаблон 10_2');

insert into user_settings(user_id, file_type_id, template_id, file_template_id, sheet_position_id, file_mask, bill_quantity, qr_add_info, name, org_name, org_inn, org_kpp, org_pay_acc, org_bank, org_bic, org_cor_acc, org_add_info)
values (1,1,1,1,1,'file_mask', 2,'CATEGORY=1', 'Коммуналка', 'ТСЖ Москва река', '7707707700', '770410001', '407028100450000654987', 'ПАО Сбербанк', '045209672','40101001010110101010','доп информация 202020');

insert into file_main_fields(user_settings_id, ls, adr, fio, period, sum, ls_name, adr_name, fio_name, period_name, sum_name)
values (1, 'LS', 'ADR', 'FIO', 'PERIOD', 'SUM','Лицевой счет', 'Адрес', 'ФИО', 'Период', 'Сумма платежа');

-- insert into  file_unique_fields(user_settings_id, name, value)
-- values (1, 'DOP_NAME', 'DOP_VAL');

-- insert into file_counter_fields(user_settings_id, name, mesure, tariff, current, previous, consumption)
-- values (1, 'CNAME1', 'MEASURE', 'TARIFF1', 'CURRENT1', 'PREVIOUS1', 'CON1'),
--        (1, 'CNAME2', 'MEASURE', 'TARIFF2', 'CURRENT2', 'PREVIOUS2', 'CON2');

insert into  file_sum_fields(user_settings_id, name, value)
values (1, 'U1', 'S1'),
       (1, 'U2', 'S2');

insert into  file_sum_add_fields(user_setting_id, name, value, is_bold)
values (1, 'Тариф', 'TAR', false),
       (1, 'Ед изм', 'MET', false);




