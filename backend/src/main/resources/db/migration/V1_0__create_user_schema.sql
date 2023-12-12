CREATE TABLE IF NOT EXISTS "user" (

    id BIGSERIAL PRIMARY KEY,
    name varchar(20) NOT NULL,
    email varchar(50),
    password varchar(50) NOT NULL
)