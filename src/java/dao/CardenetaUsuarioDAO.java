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
import model.CadernetaUsuario;
import model.TipoVacina;
import util.ConectaBanco;

/**
 *
 * @author nelson_amaral
 */
public class CardenetaUsuarioDAO {

    private final String CADASTRAR_VACINA_OBRIGATORIA = "INSERT INTO cardeneta_usuario (fk_usuario, fk_calendarioob,fk_funcionario,fk_posto,caderneta_data,caderneta_hora,caderneta_dose) VALUES (?,?,?,?,?,?,?)";
    private final String CADASTRAR_VACINA_CAMPANHA = "INSERT INTO cardeneta_usuario (fk_usuario, fk_campanha,fk_funcionario,fk_posto,caderneta_data,caderneta_hora) VALUES (?,?,?,?,?,?)";
    private final String CADASTRAR_VACINA = "INSERT INTO cardeneta_usuario (fk_usuario, fk_vacina,fk_funcionario,fk_posto,caderneta_data,caderneta_hora,descricao) VALUES (?,?,?,?,?,?,?)";
    private final String CONSULTAR_VACINACAO_NO_MESATUAL = "SELECT calendario.calendarioob_id, v.vacina_nome, v.vacina_tipo, v.vacina_descricao FROM cardeneta_usuario c, calendario_obrigatorio calendario, vacina v, usuario u WHERE c.fk_calendarioob=calendario.calendarioob_id AND c.fk_usuario=u.usuario_id AND calendario.fk_vacina=v.vacina_id AND c.fk_calendarioob=? AND c.fk_usuario=? AND extract('month' from c.caderneta_data)=? AND extract('year' from c.caderneta_data)=?";
    private final String CONSULTAR_VACINACAO__USUARIO = "SELECT calendario.calendarioob_id, v.vacina_nome, v.vacina_tipo, v.vacina_descricao, interlav.intervalov_dose,interlav.intervalov_dias FROM cardeneta_usuario c, calendario_obrigatorio calendario, vacina v, usuario u, intervalo_vacinacao interlav WHERE c.fk_calendarioob=calendario.calendarioob_id AND c.fk_usuario=u.usuario_id AND calendario.fk_vacina=v.vacina_id AND calendario.calendarioob_id=interlav.fk_calendarioob AND c.fk_calendarioob=? AND c.fk_usuario=? AND interlav.intervalov_dose=? AND c.caderneta_dose=?";
    private final String CONSULTAR_VACINACAO_OBRIGATORIO_INSERIDA = "SELECT v.vacina_nome, v.vacina_tipo, c.fk_usuario, u.usuario_nome, f.funcionario_confen, p.posto_nome, c.caderneta_data, c.caderneta_hora FROM cardeneta_usuario c, vacina v, funcionario f, calendario_obrigatorio co,usuario u, posto p WHERE c.fk_usuario=? AND c.fk_funcionario=f.funcionario_id AND f.fk_usuario=u.usuario_id AND c.fk_calendarioob=co.calendarioob_id AND co.fk_vacina = v.vacina_id AND c.fk_posto=p.posto_id AND caderneta_data >= ? AND caderneta_data <= ? order by caderneta_data desc";
    private final String CONSULTAR_VACINACAO_CAMPANHA_INSERIDA = "SELECT v.vacina_nome, v.vacina_tipo, c.fk_usuario, u.usuario_nome, f.funcionario_confen, p.posto_nome, c.caderneta_data, c.caderneta_hora FROM cardeneta_usuario c, vacina v, funcionario f, campanha ca, usuario u, posto p WHERE c.fk_usuario=? AND c.fk_funcionario=f.funcionario_id AND f.fk_usuario=u.usuario_id AND c.fk_campanha=ca.campanha_id AND ca.fk_vacina=v.vacina_id AND c.fk_posto=p.posto_id AND caderneta_data >= ? AND caderneta_data <= ? order by caderneta_data desc";
    private final String CONSULTAR_VACINACAO_VACINA_INSERIDA = "SELECT v.vacina_nome, v.vacina_tipo, c.fk_usuario, u.usuario_nome, f.funcionario_confen, p.posto_nome, c.caderneta_data, c.caderneta_hora FROM cardeneta_usuario c, vacina v, funcionario f, usuario u, posto p WHERE c.fk_usuario=? AND c.fk_vacina=v.vacina_id AND c.fk_funcionario=f.funcionario_id AND f.fk_usuario=u.usuario_id AND c.fk_posto=p.posto_id AND caderneta_data >= ? AND caderneta_data <= ? order by caderneta_data desc";
    
    public void cadastrarVacinaObrigatoria(CadernetaUsuario cadernetaUsuario){
        Connection conexao = null;
        PreparedStatement pstmt = null;
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRAR_VACINA_OBRIGATORIA);
            pstmt.setInt(1, cadernetaUsuario.getUsuario().getUsuario_id());
            pstmt.setInt(2, cadernetaUsuario.getCalendarioObrigatorio().getId_calendarioObr());           
            pstmt.setInt(3, cadernetaUsuario.getFuncionario().getId_funcionario());
            pstmt.setInt(4, cadernetaUsuario.getPosto().getPosto_id());
            pstmt.setDate(5, new java.sql.Date(cadernetaUsuario.getDateCadastro().getTime()));
            pstmt.setTime(6, new Time(cadernetaUsuario.getHoraCadastro().getTime()));
            pstmt.setInt(7, cadernetaUsuario.getCaderneta_dose());
            
            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(CardenetaUsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(CardenetaUsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public void cadastrarVacinaCampanha(CadernetaUsuario cardenetaUsuario){
        Connection conexao = null;
        PreparedStatement pstmt = null;
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRAR_VACINA_CAMPANHA);
            pstmt.setInt(1, cardenetaUsuario.getUsuario().getUsuario_id());
            pstmt.setInt(2, cardenetaUsuario.getCampanha().getCampanha_id());           
            pstmt.setInt(3, cardenetaUsuario.getFuncionario().getId_funcionario());
            pstmt.setInt(4, cardenetaUsuario.getPosto().getPosto_id());
            pstmt.setDate(5, new java.sql.Date(cardenetaUsuario.getDateCadastro().getTime()));
            pstmt.setTime(6, new Time(cardenetaUsuario.getHoraCadastro().getTime()));
           
            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(CardenetaUsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(CardenetaUsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public void cadastrarVacina(CadernetaUsuario cardenetaUsuario){
        Connection conexao = null;
        PreparedStatement pstmt = null;
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRAR_VACINA);
            pstmt.setInt(1, cardenetaUsuario.getUsuario().getUsuario_id());
            pstmt.setInt(2, cardenetaUsuario.getVacina().getId_vacina());           
            pstmt.setInt(3, cardenetaUsuario.getFuncionario().getId_funcionario());
            pstmt.setInt(4, cardenetaUsuario.getPosto().getPosto_id());
            pstmt.setDate(5, new java.sql.Date(cardenetaUsuario.getDateCadastro().getTime()));
            pstmt.setTime(6, new Time(cardenetaUsuario.getHoraCadastro().getTime()));
            pstmt.setString(7, cardenetaUsuario.getDescricao());
            
            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(CardenetaUsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(CardenetaUsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }  
    
    public CalendarioObrigatorio buscarVacinasParaExibirNoCalendarioUsuario(CadernetaUsuario cardenetausuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CalendarioObrigatorio calendarioobra = new CalendarioObrigatorio();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTAR_VACINACAO_NO_MESATUAL);
            pstmt.setInt(1, cardenetausuario.getCalendarioObrigatorio().getId_calendarioObr());
            pstmt.setInt(2, cardenetausuario.getUsuario().getUsuario_id());
            pstmt.setInt(3, cardenetausuario.getDateCadastro().getMonth());
            pstmt.setInt(4, cardenetausuario.getDateCadastro().getYear());
            rs = pstmt.executeQuery();
            if (rs.next()) {
               
                calendarioobra.setId_calendarioObr(rs.getInt("calendarioob_id"));
                calendarioobra.getVacina().setNome(rs.getString("vacina_nome"));
                calendarioobra.getVacina().setTipo(TipoVacina.valueOf(rs.getString("vacina_tipo")));
                calendarioobra.getVacina().setDescricao(rs.getString("vacina_descricao"));
                calendarioobra.getIntervalov().setDose(rs.getInt("intervalov_dose"));
                calendarioobra.getIntervalov().setDias(rs.getInt("intervalov_dias"));
                calendarioobra.setVacinacao_concluida(1);
            }
            
            return calendarioobra;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(CardenetaUsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(CardenetaUsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public CalendarioObrigatorio consultarVacinaNaCadernetaUsuario(CadernetaUsuario cardenetausuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CalendarioObrigatorio calendarioobra = new CalendarioObrigatorio();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTAR_VACINACAO__USUARIO);
            pstmt.setInt(1, cardenetausuario.getCalendarioObrigatorio().getId_calendarioObr());
            pstmt.setInt(2, cardenetausuario.getUsuario().getUsuario_id());
            pstmt.setInt(3, cardenetausuario.getCalendarioObrigatorio().getIntervalov().getDose());
            pstmt.setInt(4, cardenetausuario.getCaderneta_dose());
            
            rs = pstmt.executeQuery();
            if (rs.next()) {
               
                calendarioobra.setId_calendarioObr(rs.getInt("calendarioob_id"));
                calendarioobra.getVacina().setNome(rs.getString("vacina_nome"));
                calendarioobra.getVacina().setTipo(TipoVacina.valueOf(rs.getString("vacina_tipo")));
                calendarioobra.getVacina().setDescricao(rs.getString("vacina_descricao"));
                calendarioobra.getIntervalov().setDose(rs.getInt("intervalov_dose"));
                calendarioobra.getIntervalov().setDias(rs.getInt("intervalov_dias"));
                calendarioobra.setVacinacao_concluida(1);
            }
            
            return calendarioobra;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(CardenetaUsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(CardenetaUsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
     public ArrayList<CadernetaUsuario> consultarVacinasObrigatoriasJaInseridas(CadernetaUsuario cadernetausuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CadernetaUsuario> listvacinasOb = new ArrayList<>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTAR_VACINACAO_OBRIGATORIO_INSERIDA);
            pstmt.setInt(1, cadernetausuario.getUsuario().getUsuario_id());
            pstmt.setDate(2, new java.sql.Date(cadernetausuario.getCaderneta_datainicio().getTime()));
            pstmt.setDate(3, new java.sql.Date(cadernetausuario.getCardeneta_datafinal().getTime()));
            
            rs = pstmt.executeQuery();
            while (rs.next()) {
               CadernetaUsuario cadernetau = new CadernetaUsuario();
                cadernetau.getVacina().setNome(rs.getString("vacina_nome"));
                cadernetau.getVacina().setTipo(TipoVacina.valueOf(rs.getString("vacina_tipo")));
                cadernetau.getUsuario().setUsuario_id(rs.getInt("fk_usuario"));
                cadernetau.getUsuario().setNome(rs.getString("usuario_nome"));
                cadernetau.getFuncionario().setCofen(rs.getString("funcionario_confen"));
                cadernetau.getPosto().setPosto_nome(rs.getString("posto_nome"));
                cadernetau.setDateCadastro(rs.getDate("caderneta_data"));
                cadernetau.setHoraCadastro(rs.getTime("caderneta_hora"));         
                listvacinasOb.add(cadernetau);
            }
            
            return listvacinasOb;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(CardenetaUsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(CardenetaUsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
     
     public ArrayList<CadernetaUsuario> consultarVacinaCampanhaJaInseridas(CadernetaUsuario cadernetausuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        ArrayList<CadernetaUsuario> listvacinasCampanha = new ArrayList<>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTAR_VACINACAO_CAMPANHA_INSERIDA);
            pstmt.setInt(1, cadernetausuario.getUsuario().getUsuario_id());
            pstmt.setDate(2, new java.sql.Date(cadernetausuario.getCaderneta_datainicio().getTime()));
            pstmt.setDate(3, new java.sql.Date(cadernetausuario.getCardeneta_datafinal().getTime()));
            
             rs = pstmt.executeQuery();
            while (rs.next()) {
                CadernetaUsuario cadernetau = new CadernetaUsuario();
                cadernetau.getVacina().setNome(rs.getString("vacina_nome"));
                cadernetau.getVacina().setTipo(TipoVacina.valueOf(rs.getString("vacina_tipo")));
                cadernetau.getUsuario().setUsuario_id(rs.getInt("fk_usuario"));
                cadernetau.getUsuario().setNome(rs.getString("usuario_nome"));
                cadernetau.getFuncionario().setCofen(rs.getString("funcionario_confen"));
                cadernetau.getPosto().setPosto_nome(rs.getString("posto_nome"));
                cadernetau.setDateCadastro(rs.getDate("caderneta_data"));
                cadernetau.setHoraCadastro(rs.getTime("caderneta_hora"));               
                listvacinasCampanha.add(cadernetau);
            }
            
            return listvacinasCampanha;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(CardenetaUsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(CardenetaUsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
     
     public ArrayList<CadernetaUsuario> consultarVacinaJaInseridas(CadernetaUsuario cadernetausuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        ArrayList<CadernetaUsuario> listvacinasvacinas = new ArrayList<>();
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTAR_VACINACAO_VACINA_INSERIDA);
            pstmt.setInt(1, cadernetausuario.getUsuario().getUsuario_id());
            pstmt.setDate(2, new java.sql.Date(cadernetausuario.getCaderneta_datainicio().getTime()));
            pstmt.setDate(3, new java.sql.Date(cadernetausuario.getCardeneta_datafinal().getTime()));
            
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CadernetaUsuario cadernetau = new CadernetaUsuario();
                cadernetau.getVacina().setNome(rs.getString("vacina_nome"));
                cadernetau.getVacina().setTipo(TipoVacina.valueOf(rs.getString("vacina_tipo")));
                cadernetau.getUsuario().setUsuario_id(rs.getInt("fk_usuario"));
                cadernetau.getUsuario().setNome(rs.getString("usuario_nome"));
                cadernetau.getFuncionario().setCofen(rs.getString("funcionario_confen"));
                cadernetau.getPosto().setPosto_nome(rs.getString("posto_nome"));
                cadernetau.setDateCadastro(rs.getDate("caderneta_data"));
                cadernetau.setHoraCadastro(rs.getTime("caderneta_hora"));
                
                listvacinasvacinas.add(cadernetau);
            }
            
            return listvacinasvacinas;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(CardenetaUsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(CardenetaUsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
}
