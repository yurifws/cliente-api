
set sql_safe_updates = 0;
set foreign_key_checks = 0;

delete from tb_cidade;
delete from tb_estado;

set foreign_key_checks = 1;

alter table tb_cidade auto_increment = 1;
alter table tb_estado auto_increment = 1;

insert into tb_estado (id, nome) values (1, 'Pernambuco');
insert into tb_estado (id, nome) values (2, 'São Paulo');
insert into tb_estado (id, nome) values (3, 'Acre');
insert into tb_estado (id, nome) values (4, 'Amapá');

insert into tb_cidade (id, nome, estado_id) values (1, 'Recife', 1);
insert into tb_cidade (id, nome, estado_id) values (2, 'São Paulo', 2);
insert into tb_cidade (id, nome, estado_id) values (3, 'Jaboatao dos Guararapes', 1);
insert into tb_cidade (id, nome, estado_id) values (4, 'Goiana', 1);