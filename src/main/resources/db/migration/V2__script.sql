create table attached_file (id integer not null, url varchar(255), primary key (id));
create table issue_attached_files (issue_id integer not null, attached_files_id integer not null);
alter table if exists issue_attached_files drop constraint if exists UKen55lgya10i1pccr9f37diqji;
alter table if exists issue_attached_files add constraint UKen55lgya10i1pccr9f37diqji unique (attached_files_id);
alter table if exists issue_attached_files add constraint FKb0p866em040kxfr605w1d1jxr foreign key (attached_files_id) references attached_file;
alter table if exists issue_attached_files add constraint FKl2yohnd0xugh74shhhkdis2b9 foreign key (issue_id) references issue;
