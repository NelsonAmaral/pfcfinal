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
import model.TipoVacina;
import model.Vacina;
import util.ConectaBanco;


/**
 *
 * @author nelson_amaral
 */
public class VacinaDAO {

    private final String CADASTRA_NOVA_VACINA = "INSERT INTO vacina (vacina_nome,vacina_tipo, vacina_descricao, vacina_status, vacina_calendarioob) VALUES (?, ?, ?, ?, ?) RETURNING vacina_id";
    private final String CONSULTAR_VACINAS = "SELECT * FROM vacina WHERE vacina_status=? order by vacina_nome";
    private final String DELETAR_VACINA = "UPDATE vacina SET vacina_status=false WHERE vacina_id=? RETURNING vacina_status;";
    private final String CONSULTAR_VACINA_UNICO = "SELECT * FROM vacina WHERE vacina_id=? and vacina_status != false";
    private final String UPDATE_VACINA = "UPDATE vacina SET vacina_nome=?, vacina_descricao=?, vacina_tipo=? WHERE vacina_id=?";
    private final String UPDATE_VACINA_CALENDARIO = "UPDATE vacina SET vacina_calendarioob = ? WHERE vacina_id=?";
    private final String CONSULTAR_VACINAS_CALENDARIO = "SELECT * FROM vacina WHERE vacina_status=? AND vacina_calendarioob = false";
    private final String CONSULTAR_VACINAS_CADASTRADAS_CALENDARIOOB = "SELECT * FROM vacina WHERE vacina_status=? AND vacina_calendarioob = ?";
    
    /*******************Old class V-has-Res*******************/
    private final String CADASTRO_ID_VACINA_HAS_RESTRICAO = "INSERT INTO vacina_has_restricao(fk_vacina, fk_restricoes, status) VALUES (?, ?, true)";
    private final String EXCLUIR_ID_VACINA_HAS_RESTRICAO = "UPDATE vacina_has_restricao SET status = false WHERE fk_vacina = ? AND fk_restricoes = ?";
    private final String BUSCA_RESTRICOES = "SELECT * FROM vacina_has_restricao WHERE fk_vacina = ? AND status != ?";
    private final String CONSULTAR_VACINA_COM_RESTRICOES_IGUAL_USUARIO = "SELECT vhr.fk_vacina FROM vacina_has_restricao vhr,usuario_has_restricao uhr WHERE uhr.fk_restricao=vhr.fk_restricoes AND vhr.fk_vacina=? AND uhr.fk_usuario=? AND vhr.status=? AND uhr.status=? GROUP BY vhr.fk_vacina";
    
    /**
     * @param vacina*
     * @return ******************************************************/
    public Integer cadastaNovoVacina(Vacina vacina) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Integer Vacina_id = 0;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRA_NOVA_VACINA);
            pstmt.setString(1, vacina.getNome());
            pstmt.setString(2, vacina.getTipo().toString());
            pstmt.setString(3, vacina.getDescricao());
            pstmt.setBoolean(4,true);
            pstmt.setBoolean(5,false);

            rs = pstmt.executeQuery();

            if (rs.next()){
                Vacina_id = rs.getInt("vacina_id");
            }

            //Retorna o ID da vacina cadastrada == Este ID é necessario para cadastrar as retricões da vacina
            return Vacina_id;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(UsuarioDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(UsuarioDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }

    }

    public ArrayList<Vacina> consultarVacinas() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTAR_VACINAS);
            pstmt.setBoolean(1, true);
            ArrayList<Vacina> listavacina = new ArrayList<>();
            rs = pstmt.executeQuery();

            while (rs.next()) {

                Vacina vacina = new Vacina();
                vacina.setId_vacina(rs.getInt("vacina_id"));
                vacina.setNome(rs.getString("vacina_nome"));
                vacina.setTipo(TipoVacina.valueOf(rs.getString("vacina_tipo")));
                vacina.setStatus(rs.getBoolean("vacina_status"));
                
                listavacina.add(vacina);
            }
            return listavacina;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(VAcinaDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(VacinaDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }

    }

    public boolean excluirVacina(int id) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean status = true;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(DELETAR_VACINA);           
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
               status = (rs.getBoolean("vacina_status"));
            }

            return status;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(VacinaDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(VacinaDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public Vacina buscarVacinaUnica(Vacina vacinaModelo) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Vacina vacina = new Vacina();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTAR_VACINA_UNICO);
            pstmt.setInt(1, vacinaModelo.getId_vacina());
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                vacina.setId_vacina(rs.getInt("vacina_id"));
                vacina.setNome(rs.getString("vacina_nome"));
                vacina.setTipo(TipoVacina.valueOf(rs.getString("vacina_tipo")));
                vacina.setDescricao(rs.getString("vacina_descricao"));
                vacina.setStatus(rs.getBoolean("vacina_status"));
            }
            
            return vacina;

        } catch (SQLException erro) {
            System.out.println("Erro SQL(VacinaDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(VacinaDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public void atualizar_vacina(Vacina vacina) {
        Connection conexao = null;
        PreparedStatement pstmt = null;       
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(UPDATE_VACINA);
            pstmt.setString(1, vacina.getNome());
            pstmt.setString(2, vacina.getDescricao());
            pstmt.setString(3, vacina.getTipo().toString());
            pstmt.setInt(4, vacina.getId_vacina());

            pstmt.execute();

        } catch (SQLException erro) {
            System.out.println("Erro SQL(VacinaDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(VacinaDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
     public void atualizar_campo_vacinaCalendarioob(Vacina vacina) {
        Connection conexao = null;
        PreparedStatement pstmt = null;       
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(UPDATE_VACINA_CALENDARIO);
            pstmt.setBoolean(1, vacina.isStatus());
            pstmt.setInt(2, vacina.getId_vacina());

            pstmt.execute();

        } catch (SQLException erro) {
            System.out.println("Erro SQL(VacinaDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(VacinaDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    public ArrayList<Vacina> ConsultarVacinasCalendario() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTAR_VACINAS_CALENDARIO);
            pstmt.setBoolean(1, true);
            ArrayList<Vacina> listavacina = new ArrayList<>();
            rs = pstmt.executeQuery();

            while (rs.next()) {

                Vacina vacina = new Vacina();
                vacina.setId_vacina(rs.getInt("vacina_id"));
                vacina.setNome(rs.getString("vacina_nome"));
                vacina.setTipo(TipoVacina.valueOf(rs.getString("vacina_tipo")));
                vacina.setStatus(rs.getBoolean("vacina_status"));
                
               listavacina.add(vacina);
            }
            return listavacina;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(VAcinaDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(VacinaDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }

    }
    
     public ArrayList<Vacina> ConsultarVacinasCadastradasCalendarioOB(Vacina vacina) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTAR_VACINAS_CADASTRADAS_CALENDARIOOB);
            pstmt.setBoolean(1, vacina.isStatus());
            pstmt.setBoolean(2, vacina.isStatusCalendario());
            ArrayList<Vacina> listavacina = new ArrayList<>();
            rs = pstmt.executeQuery();

            while (rs.next()) {

                Vacina vacinaADD= new Vacina();
                vacinaADD.setId_vacina(rs.getInt("vacina_id"));
                vacinaADD.setNome(rs.getString("vacina_nome"));
                vacinaADD.setTipo(TipoVacina.valueOf(rs.getString("vacina_tipo")));
                vacinaADD.setStatus(rs.getBoolean("vacina_status"));
                
               listavacina.add(vacinaADD);
            }
            return listavacina;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(VAcinaDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(VacinaDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }

    }
    
    public boolean atualizaVacinaCalendario(int id) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
       
        boolean status = true;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(UPDATE_VACINA_CALENDARIO);           
            pstmt.setInt(1, id);
            pstmt.execute();

            

            return status;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(VacinaDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(VacinaDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }
    
    /*****************************Inicio has v-has-r**********************************/
    public void cadastroVacina_Has_Restricao(int restricao_id,int vacina_id) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRO_ID_VACINA_HAS_RESTRICAO);
            pstmt.setInt(1, vacina_id);
            pstmt.setInt(2, restricao_id);

            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(Vacina_has_restricaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(Vacina_has_restricaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public void excluirVacina_Has_Restricao(int vacina_id, int restricao_id) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(EXCLUIR_ID_VACINA_HAS_RESTRICAO);
            pstmt.setInt(1, vacina_id);
            pstmt.setInt(2, restricao_id);
            pstmt.execute();
        } catch (SQLException erro) {
            System.out.println("Erro SQL(Vacina_has_restricaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(Vacina_has_restricaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }
    }

    public ArrayList<Restricao> exibirVacinaHasRestricao(Vacina vacina) {
        Connection conexao = null;
        PreparedStatement pstmt;
        ResultSet rs;
        
        ArrayList<Restricao> listaRestricao = new ArrayList<>();
        
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_RESTRICOES);
            pstmt.setInt(1, vacina.getId_vacina());
            pstmt.setBoolean(2, false);
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
                
                Restricao restricao = new Restricao();
                restricao.setRestricao_id(rs.getInt("fk_restricoes"));
                listaRestricao.add(restricao);
                
            }
            return listaRestricao;
        } catch (SQLException erro) {
            System.out.println("Erro SQL(Vacina_has_restricaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(Vacina_has_restricaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }

    }

    public int ConsultarVacinaComrestricoesIguaisAoUsuario(int id_vacina, int id_usuario) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int id = 0;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CONSULTAR_VACINA_COM_RESTRICOES_IGUAL_USUARIO);
            pstmt.setInt(1, id_vacina);
            pstmt.setInt(2, id_usuario);
            pstmt.setBoolean(3, true);
            pstmt.setBoolean(4, true);
            rs = pstmt.executeQuery();
            
            if(rs.next()){
            id = rs.getInt("fk_vacina");
            }
            
            return id;
            
        } catch (SQLException erro) {
            System.out.println("Erro SQL(Vacina_has_restricaoDAO)" + erro);
            throw new RuntimeException(erro);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException erroSQL) {
                    System.out.println("Erro SQL(Vacina_has_restricaoDAO)" + erroSQL);
                    throw new RuntimeException(erroSQL);
                }
            }
        }

    }
}
