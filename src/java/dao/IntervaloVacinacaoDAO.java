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
import java.sql.Time;
import java.util.ArrayList;
import model.CalendarioObrigatorio;

import model.IntervaloVacinacao;
import util.ConectaBanco;

/**
 *
 * @author Victor_Aguiar
 */
public class IntervaloVacinacaoDAO {
    private final String CADASTRAR_INTERVALO_VACINA = "INSERT INTO intervalo_vacinacao(fk_calendarioob, intervalov_dose, intervalov_dias, intervalov_status) VALUES ( (SELECT CALENDARIOOB_ID FROM CALENDARIO_OBRIGATORIO WHERE CALENDARIOOB_ID=?), ?, ?, ?)";
    private final String EXCLUI_INTERVALO_VACINA_POR_DOSE = "UPDATE intervalo_vacinacao SET intervalov_status=? WHERE intervalov_dose=? AND fk_calendarioob=?";
    private final String BUSCA_INTERVALO_VACINA = "SELECT * FROM intervalo_vacinacao WHERE intervalov_status=?";
    private final String BUSCA_INTERVALO_VACINA_UNICA = "SELECT fk_calendarioob,intervalov_dose,intervalov_dias FROM intervalo_vacinacao WHERE fk_calendarioob=? AND intervalov_status=?";
    private final String ATUALIZA_INTERVALO_VACINA = "UPDATE intervalo_vacinacao SET  intervalov_dias=?, WHERE intervalov_dose=? AND fk_calendarioob=?";
    private final String EXCLUIR_INTERVALO_VACINA = "UPDATE intervalo_vacinacao SET intervalov_status=? WHERE fk_calendarioob=?";
    
    public void cadastraIntervaloVacinacao(IntervaloVacinacao intervaloVacinacao) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRAR_INTERVALO_VACINA);
            
            pstmt.setInt(1, intervaloVacinacao.getCalendarioObr().getId_calendarioObr());
            pstmt.setInt(2, intervaloVacinacao.getDose() );
            pstmt.setInt(3, intervaloVacinacao.getDias());
            pstmt.setBoolean(4, true);
            
            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(IntervaloVacinacaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(IntervaloVacinacaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    public void excluirIntervaloVacinacaoPorDose(IntervaloVacinacao intervaloVacinacao) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(EXCLUI_INTERVALO_VACINA_POR_DOSE);
            pstmt.setBoolean(1, intervaloVacinacao.isAtivoOuNao());
            pstmt.setInt(2, intervaloVacinacao.getDose());
            pstmt.setInt(3, intervaloVacinacao.getCalendarioObr().getId_calendarioObr());
            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(IntervaloVacinacaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(IntervaloVacinacaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public void excluirIntervaloVacinacao(IntervaloVacinacao intervaloVacinacao) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(EXCLUIR_INTERVALO_VACINA);
            pstmt.setBoolean(1, false);
            pstmt.setInt(2, intervaloVacinacao.getCalendarioObr().getId_calendarioObr());
            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(IntervaloVacinacaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(IntervaloVacinacaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public ArrayList<IntervaloVacinacao> buscaIntervaloVacinacao() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVsCal = null;
        ArrayList<IntervaloVacinacao> listaIntervaloVacinacao = new ArrayList<>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_INTERVALO_VACINA);
            rsVsCal = pstmt.executeQuery();
            while (rsVsCal.next()) {
                IntervaloVacinacao intervaloVacinacao = new IntervaloVacinacao();

                intervaloVacinacao.getCalendarioObr().setId_calendarioObr(rsVsCal.getInt("fk_calendarioobrigatorio"));
                intervaloVacinacao.setDose(rsVsCal.getInt("intervalov_dose"));
                intervaloVacinacao.setDias(rsVsCal.getInt("intervalov_tempo"));
                intervaloVacinacao.setAtivoOuNao(rsVsCal.getBoolean("intervalov_status"));

                listaIntervaloVacinacao.add(intervaloVacinacao);
            }
            return listaIntervaloVacinacao;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(IntervaloVacinacaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(IntervaloVacinacaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public ArrayList<IntervaloVacinacao> buscaUnicaIntervaloVacinacao(IntervaloVacinacao intervalovacinacao) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<IntervaloVacinacao> listaIntervaloVacinacao = new ArrayList<>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_INTERVALO_VACINA_UNICA);
            pstmt.setInt(1, intervalovacinacao.getCalendarioObr().getId_calendarioObr());
            pstmt.setBoolean(2, intervalovacinacao.isAtivoOuNao());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                IntervaloVacinacao intervaloVacinacao = new IntervaloVacinacao();

                //intervaloVacinacao.getCalendarioObr().setId_calendarioObr(rs.getInt("fk_calendarioob"));
                intervaloVacinacao.setDose(rs.getInt("intervalov_dose"));
                intervaloVacinacao.setDias(rs.getInt("intervalov_dias"));

                listaIntervaloVacinacao.add(intervaloVacinacao);
            }
            return listaIntervaloVacinacao;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(IntervaloVacinacaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(IntervaloVacinacaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public void atualizaIntervaloVacinacao(IntervaloVacinacao intervaloVacinacao) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ATUALIZA_INTERVALO_VACINA);
            pstmt.setInt(1, intervaloVacinacao.getDias());
            pstmt.setInt(2, intervaloVacinacao.getDose());
            pstmt.setInt(3, intervaloVacinacao.getCalendarioObr().getId_calendarioObr());
            

            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(CalendarioObrigatorioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(CalendarioObrigatorioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

}
