create table if not exists quizz_sc.users_roles
(
    user_id varchar not null,
    role_id varchar not null,
    primary key (user_id, role_id),
    foreign key (user_id) references quizz_sc.user_t (id),
    foreign key (role_id) references quizz_sc.role_t (id)
);