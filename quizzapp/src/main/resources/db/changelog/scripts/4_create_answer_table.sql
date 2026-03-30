create table if not exists quizz_sc.answer
(
    id varchar not null primary key,
    user_response_id varchar,
    option_id varchar
);