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
import model.IntervaloDoseVacinacao;
import model.TipoVacina;
import model.Vacina;
import util.ConectaBanco;

/**
 *
 * @author nelson_amaral
 */
public class IntervaloDoseVacinacaoDAO {

    private final String CADASTRO_INTERVALO_DOSE = "INSERT INTO intervalo_vacinacao(fk_calendarioobrigatorio, intervalov_dose, intervalov_tempo, intervalov_ciclo, intervalov_status)VALUES (?, ?, ?, ?, ?);";
    private final String BUSCA_INTERVALO_DOSE = "SELECT * FROM intervalo_vacinacao;";
    private final String BUSCA_INTERVALO_DOSE_UNICA = "  SELECT * FROM intervalo_vacinacao WHERE fk_calendarioobrigatorio=?;";

    public void cadastrarIntervaloDose(IntervaloDoseVacinacao intervaloDoseVacinacao) {
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRO_INTERVALO_DOSE);
            pstmt.setInt(1, intervaloDoseVacinacao.getCaledarioObr().getId_calendarioObr());
            pstmt.setInt(2, intervaloDoseVacinacao.getDose());
            pstmt.setInt(3, intervaloDoseVacinacao.getTempo());
            pstmt.setString(4, intervaloDoseVacinacao.getCiclo());
            pstmt.setBoolean(5, true);

            pstmt.execute();

        } catch (SQLException erro) {
            System.out.println("Erro SQL(IntervaloDoseVacinacaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(IntervaloDoseVacinacaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public ArrayList<IntervaloDoseVacinacao> consultarIntervaloDose() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_INTERVALO_DOSE);
            ArrayList<IntervaloDoseVacinacao> listaIntervaloDoseVacinacao = new ArrayList<>();
            rs = pstmt.executeQuery();

            while (rs.next()) {
                IntervaloDoseVacinacao intervaloDoseVacinacao = new IntervaloDoseVacinacao();
                intervaloDoseVacinacao.getCaledarioObr().setId_calendarioObr(rs.getInt("fk_calendarioobrigatorio"));
                intervaloDoseVacinacao.setDose(rs.getInt("intervalov_dose"));

                intervaloDoseVacinacao.setTempo(rs.getInt("intervalov_tempo"));
                intervaloDoseVacinacao.setCiclo(rs.getString("intervalov_ciclo"));
                intervaloDoseVacinacao.setStatus(rs.getBoolean("intervalov_status"));

                listaIntervaloDoseVacinacao.add(intervaloDoseVacinacao);
            }
            return listaIntervaloDoseVacinacao;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(IntervaloDoseVacinacaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(IntervaloDoseVacinacaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }

    }
    
    public IntervaloDoseVacinacao buscaIntervaloDose(int id) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
                        IntervaloDoseVacinacao intervaloDoseVacinacao = new IntervaloDoseVacinacao();

        ResultSet rs = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_INTERVALO_DOSE_UNICA);
            pstmt.setInt(1, id);
             rs = pstmt.executeQuery();

            while (rs.next()) {
                intervaloDoseVacinacao.getCaledarioObr().setId_calendarioObr(rs.getInt("fk_calendarioobrigatorio"));
                intervaloDoseVacinacao.setDose(rs.getInt("intervalov_dose"));

                intervaloDoseVacinacao.setTempo(rs.getInt("intervalov_tempo"));
                intervaloDoseVacinacao.setCiclo(rs.getString("intervalov_ciclo"));
                intervaloDoseVacinacao.setStatus(rs.getBoolean("intervalov_status"));

                }
            return intervaloDoseVacinacao;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(IntervaloDoseVacinacaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(IntervaloDoseVacinacaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }

    }
}
