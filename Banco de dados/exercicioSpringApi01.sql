create database exercicioSpringApi01;
use exercicioSpringApi01;
show tables;

create table cliente (
idcliente	integer			primary key auto_increment,
nome 		varchar(150)	not null,
cpf			varchar(15) 	not null	unique,
email 		varchar(100)	not null
);

desc cliente;
select * from cliente;

desc usuario;
select * from usuario;




