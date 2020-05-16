INSERT INTO endereco(endereco_logadouro, endereco_numero, endereco_cidade, endereco_bairro, endereco_complemento, endereco_cep, endereco_uf, endereco_status)
VALUES ('Estrada do Pavoeiro', 15, 'Suzano', 'Clube dos Oficiais', 'casa amarela', 08635445, 'sp', true);

INSERT INTO usuario(usuario_nome, usuario_idade, usuario_cpf, usuario_rg, usuario_senha, 
					usuario_telefone, usuario_celular, usuario_tiposangue, usuario_peso, 
					usuario_altura, usuario_nascimento, usuario_datacadastro, usuario_responsavel, usuario_status, usuario_email, 
					fk_endereco) 
					VALUES ('Victor Aguiar', 22, '48496535886', '5044397162', crypt('Thucaz1997', gen_salt('bf', 8)), 47439274, 11961889917, 'A', 4, 4, '1997-08-29', '2018-01-01', 0, true, 'victoraguiar.umc@gmail.com', 1);

INSERT INTO posto (posto_nome, posto_telefone, fk_endereco, posto_ativo) values ('Posto de Suzano', 1143729392, 1, true)

INSERT INTO funcionario(funcionario_confen, funcionario_senha, funcionario_acesso, funcionario_status, 
						fk_usuario, fk_posto) 
VALUES (3, crypt('Victor1997', gen_salt('bf', 8)), 
		'ADMINISTRADOR', true, (SELECT usuario_id FROM usuario WHERE usuario_id=1), 1);
