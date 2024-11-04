alter table if exists message_status add column notification_created boolean;
create table surpressed_chat (id integer not null, chat_id integer, is_surpressed boolean, employee_display_name varchar(255), primary key (id));
create sequence surpressed_chat_seq start with 1 increment by 50;
alter table if exists surpressed_chat add constraint FKpndcerxu6s7w8pl6bgcn1qflo foreign key (employee_display_name) references employee;
