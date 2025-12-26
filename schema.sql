
    create table user_roles (
        user_role smallint check ((user_role between 0 and 3)),
        user_id uuid not null
    );

    create table users (
        status smallint not null check ((status between 0 and 3)),
        created_at timestamp(6) with time zone,
        last_login_at timestamp(6) with time zone,
        id uuid not null,
        email varchar(255) not null unique,
        name varchar(255) not null,
        password varchar(255) not null,
        primary key (id)
    );

    alter table if exists user_roles 
       add constraint FKhfh9dx7w3ubf1co1vdev94g3f 
       foreign key (user_id) 
       references users;

    create table user_roles (
        user_role smallint check ((user_role between 0 and 3)),
        user_id uuid not null
    );

    create table users (
        status smallint not null check ((status between 0 and 3)),
        created_at timestamp(6) with time zone,
        last_login_at timestamp(6) with time zone,
        id uuid not null,
        name varchar(255) not null,
        password varchar(255) not null,
        uidx_user_email varchar(255) not null unique,
        primary key (id)
    );

    alter table if exists user_roles 
       add constraint FKhfh9dx7w3ubf1co1vdev94g3f 
       foreign key (user_id) 
       references users;

    create table user_roles (
        user_role smallint check ((user_role between 0 and 3)),
        user_id uuid not null
    );

    create table users (
        status smallint not null check ((status between 0 and 3)),
        created_at timestamp(6) with time zone,
        last_login_at timestamp(6) with time zone,
        id uuid not null,
        email varchar(255) not null unique,
        name varchar(255) not null,
        password varchar(255) not null,
        primary key (id)
    );

    alter table if exists user_roles 
       add constraint FKhfh9dx7w3ubf1co1vdev94g3f 
       foreign key (user_id) 
       references users;

    create table user_roles (
        user_role smallint check ((user_role between 0 and 3)),
        user_id uuid not null
    );

    create table users (
        status smallint not null check ((status between 0 and 3)),
        created_at timestamp(6) with time zone,
        last_login_at timestamp(6) with time zone,
        id uuid not null,
        email varchar(255) not null unique,
        name varchar(255) not null,
        password varchar(255) not null,
        primary key (id)
    );

    alter table if exists user_roles 
       add constraint FKhfh9dx7w3ubf1co1vdev94g3f 
       foreign key (user_id) 
       references users;

    create table user_roles (
        user_role smallint check ((user_role between 0 and 3)),
        user_id uuid not null
    );

    create table users (
        status smallint not null check ((status between 0 and 3)),
        created_at timestamp(6) with time zone,
        last_login_at timestamp(6) with time zone,
        id uuid not null,
        email varchar(255) not null unique,
        name varchar(255) not null,
        password varchar(255) not null,
        primary key (id)
    );

    alter table if exists user_roles 
       add constraint FKhfh9dx7w3ubf1co1vdev94g3f 
       foreign key (user_id) 
       references users;

    create table user_roles (
        user_role smallint check ((user_role between 0 and 3)),
        user_id uuid not null
    );

    create table users (
        status smallint not null check ((status between 0 and 3)),
        created_at timestamp(6) with time zone,
        last_login_at timestamp(6) with time zone,
        id uuid not null,
        email varchar(255) not null unique,
        name varchar(255) not null,
        password varchar(255) not null,
        primary key (id)
    );

    alter table if exists user_roles 
       add constraint FKhfh9dx7w3ubf1co1vdev94g3f 
       foreign key (user_id) 
       references users;

    create table user_roles (
        user_role smallint check ((user_role between 0 and 3)),
        user_id uuid not null
    );

    create table users (
        status smallint not null check ((status between 0 and 3)),
        created_at timestamp(6) with time zone,
        last_login_at timestamp(6) with time zone,
        id uuid not null,
        email varchar(255) not null unique,
        name varchar(255) not null,
        password varchar(255) not null,
        primary key (id)
    );

    alter table if exists user_roles 
       add constraint FKhfh9dx7w3ubf1co1vdev94g3f 
       foreign key (user_id) 
       references users;
