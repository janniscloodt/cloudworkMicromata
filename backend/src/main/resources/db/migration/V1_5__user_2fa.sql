alter table users add column settings bigint not null default 0;
alter table users add column "2fa_key" varchar(255);