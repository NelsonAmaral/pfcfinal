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
import model.Estado;
import util.ConectaBanco;

/**
 *
 * @author Victor_Aguiar
 */
public class EstadoDAO {
 
    final String BUSCAR_DADOS_ESTADOS_POR_ID = "SELECT cidade_nome FROM cidade WHERE cidade_id=?";
    
     public String consultarEnderecoExistente(Estado estado) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String endereco_nome = null;
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_DADOS_ESTADOS_POR_ID);
            pstmt.setLong(1, estado.getId());
            rs = pstmt.executeQuery();

            if (rs.next()) {

                endereco_nome = rs.getString("cidade_nome");

            }
            return endereco_nome;

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
