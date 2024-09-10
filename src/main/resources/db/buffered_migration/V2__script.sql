alter table if exists client drop constraint if exists UKbfgjs3fem0hmjhvih80158x29;
alter table if exists client add constraint UKbfgjs3fem0hmjhvih80158x29 unique (phone_number);