create table if not exists quizz_sc.user_response
(
    id varchar not null primary key,
    survey_id varchar,
    user_id varchar,
    submitted_at timestamp
);