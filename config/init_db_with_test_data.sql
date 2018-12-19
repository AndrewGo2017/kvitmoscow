insert into roles (name)
values ('ROLE_USER'),('ROLE_ADMIN');

-- select * from roles;
-- update roles set name = 'ROLE_ADMIN' where id = 2;

insert into  users(role_id, name, password, email)
values (1, '1', '1', 'email'), (1, 'berezina', '123', ''), (2, '2', '2', 'email'), (1, 'berezina', '123', '') ;

insert into functions(name)
values ('Формирование квитации'),('Формирование реестра задолженности');

insert into sheet_position(name)
values ('вертикальная'), ('горизонтальная');

insert into file_types(name)
values ('excel (.xls/.xlsx)'), ('text (.txt/.csv)');

insert into templates(name)
values ('Шаблон 1');

insert into file_templates(name)
values ('Шаблон 10_2'), ('Шаблон 1_7');

insert into user_settings(user_id, file_type_id, template_id, file_template_id, sheet_position_id, file_mask, bill_quantity, font_size, qr_add_info, name, org_name, org_inn, org_kpp, org_pay_acc, org_bank, org_bic, org_cor_acc, org_add_info)
values (1,1,1,1,1,'file_mask', 2, 0,'CATEGORY=1', 'Коммуналка', 'ООО УК "Гранд"', '7702380860', '770201001', '40702810300000204564', 'Филиал № 7701 Банка ВТБ (ПАО) г Москва', '044525745','30101810345250000745',''),
       (2,1,1,1,1,'file_mask', 2, 0,'', 'Коммуналка', 'ЖСК Березина', '7702125652', '77020100', '40703810838090102623', 'Сбербанк России ПАО г.Москва', '044525225','30101810400000000225','');

insert into file_main_fields(user_settings_id, ls, adr, fio, period, sum, ls_name, adr_name, fio_name, period_name, sum_name)
values (1, 'LS', 'ADR', 'FIO', 'PERIOD', 'SUM','Лицевой счет', 'Адрес', 'ФИО', 'Период', 'Сумма платежа'),
       (2, 'LS', 'ADR', 'FIO', 'PERIOD', 'SUM','Лицевой счет', 'Адрес', 'ФИО', 'Период', 'Сумма платежа');

insert into  file_unique_fields(user_settings_id, name, value)
values (2, 'PEREPLN', 'PEREPL');

insert into file_counter_fields(user_settings_id, name, value)
values (2, 'CN1', 'CV1'),
       (2, 'CN2', 'CV2');

insert into  file_sum_fields(user_settings_id, name, value, is_bold)
values (2, 'N1', 'S1', false),
       (2, 'N2', 'S2', false),
       (2, 'N3', 'S3', false),
       (2, 'N4', 'S4', true);

insert into  file_sum_add_fields(user_setting_id, name, value, is_bold)
values (2, 'Кол-во', 'K', false),
       (2, 'Тари', 'T', false),
       (2, 'Нач по тарифу', 'NN', false),
       (2, 'Льгота', 'L', false);

insert into  file_counter_add_fields(user_setting_id, name, value)
  values (2, 'Тариф', 'CT'),
       (2, 'Дата проверки', 'CDP'),
       (2, 'Дата тек пок', 'DTP'),
       (2, 'Пред пок', 'CP'),
       (2, 'Расход', 'CR');




