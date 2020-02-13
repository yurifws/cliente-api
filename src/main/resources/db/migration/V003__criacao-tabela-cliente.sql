create table tb_cliente (
 	ID bigint(20) not null auto_increment,
  	nome varchar(255) not null,
	data_nascimento date not null,
  	idade int(11) not null,
  	sexo char(1) not null,
  	cidade_id bigint(20) not null,
   	data_atualizacao datetime not null,
   	data_cadastro datetime not null,
    primary key(id)
) engine=InnoDB default charset=utf8;

alter table tb_cliente add constraint fk_cliente_cidade
	foreign key (cidade_id) references tb_cidade (id);
