USE `socialnetdb`;

create table `user_profiles`
(
    `id`          varchar(50) not null,
    `first_name`  varchar(50) not null,
    `second_name` varchar(50) not null,
    `birthdate`   date        not null,
    `biography`   text        not null,
    `city`        varchar(50) not null
);
