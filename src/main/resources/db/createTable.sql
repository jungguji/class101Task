CREATE TABLE Product (
    type varchar(31) not null,
    id integer generated by default as identity,
    name varchar(255),
    price integer,
    stock integer,
    primary key (id)
);