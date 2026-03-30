create table if not exists quizz_sc.option
(
    id varchar not null primary key,
    option_text varchar,
    order_index int,
    is_correct boolean,
    question_id varchar
);