create table user (
    firstname text,
    lastname text,
    email varchar(512),
    password_hash text,
    born date,
    level int,
    constraint pk_user primary key (email)
);

create table user_id (
    user_id int auto_increment,
    email varchar(512),
    constraint pk_user_id primary key (user_id),
    constraint fk_user_email foreign key (email) references user(email)
);

create table contaminated (
    user_id int,
    contaminated_at date,
    constraint fk_friend foreign key (user_id) references user_id(user_id)
);

create table activity (
    activity_id int auto_increment,
    start_at date,
    end_at date,
    city text,
    constraint pk_activity primary key (activity_id)
);

create table user_activity (
   user_id int,
   activity_id int,
   constraint fk_user_id_user_activity foreign key (user_id) references user_id(user_id),
   constraint fk_activity_id foreign key (activity_id) references activity(activity_id)
);

create table friends (
    user_1 int,
    user_2 int,
    constraint fk_friend_1 foreign key (user_1) references user_id(user_id),
    constraint fk_friend_2 foreign key (user_2) references user_id(user_id)
);

create table notification (
    notification_id int auto_increment,
    content text,
    constraint pk_notification_id primary key (notification_id)
);

create table user_notification (
    notification_id int,
    user_id int,
    constraint fk_user_id_user_notification foreign key (user_id) references user_id(user_id),
    constraint fk_notification_id foreign key (notification_id) references notification(notification_id)
)
