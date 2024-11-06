create table additional_attribute (id integer not null, string_value varchar(255), type_id integer, primary key (id));
create table additional_attribute_type (id integer not null, name varchar(255), primary key (id));
create table attached_file (id integer not null, file_path varchar(1000), primary key (id));
create table chat (is_closed boolean, issue_id integer not null, primary key (issue_id));
create table chat_members (chat_issue_id integer not null, members_display_name varchar(255) not null, primary key (chat_issue_id, members_display_name));
create table client (id integer not null, address varchar(255), email varchar(255), name varchar(255), phone_number varchar(255), primary key (id));
create table department (id integer not null, name varchar(255), primary key (id));
create table department_feedback (id integer not null, feedback varchar(2000), author_display_name varchar(255), message_id integer, primary key (id));
create table department_feedback_attached_files (department_feedback_id integer not null, attached_files_id integer not null);
create table employee (display_name varchar(255) not null, given_name varchar(255), mail varchar(255), samaccount_name varchar(255), sn varchar(255), primary key (display_name));
create table good (id integer not null, description varchar(2000), primary key (id));
create table issue (id integer not null, doc_date timestamp(6), doc_number varchar(255), issue_description varchar(2000), issue_result varchar(2000), issued_demands varchar(2000), related_doc_from_sigma varchar(255) array, status varchar(255) check (status in ('NEW','INPROGRESS','PENDINGRESULT','CLOSED')), chat_issue_id integer, client_id integer, issued_department_id integer, subject_id integer, type_id integer, primary key (id));
create table issue_additional_attributes (issue_id integer not null, additional_attributes_id integer not null);
create table issue_attached_files (issue_id integer not null, attached_files_id integer not null);
create table issue_department_feedbacks (issue_id integer not null, department_feedbacks_id integer not null, primary key (issue_id, department_feedbacks_id));
create table issue_issue_attributes (issue_id integer not null, issue_attributes_id integer not null);
create table issue_issued_employees (issue_id integer not null, issued_employees_display_name varchar(255) not null, primary key (issue_id, issued_employees_display_name));
create table issue_attribute (id integer not null, is_deprecated boolean, name varchar(255), primary key (id));
create table issue_type (id integer not null, name varchar(255), primary key (id));
create table message (id integer not null, created_at timestamp(6), author_display_name varchar(255), chat_issue_id integer, content_id integer, primary key (id));
create table message_statuses (message_id integer not null, statuses_id integer not null, primary key (message_id, statuses_id));
create table message_content (id integer not null, is_answer boolean, text_message varchar(1000), primary key (id));
create table message_content_attached_files (message_content_id integer not null, attached_files_id integer not null);
create table message_status (id integer not null, notification_created boolean, status integer, employee_display_name varchar(255), message_id integer, primary key (id));
create table notification (id integer not null, created_at timestamp(6), is_read boolean, is_sent boolean, policy varchar(255) check (policy in ('ONLYINAPP','INAPP','BOTH','ONLYMAIL')), ref_id integer, text varchar(255), type varchar(255) check (type in ('newIssue','issueStatusChanged','chatClosed','employeeAddedToIssue','departmentFeedbackAddedToIssue','internalInfoChanged','resultAdded','employeeAddedToChat','newMessageToChat')), employee_display_name varchar(255), primary key (id));
create table surpressed_chat (id integer not null, chat_id integer, is_surpressed boolean, employee_display_name varchar(255), primary key (id));
create table users (id integer not null, email varchar(255), password varchar(255), roles smallint array, samaccount_name varchar(255), username varchar(255), department_id integer, primary key (id));
create table vehicle (id integer not null, description varchar(2000), vin varchar(255), primary key (id));
alter table if exists additional_attribute_type drop constraint if exists UKgwbhi3act83kxlbf6guuwfydc;
alter table if exists additional_attribute_type add constraint UKgwbhi3act83kxlbf6guuwfydc unique (name);
alter table if exists client drop constraint if exists UKjgbii7eaw72fc9llajcdeyjb;
alter table if exists client add constraint UKjgbii7eaw72fc9llajcdeyjb unique (phone_number);
alter table if exists department drop constraint if exists UK1t68827l97cwyxo9r1u6t4p7d;
alter table if exists department add constraint UK1t68827l97cwyxo9r1u6t4p7d unique (name);
alter table if exists department_feedback drop constraint if exists UKf2gnfc5yh4pltlw27gjv14jbw;
alter table if exists department_feedback add constraint UKf2gnfc5yh4pltlw27gjv14jbw unique (message_id);
alter table if exists department_feedback_attached_files drop constraint if exists UKkeviux00idnau2i7v34700xec;
alter table if exists department_feedback_attached_files add constraint UKkeviux00idnau2i7v34700xec unique (attached_files_id);
alter table if exists employee drop constraint if exists UK6nit1ynrjkrd1hp1c73d60rn3;
alter table if exists employee add constraint UK6nit1ynrjkrd1hp1c73d60rn3 unique (mail);
alter table if exists issue drop constraint if exists UKg7bkfqhaelwhlpumydohuemx1;
alter table if exists issue add constraint UKg7bkfqhaelwhlpumydohuemx1 unique (chat_issue_id);
alter table if exists issue_attached_files drop constraint if exists UKen55lgya10i1pccr9f37diqji;
alter table if exists issue_attached_files add constraint UKen55lgya10i1pccr9f37diqji unique (attached_files_id);
alter table if exists issue_department_feedbacks drop constraint if exists UKjoc3mnr3w3ghvl3sm5b06k5s9;
alter table if exists issue_department_feedbacks add constraint UKjoc3mnr3w3ghvl3sm5b06k5s9 unique (department_feedbacks_id);
alter table if exists issue_attribute drop constraint if exists UKdm64v2dc8y67hy80pu4ppykpy;
alter table if exists issue_attribute add constraint UKdm64v2dc8y67hy80pu4ppykpy unique (name);
alter table if exists issue_type drop constraint if exists UKff22b26a31m6rkn70y5h208ju;
alter table if exists issue_type add constraint UKff22b26a31m6rkn70y5h208ju unique (name);
alter table if exists message drop constraint if exists UK3bi27y3fkv7etqlief9l671jn;
alter table if exists message add constraint UK3bi27y3fkv7etqlief9l671jn unique (content_id);
alter table if exists message_statuses drop constraint if exists UK3i1ivg2thu5dg7uam63rf7n1d;
alter table if exists message_statuses add constraint UK3i1ivg2thu5dg7uam63rf7n1d unique (statuses_id);
alter table if exists message_content_attached_files drop constraint if exists UKfjgs8chuxu3332kpw8g4r616l;
alter table if exists message_content_attached_files add constraint UKfjgs8chuxu3332kpw8g4r616l unique (attached_files_id);
alter table if exists users drop constraint if exists UKfnranlqhubvw04boopn028e6;
alter table if exists users add constraint UKfnranlqhubvw04boopn028e6 unique (email, username);
create sequence additional_attribute_seq start with 1 increment by 50;
create sequence additional_attribute_type_seq start with 1 increment by 50;
create sequence attached_file_seq start with 1 increment by 50;
create sequence client_seq start with 1 increment by 50;
create sequence department_feedback_seq start with 1 increment by 50;
create sequence department_seq start with 1 increment by 50;
create sequence issue_attribute_seq start with 1 increment by 50;
create sequence issue_seq start with 1 increment by 50;
create sequence issue_type_seq start with 1 increment by 50;
create sequence message_content_seq start with 1 increment by 50;
create sequence message_seq start with 1 increment by 50;
create sequence message_status_seq start with 1 increment by 50;
create sequence notification_seq start with 1 increment by 50;
create sequence subject_seq start with 1 increment by 50;
create sequence surpressed_chat_seq start with 1 increment by 50;
create sequence users_seq start with 1 increment by 50;
alter table if exists additional_attribute add constraint FKn5kflrsphv2t81p9en1jwvego foreign key (type_id) references additional_attribute_type;
alter table if exists chat add constraint FK78k2dtnk89hkpnx65r07m557n foreign key (issue_id) references issue;
alter table if exists chat_members add constraint FKrcr9t0p1l6u2d32l3k4y4ww58 foreign key (members_display_name) references employee;
alter table if exists chat_members add constraint FK58rwp0l6ahh01o582ah1j0irc foreign key (chat_issue_id) references chat;
alter table if exists department_feedback add constraint FK5e20xjx2aqe294d227qkabu5h foreign key (author_display_name) references employee;
alter table if exists department_feedback add constraint FKfuuwv1xmvxfpolddvqymyac6l foreign key (message_id) references message;
alter table if exists department_feedback_attached_files add constraint FKerk1rdbks94ti33xn8y6damh foreign key (attached_files_id) references attached_file;
alter table if exists department_feedback_attached_files add constraint FKfbamgvfm1gidwjlv4790wsoke foreign key (department_feedback_id) references department_feedback;
alter table if exists issue add constraint FK1q1svn5iwqwm0ieha7ilx97w8 foreign key (chat_issue_id) references chat;
alter table if exists issue add constraint FK9wuxdmqmwsangefmvkslserp8 foreign key (client_id) references client;
alter table if exists issue add constraint FKeecun8byou4ifm1m5kwu9hgrs foreign key (issued_department_id) references department;
alter table if exists issue add constraint FKbt4c89biujijnh5tykmf7w3b4 foreign key (type_id) references issue_type;
alter table if exists issue_additional_attributes add constraint FK5dbqa984j2wrocx37h8htsa9c foreign key (additional_attributes_id) references additional_attribute;
alter table if exists issue_additional_attributes add constraint FKg55cof72bkkjxg6vkkint1w2b foreign key (issue_id) references issue;
alter table if exists issue_attached_files add constraint FKb0p866em040kxfr605w1d1jxr foreign key (attached_files_id) references attached_file;
alter table if exists issue_attached_files add constraint FKl2yohnd0xugh74shhhkdis2b9 foreign key (issue_id) references issue;
alter table if exists issue_department_feedbacks add constraint FKlxb946m4mhtcp8rhnldbl3qor foreign key (department_feedbacks_id) references department_feedback;
alter table if exists issue_department_feedbacks add constraint FKcen1c99ekk0hc36c78adm3acb foreign key (issue_id) references issue;
alter table if exists issue_issue_attributes add constraint FKwc24c8wwopvhc755wfpl8j82 foreign key (issue_attributes_id) references issue_attribute;
alter table if exists issue_issue_attributes add constraint FKad1pixg3pi7b95ri9an0oip51 foreign key (issue_id) references issue;
alter table if exists issue_issued_employees add constraint FKckcbd4n1yvcbp2qfg8womg6i3 foreign key (issued_employees_display_name) references employee;
alter table if exists issue_issued_employees add constraint FKrm2uyhovykwr6m3mjo2kp0biq foreign key (issue_id) references issue;
alter table if exists message add constraint FKd3stxyx3nnsgtgj3vaabvmnon foreign key (author_display_name) references employee;
alter table if exists message add constraint FKmnyeqihudyfu0poausw4a90g foreign key (chat_issue_id) references chat;
alter table if exists message add constraint FKh4l1xd80af4pqrla73jgvo9y4 foreign key (content_id) references message_content;
alter table if exists message_statuses add constraint FKnclnnnn918fwibn8ik1gw0f05 foreign key (statuses_id) references message_status;
alter table if exists message_statuses add constraint FK65y8pd0v4ofutb0umccij4l67 foreign key (message_id) references message;
alter table if exists message_content_attached_files add constraint FKi4vlj3454n75a30fj29i0bj4a foreign key (attached_files_id) references attached_file;
alter table if exists message_content_attached_files add constraint FKndqnhv7unyu9lnbiortlwhwpe foreign key (message_content_id) references message_content;
alter table if exists message_status add constraint FKhal295sop7dy5wpfv4nvei3ym foreign key (employee_display_name) references employee;
alter table if exists message_status add constraint FKqboxuo620r6qmh6ds85x5bt3l foreign key (message_id) references message;
alter table if exists notification add constraint FKjr4rmbulqw0hpx425r4uy581q foreign key (employee_display_name) references employee;
alter table if exists surpressed_chat add constraint FKpndcerxu6s7w8pl6bgcn1qflo foreign key (employee_display_name) references employee;
alter table if exists users add constraint FKfi832e3qv89fq376fuh8920y4 foreign key (department_id) references department;
