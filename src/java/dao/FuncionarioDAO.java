/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Funcionario;
import model.PerfilDeAcesso;
import model.Usuario;
import util.ConectaBanco;

/**
 *
 * @author nelson_amaral
 */
public class FuncionarioDAO {
    private final String CADASTRO_FUNCIONARIO = "INSERT INTO funcionario(funcionario_confen, funcionario_senha, funcionario_acesso, funcionario_status, fk_usuario, fk_posto) VALUES (?, crypt(?, gen_salt('bf', 8)), ?, ?, (SELECT usuario_id FROM usuario WHERE usuario_id=?), (SELECT posto_id FROM posto WHERE posto_id=?));";
    private final String BLOQUEAR_OU_DESBLOQUEAR_FUNCIONARIO = "UPDATE funcionario SET funcionario_status=? WHERE funcionario_id=?";
    private final String BUSCA_FUNCIONARIO = "SELECT funcionario_id, funcionario_confen, funcionario_senha, funcionario_acesso, funcionario_status, fk_usuario, p.posto_nome FROM funcionario,  posto p WHERE fk_posto=p.posto_id AND funcionario_status=?";
    private final String BUSCA_FUNCIONARIOS_DESATIVADOS = "SELECT u.usuario_nome,u.usuario_rg,f.funcionario_id,f.funcionario_confen FROM funcionario f, usuario u WHERE f.fk_usuario=u.usuario_id AND f.funcionario_status=?";
    private final String BUSCA_FUNCIONARIO_UNICO = "SELECT funcionario_id,funcionario_confen,funcionario_senha,funcionario_acesso, funcionario_status, fk_usuario, posto_nome,posto_id FROM funcionario, posto WHERE funcionario_status=true AND funcionario_id=? AND fk_posto=posto_id";
    private final String BUSCA_FUNCIONARIO_POR_USUARIO = "SELECT * FROM funcionario WHERE fk_usuario=?";
    private final String BUSCA_FKS_USUARIOS = "SELECT * FROM funcionario WHERE funcionario_status=true";
    private final String ATUALIZA_FUNCIONARIO = "UPDATE funcionario SET funcionario_confen=?,funcionario_acesso=?,funcionario_status=?, fk_posto=?, fk_usuario=? WHERE funcionario_id=? ;";
    private final String AUTENTICA_FUNCIONARIO = "SELECT * FROM funcionario WHERE funcionario_confen=? AND funcionario_senha=crypt(?, funcionario_senha)";
    private final String ATUALIZA_SENHA_FUNCIONARIO = "UPDATE funcionario SET funcionario_senha=crypt(?, gen_salt('bf', 8)) WHERE funcionario_id=?";
    private final String CONSULTA_QNT_LOGINS_ATIVOS = "SELECT count(funcionario_id) FROM funcionario WHERE funcionario_status=?";
    
    public void cadastrarFuncionario(Funcionario funcionario){
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRO_FUNCIONARIO);
            pstmt.setString(1, funcionario.getCofen());
            pstmt.setString(2, funcionario.getSenha());
            pstmt.setString(3, funcionario.getPerfil().toString());
            pstmt.setBoolean(4,funcionario.isStatus());            
            pstmt.setInt(5, funcionario.getUsuario_id());
            pstmt.setInt(6,funcionario.getPosto().getPosto_id());
            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(FuncionarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(FuncionarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public void alterarStatusLoginFuncionario (Funcionario funcionario){
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BLOQUEAR_OU_DESBLOQUEAR_FUNCIONARIO);
            pstmt.setBoolean(1, funcionario.isStatus());
            pstmt.setInt(2, funcionario.getId_funcionario());
            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(FuncionarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(FuncionarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public ArrayList<Funcionario> exibeFuncionarios (){
        Connection conexao = null;
        PreparedStatement pstmt = null;
         ResultSet rsFunc = null;
         ArrayList<Funcionario> listaFuncionario = new ArrayList<>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_FUNCIONARIO);
            pstmt.setBoolean(1, true);
            
            rsFunc = pstmt.executeQuery();
            while(rsFunc.next()){
                Funcionario funcionario = new Funcionario();
                funcionario.setId_funcionario(rsFunc.getInt("funcionario_id"));
                funcionario.setCofen(rsFunc.getString("funcionario_confen"));
                funcionario.setPerfil(PerfilDeAcesso.valueOf(rsFunc.getString("funcionario_acesso")));
                funcionario.getPosto().setPosto_nome(rsFunc.getString("posto_nome"));
                funcionario.setStatus(rsFunc.getBoolean("funcionario_status"));
                funcionario.setUsuario_id(rsFunc.getInt("fk_usuario"));
                
                listaFuncionario.add(funcionario);
            }
            return listaFuncionario;
            
        } catch (SQLException erro) {
            System.out.println("Erro SQL(FuncionarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(FuncionarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public ArrayList<Funcionario> consultaFuncionariosDesativados (){
        Connection conexao = null;
        PreparedStatement pstmt = null;
         ResultSet rs = null;
         ArrayList<Funcionario> listaFuncionarioDesativados = new ArrayList<>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_FUNCIONARIOS_DESATIVADOS);
            pstmt.setBoolean(1, false);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setNome(rs.getString("usuario_nome"));
                funcionario.setRg(rs.getString("usuario_rg"));
                funcionario.setId_funcionario(rs.getInt("funcionario_id"));
                funcionario.setCofen(rs.getString("funcionario_confen"));

                listaFuncionarioDesativados.add(funcionario);
            }
            return listaFuncionarioDesativados;
            
        } catch (SQLException erro) {
            System.out.println("Erro SQL(FuncionarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(FuncionarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public int consultaQntDeLoginsFuncionariosAtivosNoSistema (){
        Connection conexao = null;
        PreparedStatement pstmt = null;
         ResultSet rsFunc = null;
         int QuantidadeAtivos = 0;
         
         ArrayList<Funcionario> listaFuncionario = new ArrayList<>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTA_QNT_LOGINS_ATIVOS);
            pstmt.setBoolean(1, true);
            
            rsFunc = pstmt.executeQuery();
            if(rsFunc.next()){
                
                QuantidadeAtivos = rsFunc.getInt("count");
                
            }
            return QuantidadeAtivos;
            
        } catch (SQLException erro) {
            System.out.println("Erro SQL(FuncionarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(FuncionarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    } 
    
            
      public int consultaFuncionarioPorUsuarioUnico (Funcionario funcionarioMODELO){
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsFunc = null;
        int id_funcionario = 0; 
         
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_FUNCIONARIO_POR_USUARIO);
            pstmt.setInt(1, funcionarioMODELO.getUsuario_id());
            rsFunc = pstmt.executeQuery();
            if(rsFunc.next()){
                
                id_funcionario = rsFunc.getInt("funcionario_id");
                
            }
            return id_funcionario;
            
        } catch (SQLException erro) {
            System.out.println("Erro SQL(FuncionarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(FuncionarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public Funcionario buscaFuncionario (int id){
        Connection conexao = null;
        PreparedStatement pstmt = null;
         ResultSet rsFunc = null;
         Funcionario funcionario = new Funcionario();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_FUNCIONARIO_UNICO);
            pstmt.setInt(1, id);
            rsFunc = pstmt.executeQuery();
            if(rsFunc.next()){
                funcionario.setId_funcionario(rsFunc.getInt("funcionario_id"));
                funcionario.setCofen(rsFunc.getString("funcionario_confen"));
                funcionario.setPerfil(PerfilDeAcesso.valueOf(rsFunc.getString("funcionario_acesso")));
                funcionario.setStatus(rsFunc.getBoolean("funcionario_status"));
                funcionario.setSenha(rsFunc.getString("funcionario_senha"));
                funcionario.setUsuario_id(rsFunc.getInt("fk_usuario"));
                funcionario.getPosto().setPosto_nome(rsFunc.getString("posto_nome"));
                funcionario.getPosto().setPosto_id(rsFunc.getInt("posto_id"));
            }
            return funcionario;
            
        } catch (SQLException erro) {
            System.out.println("Erro SQL(FuncionarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(FuncionarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    
    public void atualizaFuncionario (Funcionario funcionario){
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ATUALIZA_FUNCIONARIO);
            pstmt.setString(1, funcionario.getCofen());
            pstmt.setString(2, funcionario.getPerfil().toString());
            pstmt.setBoolean(3, funcionario.isStatus());
            pstmt.setInt(4, funcionario.getPosto().getPosto_id());
            pstmt.setInt(5, funcionario.getUsuario_id());    
            pstmt.setInt(6, funcionario.getId_funcionario());
            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(FuncionarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(FuncionarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public void atualizaSenhaFuncionario (Funcionario funcionario){
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ATUALIZA_SENHA_FUNCIONARIO);
            pstmt.setString(1, funcionario.getSenha());
            pstmt.setInt(2, funcionario.getId_funcionario());
            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(FuncionarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(FuncionarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public Funcionario autenticaFuncionario(Funcionario funcionario) {
        Funcionario funcionarioAutenticado = null;
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsfuncionario = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(AUTENTICA_FUNCIONARIO);
            pstmt.setString(1, funcionario.getCofen());
            pstmt.setString(2, funcionario.getSenha());
            rsfuncionario = pstmt.executeQuery();
           while (rsfuncionario.next()) {
                funcionarioAutenticado = new Funcionario();
                funcionarioAutenticado.setId_funcionario(rsfuncionario.getInt("funcionario_id"));
                funcionarioAutenticado.setSenha(rsfuncionario.getString("funcionario_senha"));
                funcionarioAutenticado.setPerfil(PerfilDeAcesso.valueOf(rsfuncionario.getString("funcionario_acesso")));
                funcionarioAutenticado.setUsuario_id(rsfuncionario.getInt("fk_usuario"));
                funcionarioAutenticado.getPosto().setPosto_id(rsfuncionario.getInt("fk_posto"));
               }
        } catch (SQLException errosql) {
            System.out.println("Erro SQL(FuncionarioDAO)" + errosql);
            throw new RuntimeException(errosql);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException errosql) {
                    System.out.println("Erro SQL(FuncionarioDAO)" + errosql);
                    throw new RuntimeException(errosql);
                }
            }
        }
        return funcionarioAutenticado;
    }
    
    public int buscaUsuarioIDFuncionario (int id){
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsFunc = null;
        int fk_usuario = 0; 
        Funcionario funcionario = new Funcionario();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_FUNCIONARIO_UNICO);
            pstmt.setInt(1, id);
            rsFunc = pstmt.executeQuery();
            if(rsFunc.next()){
                
               fk_usuario =  rsFunc.getInt("fk_usuario");
                
            }
            return fk_usuario;
            
        } catch (SQLException erro) {
            System.out.println("Erro SQL(FuncionarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(FuncionarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
        public ArrayList<Integer> buscaTodosFuncionariosAtivos (){
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsFunc = null;
        
        ArrayList<Integer> listafkusuario = new  ArrayList<Integer>() ;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_FKS_USUARIOS);
            rsFunc = pstmt.executeQuery();
            while(rsFunc.next()){
                
               listafkusuario.add(rsFunc.getInt("fk_usuario"));
                
               
            }
            return listafkusuario;
            
        } catch (SQLException erro) {
            System.out.println("Erro SQL(FuncionarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(FuncionarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
        }
}
