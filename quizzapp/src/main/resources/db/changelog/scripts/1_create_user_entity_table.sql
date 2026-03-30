create table if not exists quizz_sc.user_t
(
    id varchar not null primary key,
    login varchar not null unique,
    password  varchar not null
);