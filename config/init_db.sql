drop table if exists file_counter_add_fields;
drop table if exists file_sum_add_fields;
drop table if exists file_main_fields;
drop table if exists file_sum_fields;
drop table if exists file_unique_fields;
drop table if exists file_counter_fields;
drop table if exists file_misc_fields;
drop table if exists field_type;
drop table if exists user_settings;
drop table if exists file_types;
drop table if exists users;
drop table if exists templates;
drop table if exists roles;
drop table if exists file_templates;
drop table if exists functions;
drop table if exists sheet_position;


create table sheet_position (
  id   serial primary key
  ,
  name text
);

create table functions (
  id   serial primary key
  ,
  name text
);

create table file_templates (
  id   serial primary key
  ,
  name text
);

create table roles (
  id   serial primary key
  ,
  name text
);

-- editable
create table users (
  id       serial primary key
  ,
  role_id  integer
  ,
  name     text
  ,
  password text
  ,
  email    text
  ,
  foreign key (role_id) references roles (id)
);

create table file_types (
  id   serial primary key
  ,
  name text
);

create table templates (
  id   serial primary key
  ,
  name text
);

-- editable
create table user_settings (
  id                serial primary key
  ,
  user_id           integer
  ,
  file_type_id      integer
  ,
  template_id       integer
  ,
  file_template_id  integer
  ,
  sheet_position_id integer
  ,
  file_mask         text
  ,
  qr_add_info       text
  ,
  bill_quantity     integer
  ,
  font_size         integer
  ,
  name              text
  ,
  org_name          text
  ,
  org_inn           text
  ,
  org_kpp           text
  ,
  org_pay_acc       text
  ,
  org_bank          text
  ,
  org_bic           text
  ,
  org_cor_acc       text
  ,
  org_add_info      text
  ,
  foreign key (user_id) references users (id) on delete cascade
  ,
  foreign key (file_type_id) references file_types (id) on delete cascade
  ,
  foreign key (template_id) references templates (id) on delete cascade
  ,
  foreign key (file_template_id) references file_templates (id) on delete cascade
  ,
  foreign key (sheet_position_id) references sheet_position (id) on delete cascade
);

-- editable
create table file_main_fields (
  id               serial primary key
  ,
  user_settings_id integer
  ,
  ls               text
  ,
  adr              text
  ,
  fio              text
  ,
  period           text
  ,
  sum              text
  ,
  kbk              text
  ,
  oktmo            text
  ,
  contract         text
  ,
  purpose          text
  ,
  ls_name          text
  ,
  adr_name         text
  ,
  fio_name         text
  ,
  period_name      text
  ,
  sum_name         text
  ,
  kbk_name         text
  ,
  oktmo_name       text
  ,
  contract_name    text
  ,
  purpose_name     text
  ,
  foreign key (user_settings_id) references user_settings (id) on delete cascade
);

-- alter table file_main_fields add column kbk text;
-- alter table file_main_fields add column oktmo text;
-- alter table file_main_fields add column contract text;
-- alter table file_main_fields add column purpose text;
--
-- alter table file_main_fields add column kbk_name text;
-- alter table file_main_fields add column oktmo_name text;
-- alter table file_main_fields add column contract_name text;
-- alter table file_main_fields add column purpose_name text;

-- editable
create table file_unique_fields (
  id               serial primary key
  ,
  user_settings_id integer
  ,
  name             text
  ,
  value            text
  ,
  foreign key (user_settings_id) references user_settings (id) on delete cascade
);

-- editable
create table file_counter_fields (
  id               serial primary key
  ,
  user_settings_id integer
  ,
  name             text
  ,
  value            text
  ,
  foreign key (user_settings_id) references user_settings (id) on delete cascade
);

--editable
create table file_misc_fields (
  id               serial primary key
  ,
  user_settings_id integer
  ,
  nazn             text
  ,
  dogovor          text
  ,
  kbk              text
  ,
  oktmo            text
  ,
  teacher_fio      text
  ,
  kinder_group     text
  ,
  child_fio        text
  ,
  class_num        text
  ,
  student_fio      text
  ,
  sum              text
  ,
  foreign key (user_settings_id) references user_settings (id) on delete cascade
);

create table file_sum_fields (
  id               serial primary key
  ,
  user_settings_id integer
  ,
  name             text
  ,
  value            text
  ,
  is_bold          boolean
  ,
  foreign key (user_settings_id) references user_settings (id)
);

create table file_sum_add_fields (
  id              serial primary key
  ,
  user_setting_id integer
  ,
  name            text
  ,
  value           text
  ,
  is_bold         boolean
  ,
  foreign key (user_setting_id) references user_settings (id) on delete cascade
);

create table file_counter_add_fields (
  id              serial primary key
  ,
  user_setting_id integer
  ,
  name            text
  ,
  value           text
  ,
  foreign key (user_setting_id) references user_settings (id) on delete cascade
);