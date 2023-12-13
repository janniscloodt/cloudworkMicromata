alter table "user"
    add column created_at date not null default current_date;
alter table "user" rename to users;