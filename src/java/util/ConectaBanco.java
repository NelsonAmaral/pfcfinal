/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author nelson_amaral
 */
public class ConectaBanco {
    public static Connection getConexao(){
        Connection conexao = null;
        try{
            //driver que será utilizado
            Class.forName("org.postgresql.Driver");
            //cria um objeto de conexao com um banco especificado no caminho..
            conexao = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/tccapresentacao","postgres","123456");
            
        }catch(ClassNotFoundException erro){
            System.out.println("Erro(ConectaBanco): "+erro);
            throw new RuntimeException(erro);
            
        }catch(SQLException errosq){
            System.out.println("Erro sql(ConectaBanco)"+errosq);
            throw new RuntimeException(errosq);
        }
        return conexao;
    }
}
