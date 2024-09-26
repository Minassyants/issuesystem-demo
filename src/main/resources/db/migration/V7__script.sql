alter table if exists message add column chat_id integer;
alter table if exists message add constraint FKmejd0ykokrbuekwwgd5a5xt8a foreign key (chat_id) references chat;
