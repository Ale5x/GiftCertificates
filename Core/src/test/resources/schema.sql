
CREATE SCHEMA online_store;

create table roles(
     id_roles bigint primary key auto_increment,
     name varchar(120)
);

create table statuses(
     id_status bigint primary key auto_increment,
     name varchar (120)
);

create table users(
     id_users bigint primary key auto_increment,
     id_status bigint,
     first_name varchar(120),
     last_name varchar(120),
     email varchar(120),
     create_date timestamp,
     update_date timestamp,
     password varchar (120)

);

create table users_roles(
    id_roles bigint,
    id_users bigint,
    constraint id_roles_foreign_key
    foreign key (id_roles) references roles(id_roles)
    on delete cascade on update cascade,
    constraint id_users_foreign_key
    foreign key (id_users) references users(id_users)
    on delete cascade on update cascade
);


create table orders(
     id_orders bigint primary key auto_increment,
     id_users bigint,
     cost decimal(8,2),
     purchase_time timestamp
);


create table tags(
     id_tags bigint primary key auto_increment,
     name varchar(120)
);


create table order_details(
     id bigint primary key auto_increment,
     price decimal(8,2),
     id_orders bigint,
     id_certificates bigint
);


create table gift_certificates(
    id_certificates bigint primary key auto_increment,
    name varchar(120),
    description varchar(120),
    price decimal(8,2),
    duration int,
    create_date timestamp,
    last_update_date timestamp,
    id_orders bigint
);


create table tags_gift_certificates(
    id_tags bigint,
    id_certificates bigint,
    constraint id_tags_foreign_key
    foreign key (id_tags) references tags(id_tags)
    on delete cascade on update cascade,
    constraint id_certificates_foreign_key
    foreign key (id_certificates) references gift_certificates(id_certificates)
    on delete cascade on update cascade
);

