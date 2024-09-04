create table employee (id integer not null, given_name varchar(255), mail varchar(255), sn varchar(255), primary key (id));
alter table if exists issue add column issued_employee_id integer;
alter table if exists employee drop constraint if exists UK6nit1ynrjkrd1hp1c73d60rn3;
alter table if exists employee add constraint UK6nit1ynrjkrd1hp1c73d60rn3 unique (mail);
create sequence employee_seq start with 1 increment by 50;
alter table if exists issue add constraint FKfpb9cu9padavowgv4516ig4ic foreign key (issued_employee_id) references employee;
