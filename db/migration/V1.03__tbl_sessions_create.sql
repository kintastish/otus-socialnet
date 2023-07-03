USE socialnetdb;

create table sessions
(
    user_id    varchar(50)  not null,
    session_id varchar(100) not null,
    started    datetime     not null,
    expiring   datetime     not null
);
