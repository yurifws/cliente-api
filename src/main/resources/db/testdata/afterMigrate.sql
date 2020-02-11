
set sql_safe_updates = 0;
set foreign_key_checks = 0;

delete from tb_estado;

insert into tb_estado (id, nome) values (1, 'Pernambuco');
insert into tb_estado (id, nome) values (2, 'São Paulo');
insert into tb_estado (id, nome) values (3, 'Acre');
insert into tb_estado (id, nome) values (4, 'Amapá');