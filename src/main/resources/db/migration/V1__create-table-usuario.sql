create table usuarios(

            id serial4 NOT NULL,
            nome varchar(100) not null,
            login varchar(100) not null unique,
            senha varchar(255) not null,

            primary key(id)

);
