create table if not exists quizz_sc.survey
(
    id varchar not null primary key,
    title varchar,
    description varchar,
    create_dt timestamp,
    update_dt timestamp,
    is_active boolean,
    created_by_id varchar
);