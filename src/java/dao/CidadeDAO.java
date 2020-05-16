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
import model.Cidade;
import model.Estado;
import util.ConectaBanco;

/**
 *
 * @author Victor_Aguiar
 */
public class CidadeDAO {
 
    final String BUSCAR_DADOS_ESTADOS_POR_ID = "SELECT c.cidade_id,c.cidade_nome,e.estado_nome FROM cidade c, estado e WHERE c.cidade_id=? AND c.cidade_estado=e.estado_id";
    
     public String consultarEnderecoExistente(Cidade cidade) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String cidade_nome = null;
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_DADOS_ESTADOS_POR_ID);
            pstmt.setLong(1, cidade.getId_cidade());
            rs = pstmt.executeQuery();

            if (rs.next()) {

                cidade_nome = rs.getString("cidade_nome");

            }
            return cidade_nome;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(EnderecoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(EnderecoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
     
      public Cidade consultarDadosCidade(Cidade cidadeModelo) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        Cidade cidade = new Cidade();
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_DADOS_ESTADOS_POR_ID);
            pstmt.setLong(1, cidadeModelo.getId_cidade());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                cidade.setNome_cidade(rs.getString("cidade_nome"));
                cidade.getEstado().setNome(rs.getString("estado_nome"));

            }
            
            return cidade;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(EnderecoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(EnderecoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
}

