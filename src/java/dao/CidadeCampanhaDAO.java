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
import model.Campanha;
import util.ConectaBanco;

/**
 *
 * @author Victor_Aguiar
 */
public class CidadeCampanhaDAO {
    
        private final String CADASTRO_CIDADES_CAMPANHA = "INSERT INTO estado_campanha(campanha_id, cidade_id)VALUES (?, ?)";
        private final String BUSCAR_CIDADES_CAMPANHA = "SELECT * FROM estado_campanha WHERE campanha_id=?";
     
        public void cadastraCidadeCampanha(Campanha campanha) {
        Connection conexao = null;
        PreparedStatement pstmt;

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRO_CIDADES_CAMPANHA);
            pstmt.setInt(1, campanha.getCampanha_id());
            pstmt.setInt(2, campanha.getCidade().getId_cidade());
            pstmt.execute();

        } catch (SQLException erro) {
            System.out.println("Erro SQL(CampanhaDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(CampanhaDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
        
        public ArrayList<Integer> consultarCidadesdaCampanha(Campanha campanha) {
        Connection conexao = null;
        PreparedStatement pstmt;
        ResultSet rs;
        
        ArrayList<Integer> listIdCidades = new ArrayList<>();
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_CIDADES_CAMPANHA);
            pstmt.setInt(1, campanha.getCampanha_id());
             rs = pstmt.executeQuery();

             while (rs.next()){
                 listIdCidades.add(rs.getInt("cidade_id"));
             }
             
             return listIdCidades;
             
        } catch (SQLException erro) {
            System.out.println("Erro SQL(CampanhaDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(CampanhaDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
}
