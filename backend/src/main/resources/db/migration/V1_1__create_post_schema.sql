create table "posts" (
    id bigserial primary key,
    user_id bigint not null,
    title varchar(255) not null,
    description text,
    published_at timestamp,
    foreign key (user_id) references "user"(id)
)