
CREATE SCHEMA cademp AUTHORIZATION teste;

-- DROP TABLE cademp.empresa;
CREATE TABLE cademp.empresa (
	id int4 NOT NULL,
	cnpj varchar(14) NOT NULL,
	nome varchar(50) NOT NULL,
	razao_social varchar(40) NOT NULL,
	contato varchar(40) NOT NULL,
	email varchar(40) NOT NULL,
	id_matriz int4 NULL,
	tipo varchar(255) NULL,
	id_endereco int4 NULL,
	CONSTRAINT empresa_pk PRIMARY KEY (id)
);

-- DROP TABLE cademp.endereco;

CREATE TABLE cademp.endereco (
	id int4 NOT NULL,
	cep varchar(10) NOT NULL,
	estado varchar(40) NOT NULL,
	bairro varchar(40) NOT NULL,
	cidade varchar(40) NOT NULL,
	logradouro varchar(40) NOT NULL,
	complemento varchar(40) NULL,
	CONSTRAINT endereco_pk PRIMARY KEY (id)
);

-- DROP SEQUENCE cademp.seq_id_empresa;

CREATE SEQUENCE cademp.seq_id_empresa
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-- DROP SEQUENCE cademp.seq_id_endereco;

CREATE SEQUENCE cademp.seq_id_endereco
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
