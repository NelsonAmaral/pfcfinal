/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editsor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Cidade;
import model.Endereco;
import model.Restricao;
import model.Usuario;
import util.ConectaBanco;

/**
 *
 * @author nelson_amaral
 */
public class UsuarioDAO {

    private final String CADASTRA_NOVO_USUARIO = "INSERT INTO usuario(usuario_nome, usuario_idade, usuario_cpf, usuario_rg, usuario_senha, usuario_telefone, usuario_celular, usuario_tiposangue, usuario_peso, usuario_altura, usuario_nascimento,usuario_responsavel, usuario_status,usuario_email, fk_endereco, usuario_datacadastro)  VALUES (?,?, ?, ?,crypt(?, gen_salt('bf', 8)), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING usuario_id";
    private final String AUTENTICA_USUARIO = "SELECT * FROM usuario WHERE usuario_rg = ? AND usuario_senha = crypt(?, usuario_senha) AND usuario_status=? AND usuario_responsavel=?";
    private final String DELETA_USUARIO = "UPDATE usuario SET usuario_status = ? WHERE usuario_id = ? RETURNING usuario_status";
    private final String UPDATE_USUARIO = "UPDATE usuario SET usuario_nome=?, usuario_idade=?, usuario_cpf=?, usuario_rg=?, usuario_telefone=?, usuario_celular=?, usuario_tiposangue=?, usuario_peso=?, usuario_altura=?, usuario_nascimento=?, usuario_responsavel=? ,usuario_email=?, fk_endereco=? WHERE usuario_id=?;";
    private final String BUSCAR_USUARIO_UNICO = "SELECT u.usuario_id, u.usuario_nome, u.usuario_idade, u.usuario_cpf, u.usuario_rg, u.usuario_telefone, u.usuario_celular, u.usuario_tiposangue, u.usuario_peso, u.usuario_altura, u.usuario_nascimento, u.usuario_responsavel, u.usuario_status, u.usuario_email, u.usuario_datacadastro, e.endereco_id, e.endereco_logadouro, e.endereco_numero, e.endereco_cidade, e.endereco_bairro, e.endereco_complemento, e.endereco_cep, e.endereco_uf FROM usuario u, endereco e WHERE u.fk_endereco=e.endereco_id AND u.usuario_id=?";
    private final String BUSCAR_USUARIO_RG_MENOR18 = "SELECT * FROM usuario WHERE usuario_rg=? AND usuario_idade >= ? AND usuario_status=?";
    private final String BUSCAR_USUARIO = "SELECT * FROM usuario WHERE usuario_rg=?";
    private final String ATUALIZAR_SENHA_USUARIO = "UPDATE usuario SET usuario_senha=crypt(?, gen_salt('bf', 8)) WHERE usuario_id=?";
    private final String CRIAR_PRIMEIRO_ACESSO_DEPENDENTE = "UPDATE usuario SET usuario_senha=crypt(?, gen_salt('bf', 8)), usuario_email=?, usuario_cpf=?, usuario_responsavel=? WHERE usuario_id=? RETURNING usuario_id";
    private final String BUSCAR_USUARIO_ESPECIFICO = "SELECT * FROM usuario WHERE usuario_rg=? AND usuario_status=? AND usuario_idade >= ?";
    private final String BUSCAR_FK_ENDERECO = "SELECT fk_endereco FROM usuario WHERE usuario_id = ?";
    private final String BUSCAR_TODAS_FK_ENDERECO = "SELECT fk_endereco FROM usuario";
    private final String UPDATE_FK_ENDERECO = "UPDATE usuario SET fk_endereco=? WHERE usuario_id=?";
    private final String BUSCAR_USUARIO_RG = "SELECT * FROM usuario WHERE usuario_rg=? AND usuario_status=?";
    private final String BUSCAR_QUNT_DEPENDENTES_DO_USUARIO = "SELECT count(usuario_id) FROM usuario WHERE usuario_responsavel=?";
    private final String BUSCAR_TODOS_DEPENDENTES_DO_USUARIO = "SELECT usuario_id,usuario_nome,usuario_nascimento,usuario_rg FROM usuario WHERE usuario_responsavel=?";
    private final String BUSCAR_USUARIO_EMAIL = "SELECT usuario_id, usuario_nome, usuario_idade, usuario_email, fk_endereco FROM usuario WHERE usuario_status = ?";
    private final String ATUALIZAR_FISICO_USUARIO = "update usuario set usuario_altura=?, usuario_peso=? WHERE usuario_id=?";
    private final String ATUALIZAR_CONTATOS_USUARIO = "update usuario set usuario_email=?, usuario_telefone=?, usuario_celular=? WHERE usuario_id=?";
    private final String BUSCAR_USUARIOS_POR_CIDADE = "SELECT u.usuario_id,u.usuario_nome,u.usuario_nascimento,u.usuario_email,e.endereco_id,e.endereco_numero,e.endereco_cidade,e.endereco_cep,e.endereco_uf FROM usuario u, endereco e WHERE u.fk_endereco=e.endereco_id AND e.endereco_cidade=? AND u.usuario_status=?";
    
    private final String CONSULTAR_RG_EXISTENTE = "SELECT count(usuario_id) FROM usuario WHERE usuario_rg=?";
    private final String CONSULTAR_CPF_EXISTENTE = "SELECT count(usuario_id) FROM usuario WHERE usuario_cpf=?";
    private final String CONSULTAR_EMAIL_EXISTENTE = "SELECT count(usuario_id) FROM usuario WHERE usuario_email=?";
    
    private final String CADASTRO_ID_USUARIO_HAS_RESTRICAO = "INSERT INTO usuario_has_restricao(usuario_id, restricao_id)VALUES (?, ?)";
    private final String BUSCA_RESTRICOES = "	SELECT * FROM usuario_has_restricao WHERE usuario_id = ?";
    private final String BUSCA_RESTRICOES_ALL = "SELECT * FROM usuario_has_restricao ";        
    
    private final String CADASTRO_ID_USUARIO_HAS_RESPONSAVEL = "INSERT INTO usuario_responsavel(usuario_responsavel, usuario_dependente)VALUES (?, ?)";
    private final String BUSCA_USUARIO_HAS_RESPONSAVEL = "SELECT * FROM usuario_responsavel WHERE usuario_responsavel=?";

    /**
     * *******************************************************************************************************************************
     * @param usuario
     * @return 
     */
    public int cadastaNovoUsuario(Usuario usuario) throws SQLException {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRA_NOVO_USUARIO);
            pstmt.setString(1, usuario.getNome());
            pstmt.setInt(2, usuario.getIdade());
            pstmt.setString(3, usuario.getCpf());
            pstmt.setString(4, usuario.getRg());
            pstmt.setString(5, usuario.getSenha());
            pstmt.setLong(6, usuario.getTelefone());
            pstmt.setLong(7, usuario.getCelular());
            pstmt.setString(8, usuario.getTiposague());
            pstmt.setFloat(9, usuario.getPeso());
            pstmt.setFloat(10, usuario.getAltura());
            pstmt.setDate(11, new java.sql.Date(usuario.getNascimento().getTime()));
            pstmt.setInt(12, usuario.getResponsavel());
            pstmt.setBoolean(13, usuario.isStatus());
            pstmt.setString(14, usuario.getEmail());
            pstmt.setInt(15, usuario.getEndereco().getId_endereco());
            pstmt.setDate(16, new java.sql.Date(usuario.getDatacadastro().getTime()));
            rs = pstmt.executeQuery();

            if (rs.next()) {
                usuario.setUsuario_id(rs.getInt("usuario_id"));
            }
            
            return usuario.getUsuario_id();
            
        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public Usuario autenticaUsuario(Usuario usuario) {
        Usuario usuarioAutenticado = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsUsuario = null;
        
        usuarioAutenticado = new Usuario();
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(AUTENTICA_USUARIO);
            pstmt.setString(1, usuario.getRg());
            pstmt.setString(2, usuario.getSenha());
            pstmt.setBoolean(3, usuario.isStatus());
            pstmt.setInt(4, usuario.getResponsavel());
            rsUsuario = pstmt.executeQuery();
            
            if (rsUsuario.next()) {
                
                usuarioAutenticado.setUsuario_id(rsUsuario.getInt("usuario_id"));
                usuarioAutenticado.setNome(rsUsuario.getString("usuario_nome"));
                usuarioAutenticado.setSenha(rsUsuario.getString("usuario_senha"));
                usuarioAutenticado.setNascimento(rsUsuario.getDate("usuario_nascimento"));
                usuarioAutenticado.setIdade(rsUsuario.getInt("usuario_idade"));
            }
            
            return usuarioAutenticado;
            
        } catch (SQLException errosql) {
            System.out.println("Erro SQL(UsuarioDAO)" + errosql);
            throw new RuntimeException(errosql);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException errosql) {
                    System.out.println("Erro SQL(UsuarioDAO)" + errosql);
                    throw new RuntimeException(errosql);
                }
            }
        }
        
    }

    public ArrayList<Usuario> exibirUsuarios(Usuario usuarioModelo) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_USUARIO);
            pstmt.setString(1, usuarioModelo.getRg());
            ArrayList<Usuario> listaUsuario = new ArrayList<>();
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setUsuario_id(rs.getInt("usuario_id"));
                usuario.setNome(rs.getString("usuario_nome"));
                usuario.setIdade(rs.getInt("usuario_idade"));
                usuario.setCpf(rs.getString("usuario_cpf"));
                usuario.setRg(rs.getString("usuario_rg"));
                usuario.setTelefone(rs.getLong("usuario_telefone"));
                usuario.setCelular(rs.getLong("usuario_celular"));
                usuario.setTiposague(rs.getString("usuario_tiposangue"));
                usuario.setPeso(rs.getInt("usuario_peso"));
                usuario.setAltura(rs.getInt("usuario_altura"));
                usuario.setNascimento(rs.getDate("usuario_nascimento"));
                usuario.setStatus(rs.getBoolean("usuario_status"));
                usuario.setEmail(rs.getString("usuario_email"));
                usuario.setResponsavel(rs.getInt("usuario_responsavel"));
                listaUsuario.add(usuario);
            }
            return listaUsuario;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public int consultarQuntDependentesDoUsuario(Usuario usuarioModelo) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int quntDependentes = 0;

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_QUNT_DEPENDENTES_DO_USUARIO);
            pstmt.setInt(1, usuarioModelo.getUsuario_id());

            rs = pstmt.executeQuery();

            if (rs.next()) {

                quntDependentes = rs.getInt("count");

            }

            return quntDependentes;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public int consultarRgExistente(Usuario usuarioModelo) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int qntcadastros = 0;

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTAR_RG_EXISTENTE);
            pstmt.setString(1, usuarioModelo.getRg());
            
            rs = pstmt.executeQuery();

            if (rs.next()) {

                qntcadastros = rs.getInt("count");

            }

            return qntcadastros;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public int consultarCpfExistente(Usuario usuarioModelo) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int qntcadastros = 0;

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTAR_CPF_EXISTENTE);
            pstmt.setString(1, usuarioModelo.getCpf());
            
            rs = pstmt.executeQuery();

            if (rs.next()) {

                qntcadastros = rs.getInt("count");

            }

            return qntcadastros;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public int consultarEmailExistente(Usuario usuarioModelo) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int qntcadastros = 0;

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTAR_EMAIL_EXISTENTE);
            pstmt.setString(1, usuarioModelo.getEmail());
            
            rs = pstmt.executeQuery();

            if (rs.next()) {

                qntcadastros = rs.getInt("count");

            }

            return qntcadastros;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public boolean excluirUsuario(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean status = false;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(DELETA_USUARIO);
            pstmt.setBoolean(1, usuario.isStatus());
            pstmt.setInt(2, usuario.getUsuario_id());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                status = rs.getBoolean("usuario_status");
            }

            return status;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }     

    public void atualizar_usuario(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(UPDATE_USUARIO);
            pstmt.setString(1, usuario.getNome());
            pstmt.setInt(2, usuario.getIdade());
            pstmt.setString(3, usuario.getCpf());
            pstmt.setString(4, usuario.getRg());
            pstmt.setLong(5, usuario.getTelefone());
            pstmt.setLong(6, usuario.getCelular());
            pstmt.setString(7, usuario.getTiposague());
            pstmt.setFloat(8, usuario.getPeso());
            pstmt.setFloat(9, usuario.getAltura());
            pstmt.setDate(10, new java.sql.Date(usuario.getNascimento().getTime()));
            pstmt.setInt(11, usuario.getResponsavel());
            pstmt.setString(12, usuario.getEmail());
            pstmt.setInt(13, usuario.getEndereco().getId_endereco());
            pstmt.setInt(14, usuario.getUsuario_id());
            pstmt.execute();

        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public ArrayList<Usuario> emailUsuarios(ArrayList<Integer> listaid) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exite;

        ArrayList<Usuario> listaUsuario = new ArrayList<>();

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_USUARIO_EMAIL);
            pstmt.setBoolean(1, true);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                exite = false;
                for (int i = 0; listaid.size() > i; i++) {
                    if (i != rs.getInt("usuario_id")) {
                        exite = true;
                    }
                }
                if (exite == false) {
                    Endereco endereco = new Endereco();
                    Usuario usuario = new Usuario();
                    usuario.setEndereco(endereco);
                    usuario.setNome(rs.getString("usuario_nome"));
                    usuario.setIdade(rs.getInt("usuario_idade"));

                    usuario.setEmail(rs.getString("usuario_email"));
                    usuario.getEndereco().setId_endereco(rs.getInt("fk_endereco"));
                    listaUsuario.add(usuario);
                }
            }
            return listaUsuario;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public void atualizarSenhaUsuario(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ATUALIZAR_SENHA_USUARIO);
            pstmt.setString(1, usuario.getSenha());
            pstmt.setInt(2, usuario.getUsuario_id());
            pstmt.execute();

        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
     public int criarPrimeiroAcessoDependente(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int usuario_id = 0;
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CRIAR_PRIMEIRO_ACESSO_DEPENDENTE);
            pstmt.setString(1, usuario.getSenha());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getCpf());
            pstmt.setInt(4, 0);
            pstmt.setInt(5, usuario.getUsuario_id());
            
             rs = pstmt.executeQuery();
             
             if(rs.next()){
                 usuario_id = rs.getInt("usuario_id");
             }

             return usuario_id;
             
        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public Usuario buscaUsuarioUnico(Usuario usuarioModelo) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Endereco endereco = new Endereco();
        Usuario usuario = new Usuario();
        usuario.setEndereco(endereco);
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_USUARIO_UNICO);
            pstmt.setInt(1, usuarioModelo.getUsuario_id());
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                usuario.setUsuario_id(rs.getInt("usuario_id"));
                usuario.setNome(rs.getString("usuario_nome"));
                usuario.setIdade(rs.getInt("usuario_idade"));
                usuario.setCpf(rs.getString("usuario_cpf"));
                usuario.setRg(rs.getString("usuario_rg"));
                usuario.setTelefone(rs.getLong("usuario_telefone"));
                usuario.setCelular(rs.getLong("usuario_celular"));
                usuario.setTiposague(rs.getString("usuario_tiposangue"));
                usuario.setPeso(rs.getFloat("usuario_peso"));
                usuario.setAltura(rs.getFloat("usuario_altura"));
                usuario.setNascimento(rs.getDate("usuario_nascimento"));
                usuario.setDatacadastro(rs.getDate("usuario_datacadastro"));
                usuario.setStatus(rs.getBoolean("usuario_status"));
                usuario.setEmail(rs.getString("usuario_email"));
                usuario.setResponsavel(rs.getInt("usuario_responsavel"));
                usuario.getEndereco().setId_endereco(rs.getInt("endereco_id"));
                usuario.getEndereco().setLogradouro(rs.getString("endereco_logadouro"));
                usuario.getEndereco().setNumero(rs.getInt("endereco_numero"));
                usuario.getEndereco().setCidade(rs.getString("endereco_cidade"));
                usuario.getEndereco().setBairro(rs.getString("endereco_bairro"));
                usuario.getEndereco().setComplemento(rs.getString("endereco_complemento"));
                usuario.getEndereco().setCep(rs.getString("endereco_cep"));
                usuario.getEndereco().setUf(rs.getString("endereco_uf"));
            }
            
            return usuario;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public Usuario buscarResponsavelDoUsuarioPorId(Usuario usuarioModelo) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Endereco endereco = new Endereco();
        Usuario usuario = new Usuario();
        usuario.setEndereco(endereco);
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_USUARIO_UNICO);
            pstmt.setInt(1, usuarioModelo.getResponsavel());
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                usuario.setUsuario_id(rs.getInt("usuario_id"));
                usuario.setNome(rs.getString("usuario_nome"));
                usuario.setIdade(rs.getInt("usuario_idade"));
                usuario.setCpf(rs.getString("usuario_cpf"));
                usuario.setRg(rs.getString("usuario_rg"));
                usuario.setTelefone(rs.getLong("usuario_telefone"));
                usuario.setCelular(rs.getLong("usuario_celular"));
                usuario.setTiposague(rs.getString("usuario_tiposangue"));
                usuario.setPeso(rs.getFloat("usuario_peso"));
                usuario.setAltura(rs.getFloat("usuario_altura"));
                usuario.setNascimento(rs.getDate("usuario_nascimento"));
                usuario.setDatacadastro(rs.getDate("usuario_datacadastro"));
                usuario.setStatus(rs.getBoolean("usuario_status"));
                usuario.setEmail(rs.getString("usuario_email"));
                usuario.setResponsavel(rs.getInt("usuario_responsavel"));
                usuario.getEndereco().setId_endereco(rs.getInt("endereco_id"));
                usuario.getEndereco().setLogradouro(rs.getString("endereco_logadouro"));
                usuario.getEndereco().setNumero(rs.getInt("endereco_numero"));
                usuario.getEndereco().setCidade(rs.getString("endereco_cidade"));
                usuario.getEndereco().setBairro(rs.getString("endereco_bairro"));
                usuario.getEndereco().setComplemento(rs.getString("endereco_complemento"));
                usuario.getEndereco().setCep(rs.getString("endereco_cep"));
                usuario.getEndereco().setUf(rs.getString("endereco_uf"));
            }
            
            return usuario;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public Usuario buscarResponsavelDoUsuarioPorRg(Usuario usuarioMODELO) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Usuario usuarioResponsavel = new Usuario();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_USUARIO_RG_MENOR18);
            pstmt.setString(1, usuarioMODELO.getRg_responsavel());
            pstmt.setInt(2, 18);
            pstmt.setBoolean(3, true);
            rs = pstmt.executeQuery();
            if (rs.next()) {

                usuarioResponsavel.setUsuario_id(rs.getInt("usuario_id"));
                usuarioResponsavel.setEmail(rs.getString("usuario_email"));
                
            }
            return usuarioResponsavel;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public Usuario buscaUsuarioPorRg(Usuario usuarioM) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Usuario usuario = new Usuario();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_USUARIO_RG);
            pstmt.setString(1, usuarioM.getRg());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                usuario.setUsuario_id(rs.getInt("usuario_id"));
                usuario.setIdade(rs.getInt("usuario_idade"));
                usuario.setDatacadastro(rs.getDate("usuario_datacadastro"));
                usuario.setNascimento(rs.getDate("usuario_nascimento"));
                usuario.setRg(rs.getString("usuario_rg"));
            }
            
            return usuario;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public Usuario exibirUsuarioEspecifico(Usuario usuarioModelo) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Usuario usuario = new Usuario();

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_USUARIO_ESPECIFICO);

            pstmt.setString(1, usuarioModelo.getRg());
            pstmt.setBoolean(2, usuarioModelo.isStatus());
            pstmt.setInt(3, usuarioModelo.getIdade());
            rs = pstmt.executeQuery();

            if (rs.next()) {

                usuario.setUsuario_id(rs.getInt("usuario_id"));
                usuario.setNome(rs.getString("usuario_nome"));
                usuario.setCpf(rs.getString("usuario_cpf"));
                usuario.setRg(rs.getString("usuario_rg"));
                usuario.setIdade(rs.getInt("usuario_idade"));

            }

            return usuario;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }

    }

    public int enderecoUsuario(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int fk_endereco = 0;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_FK_ENDERECO);
            pstmt.setInt(1, usuario.getUsuario_id());

            rs = pstmt.executeQuery();
            if (rs.next()) {
                fk_endereco = rs.getInt("fk_endereco");

            }

            return fk_endereco;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public boolean enderecoPertenceaMaisdeUmUsuário(int fk_endereco) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean pertence = false;
        int cont = 0;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_TODAS_FK_ENDERECO);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (fk_endereco == rs.getInt("fk_endereco")) {
                    cont++;
                    if (cont > 1) {
                        pertence = true;
                        break;
                    }
                }

            }

            return pertence;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public void atualizarEndereco(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(UPDATE_FK_ENDERECO);
            pstmt.setInt(1, usuario.getEndereco().getId_endereco());
            pstmt.setInt(2, usuario.getUsuario_id());
            pstmt.execute();

        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public ArrayList<Usuario> buscarTodosDependentesDoUsuario(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<Usuario> listdependentes = new ArrayList<Usuario>();

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_TODOS_DEPENDENTES_DO_USUARIO);
            pstmt.setInt(1, usuario.getUsuario_id());
            rs = pstmt.executeQuery();

            while (rs.next()) {

                Usuario usuarioModelo = new Usuario();
                usuarioModelo.setUsuario_id(rs.getInt("usuario_id"));
                usuarioModelo.setNome(rs.getString("usuario_nome"));
                usuarioModelo.setNascimento(rs.getDate("usuario_nascimento"));
                usuarioModelo.setRg(rs.getString("usuario_rg"));

                listdependentes.add(usuarioModelo);
            }

            return listdependentes;

        } catch (SQLException errosql) {
            System.out.println("Erro SQL(UsuarioDAO)" + errosql);
            throw new RuntimeException(errosql);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException errosql) {
                    System.out.println("Erro SQL(UsuarioDAO)" + errosql);
                    throw new RuntimeException(errosql);
                }
            }
        }
    }
    
    public void atualizarContatosUsuario(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ATUALIZAR_CONTATOS_USUARIO);
            pstmt.setString(1, usuario.getEmail());
            pstmt.setLong(2, usuario.getTelefone());
            pstmt.setLong(3, usuario.getCelular());
            pstmt.setInt(4, usuario.getUsuario_id());
            pstmt.execute();

        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public void atualizarFisicoUsuario(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ATUALIZAR_FISICO_USUARIO);
            pstmt.setFloat(1, usuario.getAltura());
            pstmt.setFloat(2, usuario.getPeso());
            pstmt.setInt(3, usuario.getUsuario_id());
            pstmt.execute();

        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    /**
     * **********************************************Usuario has Restricoes***********************************************************
     */
    public void cadastroUsuario_has_restricaoDAO(int id_usuario, int id_restricao) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRO_ID_USUARIO_HAS_RESTRICAO);
            pstmt.setInt(1, id_usuario);
            pstmt.setInt(2, id_restricao);

            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(Usuario_has_restricaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(Usuario_has_restricaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public ArrayList<Integer> exibirUsuario_has_restricao(int id_usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_RESTRICOES);
            ArrayList<Integer> listaRestricao = new ArrayList<>();
            pstmt.setInt(1, id_usuario);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                listaRestricao.add(rs.getInt("restricao_id"));
            }
            return listaRestricao;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(Usuario_has_restricaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(Usuario_has_restricaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public ArrayList<Usuario> buscar_Usuario_Por_Cidade(Cidade cidade) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
            ArrayList<Usuario> listaUsuario = new ArrayList<>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_USUARIOS_POR_CIDADE);
            pstmt.setString(1, cidade.getNome_cidade());
            pstmt.setBoolean(2, true);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setUsuario_id(rs.getInt("usuario_id"));
                usuario.setNome(rs.getString("usuario_nome"));
                usuario.setNascimento(rs.getDate("usuario_nascimento"));
                usuario.setEmail(rs.getString("usuario_email"));
                usuario.getEndereco().setId_endereco(rs.getInt("endereco_id"));
                usuario.getEndereco().setNumero(rs.getInt("endereco_numero"));
                usuario.getEndereco().setCidade(rs.getString("endereco_cidade"));
                usuario.getEndereco().setCep(rs.getString("endereco_cep"));
                usuario.getEndereco().setUf(rs.getString("endereco_uf"));
                listaUsuario.add(usuario);
            }
            return listaUsuario;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    /**
     * ************************************************************************************************************************
     */

    /**
     * **********************************************Usuario has Resposnsavel*********************************************************
     */
    public void cadastroUsuario_has_responsavel(int id_responsavel, int id_dependente) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRO_ID_USUARIO_HAS_RESPONSAVEL);
            pstmt.setInt(1, id_responsavel);
            pstmt.setInt(2, id_dependente);

            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(Usuario_has_ResponsavelDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(Usuario_has_ResponsavelDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public ArrayList<Integer> exibirUsuario_has_responsavel(int id_responsavel) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_USUARIO_HAS_RESPONSAVEL);
            ArrayList<Integer> listaRestricao = new ArrayList<>();
            pstmt.setInt(1, id_responsavel);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                listaRestricao.add(rs.getInt("usuario_dependente"));

            }
            return listaRestricao;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(Usuario_has_ResponsavelDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(Usuario_has_ResponsavelDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public ArrayList<Integer> usuario_has_restricaoBuscaUsuarioComRestricao(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exite;
        
        ArrayList<Integer> listaUsuarios = new ArrayList<>();
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_RESTRICOES_ALL);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                exite = false;
                for (Restricao u : usuario.getRestricao().getListrestricao()) {
                    
                    if (u.getRestricao_id() == rs.getInt("fk_restricao")) {
                        exite = true;
                    }
                }
                
                if (exite == false) {
                    listaUsuarios.add(rs.getInt("fk_usuario"));
                }
                
            }
            return listaUsuarios;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(Usuario_has_restricaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(Usuario_has_restricaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    /**
     * *******************************************************************************************************************************
     */
}
