USE `socialnetdb`;

create table `users`
(
    `id`       varchar(50)  not null,
    `pwd_hash` text not null
);
