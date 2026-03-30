alter table quizz_sc.user_response
drop column submitted_at;

alter table quizz_sc.user_response
add column start_try_time timestamp;

alter table quizz_sc.user_response
add column finish_try_time timestamp;