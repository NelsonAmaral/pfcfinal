CREATE TABLE RESTRICAO (
  RESTRICAO_ID SERIAL PRIMARY KEY,
  RESTRICAO_NOME TEXT NOT NULL,
  RESTRICAO_TIPO TEXT NOT NULL,
  RESTRICAO_STATUS BOOLEAN NOT NULL
);

CREATE TABLE ENDERECO (
  ENDERECO_ID SERIAL primary key,
  ENDERECO_LOGADOURO TEXT NOT NULL,
  ENDERECO_NUMERO INTEGER NOT NULL,
  ENDERECO_CIDADE TEXT NOT NULL,
  ENDERECO_BAIRRO TEXT NOT NULL,
  ENDERECO_COMPLEMENTO TEXT,
  ENDERECO_CEP VARCHAR NOT NULL,
  ENDERECO_UF TEXT NOT NULL,
  ENDERECO_STATUS boolean not null
);

CREATE TABLE usuario (
  USUARIO_ID serial primary key,
  USUARIO_NOME TEXT NOT NULL,
  USUARIO_IDADE INTEGER NOT NULL,
  USUARIO_CPF TEXT,
  USUARIO_RG TEXT NOT NULL,
  USUARIO_SENHA varchar,
  USUARIO_TELEFONE BIGINT NOT NULL,
  USUARIO_CELULAR BIGINT NOT NULL,
  USUARIO_TIPOSANGUE TEXT NOT NULL,
  USUARIO_PESO NUMERIC NOT NULL,
  USUARIO_ALTURA NUMERIC NOT NULL,
  USUARIO_NASCIMENTO DATE NOT NULL,
  USUARIO_DATACADASTRO date NOT NULL,
  USUARIO_RESPONSAVEL INT,
  USUARIO_STATUS boolean NOT NULL,
  USUARIO_EMAIL TEXT,
  FK_ENDERECO INT NOT NULL,
  CONSTRAINT USUARIO_HAS_ENDERECO FOREIGN KEY (FK_ENDERECO) REFERENCES ENDERECO (ENDERECO_ID)
);

CREATE TABLE POSTO (
  POSTO_ID SERIAL PRIMARY KEY,
  POSTO_NOME TEXT NOT NULL,
  POSTO_TELEFONE INTEGER NOT NULL,
  FK_ENDERECO INTEGER NOT NULL,
  POSTO_ATIVO boolean not null,
  CONSTRAINT POSTO_HAS_ENDERECO foreign key (FK_ENDERECO) references ENDERECO (ENDERECO_ID)
);

CREATE TABLE FUNCIONARIO (
  FUNCIONARIO_ID SERIAL PRIMARY KEY,
  FUNCIONARIO_CONFEN VARCHAR NOT NULL,
  FUNCIONARIO_SENHA VARCHAR NOT NULL,
  FUNCIONARIO_ACESSO TEXT NOT NULL,
  FUNCIONARIO_STATUS BOOLEAN NOT NULL,
  FK_USUARIO INT NOT NULL,
  FK_POSTO INT NOT NULL,
  CONSTRAINT FUNCIONARIO_HAS_USUARIO FOREIGN KEY (FK_USUARIO) REFERENCES USUARIO (USUARIO_ID),
  CONSTRAINT FUNCIONARIO_HAS_POSTO FOREIGN KEY (FK_POSTO) REFERENCES POSTO (POSTO_ID)
  );

CREATE TABLE VACINA (
  VACINA_ID SERIAL PRIMARY KEY,
  VACINA_NOME TEXT NOT NULL,
  VACINA_TIPO TEXT NOT NULL,
  VACINA_STATUS BOOLEAN NOT NULL,
  VACINA_CALENDARIOOB BOOLEAN NOT NULL,
  Vacina_descricao VARCHAR
);

CREATE TABLE CALENDARIO_OBRIGATORIO(
  CALENDARIOOB_ID SERIAL PRIMARY KEY,
  CALENDARIOOB_COMENTARIO VARCHAR(150),
  FK_FUNCIONARIO INT NOT NULL, 
  FK_VACINA INT NOT NULL,
  CALENDARIOOB_DATACADASTRO DATE NOT NULL,
  CALENDARIOOB_HORACADASTRO TIME NOT NULL, 
  CALENDARIOOB_STATUS BOOLEAN NOT NULL,
  CALENDARIOOB_TOTALDOSES INT NOT NULL,
  CONSTRAINT calendarioOB_has_funcionario FOREIGN KEY (FK_FUNCIONARIO) REFERENCES FUNCIONARIO (FUNCIONARIO_ID),
  CONSTRAINT calendarioOB_has_vacina FOREIGN KEY (FK_Vacina) REFERENCES VACINA (VACINA_ID)
);

CREATE TABLE INTERVALO_VACINACAO(
  FK_CALENDARIOOB INT NOT NULL,
  INTERVALOV_DOSE INT NOT NULL,
  INTERVALOV_DIAS INT NOT NULL,
  INTERVALOV_STATUS boolean not null,
  constraint intervalov_has_calendarioob foreign key (FK_CALENDARIOOB) references calendario_obrigatorio (CALENDARIOOB_ID)
);

CREATE TABLE VACINA_HAS_RESTRICAO (
  FK_RESTRICOES INTEGER NOT NULL,
  FK_VACINA INTEGER NOT NULL,
  Status BOOLEAN NOT NULL,
  CONSTRAINT VACINA_HAS_RESTRICAO FOREIGN KEY (FK_RESTRICOES) REFERENCES RESTRICAO (RESTRICAO_ID),
  CONSTRAINT RESTRICAO_HAS_VACINA FOREIGN KEY (FK_VACINA) REFERENCES VACINA (VACINA_ID)
);

CREATE TABLE USUARIO_RESPONSAVEL (
   USUARIO_RESPONSAVEL INT NOT NULL,
   USUARIO_DEPENDENTE INT NOT NULL,
   CONSTRAINT USUARIO_HAS_RESPONSAVEL FOREIGN KEY (USUARIO_RESPONSAVEL) REFERENCES USUARIO (USUARIO_ID),
   CONSTRAINT USUARIO_HAS_DEPENDENTE FOREIGN KEY (USUARIO_DEPENDENTE) REFERENCES USUARIO (USUARIO_ID)   
);

CREATE TABLE CAMPANHA (
  CAMPANHA_ID serial PRIMARY KEY,
  CAMPANHA_NOME TEXT NOT NULL,
  CAMPANHA_INICIO DATE NOT NULL,
  CAMPANHA_FINAL DATE ,
  CAMPANHA_PREVISTA DATE NOT NULL,
  CAMPANHA_OBS TEXT,
  FK_VACINA INTEGER NOT NULL,
  CAMPANHA_STATUS BOOLEAN NOT NULL,
  CONSTRAINT CAMPANHA_HAS_VACINA FOREIGN KEY (FK_VACINA) REFERENCES VACINA (VACINA_ID)
);

CREATE TABLE ESTADO(
  ESTADO_ID SERIAL PRIMARY KEY,
  ESTADO_NOME TEXT NOT NULL
);

CREATE TABLE CIDADE(
  CIDADE_ID SERIAL PRIMARY KEY,
  CIDADE_NOME TEXT NOT NULL,
  CIDADE_ESTADO INTEGER,
  CONSTRAINT FK_ESTADO foreign key (CIDADE_ESTADO) references ESTADO (ESTADO_ID)
  
);

CREATE TABLE ESTADO_CAMPANHA(
    CAMPANHA_ID INTEGER,
    CIDADE_ID INTEGER,
    CONSTRAINT FK_CAMPANHA foreign key (CAMPANHA_ID) references CAMPANHA (CAMPANHA_ID),
    CONSTRAINT FK_CIDADE foreign key (CIDADE_ID) references CIDADE (CIDADE_ID)
);

CREATE TABLE usuario_has_restricao (
  FK_USUARIO INTEGER NOT NULL,
  FK_RESTRICAO INTEGER NOT NULL,
  STATUS BOOLEAN NOT NULL,
  CONSTRAINT usuario_has_restricao_FK1 foreign key (FK_USUARIO) references USUARIO (USUARIO_ID),
  CONSTRAINT usuario_has_restricao_FK2 foreign key (FK_RESTRICAO) references RESTRICAO (RESTRICAO_ID)
);

CREATE TABLE CARDENETA_USUARIO(
  FK_USUARIO int not null,
  FK_CALENDARIOOB int,
  FK_CAMPANHA int,
  FK_FUNCIONARIO int not null,
  FK_VACINA int,
  FK_POSTO int,
  CADERNETA_DOSE int,
  CADERNETA_DATA date not null,
  CADERNETA_HORA time not null,
  DESCRICAO varchar(300),
  CONSTRAINT CARDENETA_FK_POSTO FOREIGN KEY (FK_POSTO) REFERENCES POSTO (POSTO_ID), 
  CONSTRAINT CARDENETA_FK_VACINA FOREIGN KEY (FK_VACINA) REFERENCES VACINA (VACINA_ID),                                          
  CONSTRAINT CARDENETA_FK_USUARIO FOREIGN KEY (FK_USUARIO) REFERENCES USUARIO (USUARIO_ID),
  CONSTRAINT CARDENETA_FK_CALENDARIOOB FOREIGN KEY (FK_CALENDARIOOB) REFERENCES CALENDARIO_OBRIGATORIO (calendarioOB_id),
  CONSTRAINT CARDENETA_FK_CAMPANHA FOREIGN KEY (FK_CAMPANHA) REFERENCES CAMPANHA (CAMPANHA_ID),
  CONSTRAINT CARDENETA_FK_FUNCIONARIO FOREIGN KEY (FK_FUNCIONARIO) REFERENCES FUNCIONARIO (FUNCIONARIO_ID)
);

CREATE EXTENSION pgcrypto

