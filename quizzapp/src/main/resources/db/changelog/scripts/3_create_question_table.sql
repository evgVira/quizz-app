create table if not exists quizz_sc.question
(
    id varchar not null primary key,
    question_text varchar,
    order_index int,
    survey_id varchar
);