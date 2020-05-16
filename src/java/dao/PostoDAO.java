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
import model.Posto;
import model.Endereco;

import util.ConectaBanco;

/**
 *
 * @author nelson_amaral
 */
public class PostoDAO {

    private final String CADASTRAR_POSTO = "INSERT INTO posto(posto_nome, posto_telefone,posto_ativo ,fk_endereco)VALUES (?, ?, ?, ?);";
    private final String EXCLUI_POSTO = "UPDATE posto SET posto_ativo=? WHERE posto_id=?;";
    private final String BUSCA_POSTO = "SELECT * FROM posto order by posto_nome";
    private final String BUSCA_POSTO_ATIVO = "SELECT * FROM posto WHERE posto_ativo=?";
    private final String BUSCA_POSTO_UNICO = "SELECT * FROM posto WHERE posto_id = ?;";
    private final String ATUALIZA_POSTO = "UPDATE posto SET posto_nome=?, posto_telefone=? WHERE posto_id=?;";
    private final String BUSCAR_TODAS_FK_ENDERECO = "SELECT fk_endereco FROM usuario;";
    private final String UPDATE_FK_ENDERECO = "UPDATE posto SET fk_endereco=? WHERE posto_id=?";
   
    public void cadastraPosto(Posto posto) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRAR_POSTO);

            pstmt.setString(1, posto.getPosto_nome());
            pstmt.setLong(2, posto.getPosto_telefone());
            pstmt.setBoolean(3, true);
            pstmt.setInt(4, posto.getPosto_endereco().getId_endereco());

            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(PostoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(PostoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public void excluirPosto(Posto posto) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(EXCLUI_POSTO);
            pstmt.setBoolean(1, posto.isPosto_ativo());
            pstmt.setInt(2, posto.getPosto_id());
            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(PostoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(PostoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public ArrayList<Posto> buscaPostos() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVsCal = null;
        ArrayList<Posto> listaPosto = new ArrayList<>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_POSTO);
            rsVsCal = pstmt.executeQuery();
            while (rsVsCal.next()) {
                Posto posto = new Posto();
                Endereco endereco = new Endereco();
                posto.setPosto_endereco(endereco);
                posto.setPosto_id(rsVsCal.getInt("posto_id"));
                posto.setPosto_nome(rsVsCal.getString("posto_nome"));
                posto.setPosto_telefone(rsVsCal.getLong("posto_telefone"));
                posto.setPosto_ativo(rsVsCal.getBoolean("posto_ativo"));

                posto.getPosto_endereco().setId_endereco(rsVsCal.getInt("fk_endereco"));
                listaPosto.add(posto);
            }
            return listaPosto;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(PostoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(PostoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public Posto buscaPostosUnico(Posto p) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVsCal = null;
        Endereco endereco = new Endereco();
        Posto posto = new Posto();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_POSTO_UNICO);
            pstmt.setInt(1, p.getPosto_id());
            rsVsCal = pstmt.executeQuery();
            if (rsVsCal.next()) {
                posto.setPosto_endereco(endereco);
                posto.setPosto_id(rsVsCal.getInt("posto_id"));
                posto.setPosto_nome(rsVsCal.getString("posto_nome"));
                posto.setPosto_telefone(rsVsCal.getLong("posto_telefone"));
                posto.setPosto_ativo(rsVsCal.getBoolean("posto_ativo"));

                posto.getPosto_endereco().setId_endereco(rsVsCal.getInt("fk_endereco"));

            }
            return posto;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(PostoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(PostoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public void atualizaPosto(Posto posto) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ATUALIZA_POSTO);
            pstmt.setString(1, posto.getPosto_nome());
            pstmt.setLong(2, posto.getPosto_telefone());
            pstmt.setInt(3, posto.getPosto_id());

            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(PostoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(PostoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public boolean enderecoPertenceaMaisdeUmPosto (int fk_endereco){
            Connection conexao = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            boolean pertence = false;
            int cont = 0;
            try {
                conexao = ConectaBanco.getConexao();
                pstmt = conexao.prepareStatement(BUSCAR_TODAS_FK_ENDERECO);
                rs = pstmt.executeQuery();
                while(rs.next()){
                   if (fk_endereco == rs.getInt("fk_endereco")){
                       cont++;
                       if(cont > 1){
                           pertence = true;
                           break;
                       }
                   }
                   
                }
                
                return pertence;
            } catch (SQLException erro) {
                System.out.println("Erro SQL(PostoDAO)" + erro);
                throw new RuntimeException(erro);
            } finally {
                if (conexao != null) {
                    try {
                        conexao.close();
                    } catch (SQLException erroSQL) {
                        System.out.println("Erro SQL(PostoDAO)" + erroSQL);
                        throw new RuntimeException(erroSQL);
                    }
                }
            }
        }
     public void enderecoAtualiza (int fk_endereco, int id_posto){
            Connection conexao = null;
            PreparedStatement pstmt = null;
            try {
                conexao = ConectaBanco.getConexao();
                pstmt = conexao.prepareStatement(UPDATE_FK_ENDERECO);
                pstmt.setInt(1, fk_endereco);
                pstmt.setInt(2, id_posto);
                pstmt.execute();

            } catch (SQLException erro) {
                System.out.println("Erro SQL(PostoDAO)" + erro);
                throw new RuntimeException(erro);
            } finally {
                if (conexao != null) {
                    try {
                        conexao.close();
                    } catch (SQLException erroSQL) {
                        System.out.println("Erro SQL(PostoDAO)" + erroSQL);
                        throw new RuntimeException(erroSQL);
                    }
                }
            }
        }
     
     public ArrayList<Posto> buscaPostosAtivos() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVsCal = null;
        ArrayList<Posto> listaPosto = new ArrayList<>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_POSTO_ATIVO);
            pstmt.setBoolean(1, true);
            
            rsVsCal = pstmt.executeQuery();
            while (rsVsCal.next()) {
                Posto posto = new Posto();
                posto.setPosto_id(rsVsCal.getInt("posto_id"));
                posto.setPosto_nome(rsVsCal.getString("posto_nome"));
                posto.setPosto_telefone(rsVsCal.getLong("posto_telefone"));
                posto.setPosto_ativo(rsVsCal.getBoolean("posto_ativo"));
                posto.getPosto_endereco().setId_endereco(rsVsCal.getInt("fk_endereco"));
                
                listaPosto.add(posto);
            }
            return listaPosto;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(PostoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(PostoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

}
