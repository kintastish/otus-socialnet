CREATE DATABASE socialnetdb;
CREATE USER 'db_user'@'%' IDENTIFIED BY 'db_pass_123';
GRANT ALL PRIVILEGES ON socialnetdb.* TO 'db_user'@'%';


