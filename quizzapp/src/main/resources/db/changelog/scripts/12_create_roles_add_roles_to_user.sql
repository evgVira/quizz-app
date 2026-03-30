insert into quizz_sc.role_t (id, role_name)
values ('63a23a7b-4754-4c7a-927a-e6f7c1b8420b', 'ROLE_USER'),
       ('5b8b13c1-203e-4141-a229-a4e25e05f1da', 'ROLE_ADMIN');

insert into quizz_sc.users_roles (user_id, role_id)
values ('00000000000', '5b8b13c1-203e-4141-a229-a4e25e05f1da');