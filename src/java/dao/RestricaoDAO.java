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
import model.Restricao;
import model.Usuario;
import util.ConectaBanco;

/**
 *
 * @author Victor Aguiar
 */
public class RestricaoDAO {
    
     private final String RESTRICAO_CADASTRAR = "INSERT INTO restricao (restricao_nome,restricao_tipo,restricao_status) values (?,?,?) RETURNING restricao_id";
     private final String CONSULTAR_RESTRICAO = "SELECT * FROM restricao WHERE restricao_status !=false order by restricao_nome";
     private final String CONSULTAR_RESTRICAO_UNICA = "SELECT * FROM restricao WHERE restricao_status=true AND restricao_id = ?";
     private final String DELETAR_RESTRICAO = "UPDATE restricao SET restricao_status = false WHERE restricao_id=? RETURNING restricao_status";
     private final String ATUALIZAR_RESTRICAO = "UPDATE restricao SET restricao_nome=?, restricao_tipo=? WHERE restricao_id=? ";
     
     private final String CONSULTAR_RESTRICAO_USUARIO = "SELECT restricao_id,restricao_nome, restricao_tipo FROM restricao r, usuario_has_restricao uhr WHERE r.restricao_id=uhr.fk_restricao AND fk_usuario=? AND uhr.status=?";
     private final String CADASTRAR_RESTRICAO_USUARIO = "INSERT INTO usuario_has_restricao values (?,?,?)";
     private final String DELETAR_RESTRICAO_USUARIO = "UPDATE usuario_has_restricao SET status=? WHERE fk_usuario=? AND fk_restricao=?";
     private final String VERIFICAR_RESTRICAO_USUARIO = "SELECT uhr.fk_restricao FROM usuario_has_restricao uhr WHERE uhr.fk_restricao=? AND uhr.fk_usuario=?";
     private final String ATIVAR_RESTRICAO_USUARIO = "UPDATE usuario_has_restricao SET status=? WHERE fk_usuario=? AND fk_restricao=?";
     
     public int cadastaNovoRestricao(Restricao restricao) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(RESTRICAO_CADASTRAR);
            pstmt.setString(1, restricao.getRestricao_nome());
            pstmt.setString   (2, restricao.getRestricao_tipo());
            pstmt.setBoolean(3, restricao.isRestricao_status());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                restricao.setRestricao_id(rs.getInt("restricao_id"));
            }
            return restricao.getRestricao_id();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(RestricaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(RestricaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
     
     public Restricao consultarUnicaRestricao(int id) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Restricao restricao = new Restricao();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTAR_RESTRICAO_UNICA);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs != null) {
                    restricao.setRestricao_id(rs.getInt("restricao_id"));
                    restricao.setRestricao_nome(rs.getString("restricao_nome"));
                    restricao.setRestricao_tipo(rs.getString("restricao_tipo"));
                    restricao.setRestricao_status(rs.getBoolean("restricao_status"));
                }
            }
            return restricao;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(RestricaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(RestricaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }

    }
     
     
     public ArrayList<Restricao> consultarRestricao() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTAR_RESTRICAO);
           
            ArrayList<Restricao> listarestricao = new ArrayList<>();
            rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs != null) {
                    Restricao restricao = new Restricao();
                    restricao.setRestricao_id(rs.getInt("restricao_id"));
                    restricao.setRestricao_nome(rs.getString("restricao_nome"));
                    restricao.setRestricao_tipo(rs.getString("restricao_tipo"));
                    restricao.setRestricao_status(rs.getBoolean("restricao_status"));

                    listarestricao.add(restricao);
                }
            }
            return listarestricao;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(RestricaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(RestricaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
     
     public ArrayList<Restricao> consultarRestricoesUsuario(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTAR_RESTRICAO_USUARIO);
            pstmt.setInt(1, usuario.getUsuario_id());
            pstmt.setBoolean(2, true);
            
            ArrayList<Restricao> listarestricao = new ArrayList<>();
            rs = pstmt.executeQuery();

            while (rs.next()) {

                    Restricao restricao = new Restricao();
                    restricao.setRestricao_id(rs.getInt("restricao_id"));
                    restricao.setRestricao_nome(rs.getString("restricao_nome"));
                    restricao.setRestricao_tipo(rs.getString("restricao_tipo"));

                    listarestricao.add(restricao);
            }
            return listarestricao;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(RestricaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(RestricaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
     
     public boolean deletarRestricao(Restricao restricao) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean status = false;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(DELETAR_RESTRICAO);
            
            pstmt.setInt(1, restricao.getRestricao_id());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                status = rs.getBoolean("restricao_status");
            }

            return status;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(RestricaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(RestricaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
     
     public void atualizaRestricao(Restricao restricao) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ATUALIZAR_RESTRICAO);
            
            pstmt.setString(1, restricao.getRestricao_nome());
            pstmt.setString(2, restricao.getRestricao_tipo());
            pstmt.setInt(3, restricao.getRestricao_id());
            pstmt.execute();

        } catch (SQLException erro) {
            System.out.println("Erro SQL(RestricaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(RestricaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
     
     public void cadastrarNovaRestricaoUsuario(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRAR_RESTRICAO_USUARIO);
            pstmt.setInt(1, usuario.getUsuario_id());
            pstmt.setInt(2, usuario.getRestricao().getRestricao_id());
            pstmt.setBoolean(3, true);
            pstmt.execute();
  
        } catch (SQLException erro) {
            System.out.println("Erro SQL(RestricaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(RestricaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
     
     public void atualizarRestricaoUsuario(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(DELETAR_RESTRICAO_USUARIO);           
            pstmt.setBoolean(1, false);
            pstmt.setInt(2, usuario.getUsuario_id());
            pstmt.setInt(3, usuario.getRestricao().getRestricao_id());
            pstmt.execute();

        } catch (SQLException erro) {
            System.out.println("Erro SQL(RestricaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(RestricaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
     
     public int verificarRestricaoUsuario(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Integer resultado = 0;
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(VERIFICAR_RESTRICAO_USUARIO);
            pstmt.setInt(1, usuario.getRestricao().getRestricao_id());
            pstmt.setInt(2, usuario.getUsuario_id());

            rs = pstmt.executeQuery();

            if (rs.next()) {

                    resultado = rs.getInt("fk_restricao");

            }
            
            return resultado;
            
        } catch (SQLException erro) {
            System.out.println("Erro SQL(RestricaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(RestricaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
     
     public void ativarRestricaoUsuario(Usuario usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ATIVAR_RESTRICAO_USUARIO);
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, usuario.getUsuario_id());
            pstmt.setInt(3, usuario.getRestricao().getRestricao_id());
            pstmt.execute();

        } catch (SQLException erro) {
            System.out.println("Erro SQL(RestricaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(RestricaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
}
