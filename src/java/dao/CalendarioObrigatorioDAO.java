/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import model.CalendarioObrigatorio;
import model.Funcionario;
import model.IntervaloVacinacao;
import model.TipoVacina;
import model.Vacina;
import util.ConectaBanco;

/**
 *
 * @author Nelson.Amaral
 */
public class CalendarioObrigatorioDAO {

    private final String CADASTRA_VACINA_CALENDARIO = "INSERT INTO calendario_obrigatorio(calendarioob_comentario, calendarioob_datacadastro, calendarioob_horacadastro,calendarioob_status, fk_funcionario, fk_vacina,calendarioob_totaldoses) VALUES (?, ?, ?, ?, (SELECT FUNCIONARIO_ID FROM FUNCIONARIO WHERE FUNCIONARIO_ID=?), (SELECT VACINA_ID FROM VACINA WHERE VACINA_ID=?), ?) RETURNING calendarioob_id";
    private final String EXCLUI_VACINA_CALENDARIO = "UPDATE calendario_obrigatorio SET calendarioob_status=? WHERE calendarioob_id=?";
    private final String BUSCA_VACINA_CALENDARIO = "SELECT calendarioob.calendarioob_id,calendarioob.calendarioob_totaldoses FROM calendario_obrigatorio calendarioob WHERE calendarioob.fk_vacina=?";
    private final String BUSCA_ID_VACINA_CALENDARIOOBR = "SELECT fk_vacina FROM calendario_obrigatorio WHERE calendarioob_id=?";
    private final String ATUALIZA_VACINA_CALENDARIO = "UPDATE calendario_obrigatorio SET calendarioob_comentario=?  WHERE calendarioob_id=?;";
    private final String BUSCAR_VACINAS_POR_FAIXA_DE_INTERLAVO = "SELECT calendario.calendarioob_id,fk_vacina from calendario_obrigatorio calendario, intervalo_vacinacao intervalov WHERE intervalov.intervalov_dias>=? AND intervalov.intervalov_dias<=? AND intervalov.intervalov_status=? AND intervalov.fk_calendarioob=calendario.calendarioob_id AND calendario.calendarioob_status=? group by calendario.calendarioob_id";
    private final String BUSCAR_VACINACAO_PARA_EXIBIR_CARDENETA = "SELECT calendario.calendarioob_id,vacina.vacina_nome,vacina.vacina_tipo, vacina.vacina_descricao, intervalov.intervalov_dose,intervalov.intervalov_dias from calendario_obrigatorio calendario, intervalo_vacinacao intervalov,vacina vacina WHERE intervalov.intervalov_dias>=? AND intervalov.intervalov_dias<=? AND calendario.calendarioob_status=? AND intervalov.intervalov_status=? AND calendario.fk_vacina=? AND calendario.fk_vacina=vacina.vacina_id AND intervalov.fk_calendarioob=calendario.calendarioob_id";
    private final String EXCLUIRLOG_CALENDARIOOB_VACINA = "UPDATE calendario_obrigatorio SET calendarioob_status=? WHERE fk_vacina=? RETURNING calendarioob_id";
    private final String BUSCAR_VACINAS_DESDE_O_CADASTRO = "SELECT calendario.calendarioob_id,fk_vacina from calendario_obrigatorio calendario, intervalo_vacinacao intervalov WHERE intervalov.intervalov_dias>=? AND intervalov.intervalov_status=? AND intervalov.fk_calendarioob=calendario.calendarioob_id AND calendario.calendarioob_status=? group by calendario.calendarioob_id";
    
    public int cadastraVacinaCalendarioObr(CalendarioObrigatorio calendarioObr) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs =null;
        int id = 0;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRA_VACINA_CALENDARIO);
            
            pstmt.setString(1, calendarioObr.getComentario());
            pstmt.setDate(2, new java.sql.Date(calendarioObr.getDateCadastro().getTime()) );
            pstmt.setTime(3, new Time(calendarioObr.getHoraCadastro().getTime()));
            pstmt.setBoolean(4, true);           
            pstmt.setInt(5, calendarioObr.getFuncionario().getId_funcionario());
            pstmt.setInt(6, calendarioObr.getVacina().getId_vacina());
            pstmt.setInt(7, calendarioObr.getDose());
          
            rs = pstmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("calendarioob_id");
            }
            return id;
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

    public void excluirVacinaCalendarioObrPorId(CalendarioObrigatorio calendarioObr) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(EXCLUI_VACINA_CALENDARIO);
            pstmt.setBoolean(1, calendarioObr.isStatus());
            pstmt.setInt(2, calendarioObr.getId_calendarioObr());
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
    
     public CalendarioObrigatorio excluirVacinaCalendarioObrPorIdVacina(CalendarioObrigatorio calendarioObr) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int calendario_id = 0;
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(EXCLUIRLOG_CALENDARIOOB_VACINA);
            pstmt.setBoolean(1, calendarioObr.isStatus());
            pstmt.setInt(2, calendarioObr.getVacina().getId_vacina());
            rs = pstmt.executeQuery();
            
            CalendarioObrigatorio calendarioobrigatorio = new CalendarioObrigatorio();
            
            if(rs.next()){
                
                calendarioobrigatorio.setId_calendarioObr(rs.getInt("calendarioob_id"));
                
            }
            
            return calendarioobrigatorio;
            
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

    public ArrayList<CalendarioObrigatorio> buscaVacinaCalendarioObr(CalendarioObrigatorio calendarioObr) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CalendarioObrigatorio> listaCalendario = new ArrayList<>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_VACINA_CALENDARIO);
            pstmt.setInt(1, calendarioObr.getVacina().getId_vacina());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                
                calendarioObr.setId_calendarioObr(rs.getInt("calendarioob_id"));
                calendarioObr.setDose(rs.getInt("calendarioob_totaldoses"));

                listaCalendario.add(calendarioObr);
            }
            
            return listaCalendario;

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

    public void atualizaVacinaCalendarioObr(CalendarioObrigatorio calendarioObr) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ATUALIZA_VACINA_CALENDARIO);
            pstmt.setString(1, calendarioObr.getComentario());
            
            pstmt.setInt(3, calendarioObr.getId_calendarioObr());
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

    public int buscaIdVacinaCalendarioobrEspecifico(CalendarioObrigatorio calendarioobr) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVsCal = null;

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_ID_VACINA_CALENDARIOOBR);
            pstmt.setInt(1, calendarioobr.getId_calendarioObr());
            rsVsCal = pstmt.executeQuery();
            
            rsVsCal.next();

            return rsVsCal.getInt("fk_vacina");

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
    
     public ArrayList<CalendarioObrigatorio> buscarVacinasPorFaixasdeVidaParaocalendario(CalendarioObrigatorio calendarioObr) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CalendarioObrigatorio> listaCalendario = new ArrayList<>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_VACINAS_POR_FAIXA_DE_INTERLAVO);
            pstmt.setLong(1, calendarioObr.getDiasvidainiciomes());
            pstmt.setLong(2, calendarioObr.getDiasvidafinalmes());
            pstmt.setBoolean(3, calendarioObr.isStatus());
            pstmt.setBoolean(4, calendarioObr.isStatus_intervalov());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CalendarioObrigatorio calendarioobra = new CalendarioObrigatorio();
                calendarioobra.setId_calendarioObr(rs.getInt("calendarioob_id"));
                calendarioobra.getVacina().setId_vacina(rs.getInt("fk_vacina"));
                listaCalendario.add(calendarioobra);
            }
            
            return listaCalendario;

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
     
      public CalendarioObrigatorio buscarVacinasParaExibirNoCalendarioUsuario(CalendarioObrigatorio calendarioObr) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CalendarioObrigatorio calendarioobra = new CalendarioObrigatorio();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_VACINACAO_PARA_EXIBIR_CARDENETA);
            pstmt.setLong(1, calendarioObr.getDiasvidainiciomes());
            pstmt.setLong(2, calendarioObr.getDiasvidafinalmes());
            pstmt.setBoolean(3, calendarioObr.isStatus());
            pstmt.setBoolean(4, calendarioObr.getIntervalov().isAtivoOuNao());
            pstmt.setInt(5, calendarioObr.getVacina().getId_vacina());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                calendarioobra.setId_calendarioObr(rs.getInt("calendarioob_id"));
                calendarioobra.getVacina().setNome(rs.getString("vacina_nome"));
                calendarioobra.getVacina().setTipo(TipoVacina.valueOf(rs.getString("vacina_tipo")));
                calendarioobra.getVacina().setDescricao(rs.getString("vacina_descricao"));
                calendarioobra.getIntervalov().setDose(rs.getInt("intervalov_dose"));
                calendarioobra.getIntervalov().setDias(rs.getInt("intervalov_dias"));
       
            }
            
            return calendarioobra;

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
      
      public ArrayList<CalendarioObrigatorio> buscarVacinasDisponiveisDesdeoCadastro(CalendarioObrigatorio calendarioObr) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        ArrayList<CalendarioObrigatorio> listaCalendario = new ArrayList<>();
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCAR_VACINAS_DESDE_O_CADASTRO);
            pstmt.setLong(1, calendarioObr.getDiasvidainiciomes());
            pstmt.setBoolean(2, calendarioObr.isStatus());
            pstmt.setBoolean(3, calendarioObr.getIntervalov().isAtivoOuNao());
            rs = pstmt.executeQuery();
            if (rs.next()) {
               
                CalendarioObrigatorio calendarioobra = new CalendarioObrigatorio();
                calendarioobra.setId_calendarioObr(rs.getInt("calendarioob_id"));
                calendarioobra.getVacina().setId_vacina(rs.getInt("fk_vacina"));
                listaCalendario.add(calendarioobra);
       
            }
            
            return listaCalendario;

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
