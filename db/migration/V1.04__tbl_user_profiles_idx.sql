USE `socialnetdb`;

create index idx_user_profiles_first_second_names
    on socialnetdb.user_profiles (first_name, second_name);

