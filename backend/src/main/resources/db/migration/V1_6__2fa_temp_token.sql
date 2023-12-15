create table user_temp_token (
    token varchar(255) primary key,
    user_id bigint,
    foreign key (user_id) references users(id)
);