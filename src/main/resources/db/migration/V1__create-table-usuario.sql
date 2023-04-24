create table usuarios(

            id serial4 NOT NULL,
            login varchar(100) not null,
            senha varchar(255) not null unique,

            primary key(id)

);
