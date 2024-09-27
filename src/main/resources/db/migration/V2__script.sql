create table chat_members (chat_issue_id integer not null, members_id integer not null);
alter table if exists chat_members add constraint FKjqn0nl3gjvy9whj9op521e71r foreign key (members_id) references employee;
alter table if exists chat_members add constraint FK58rwp0l6ahh01o582ah1j0irc foreign key (chat_issue_id) references chat;
