/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import model.Campanha;
import model.Cidade;
import model.Endereco;
import model.Estado;
import model.Posto;
import model.TipoVacina;
import model.Vacina;
import util.ConectaBanco;

/**
 *
 * @author nelson_amaral
 */
public class CampanhaDAO {

    private final String CADASTRAR_CAMPANHA = "INSERT INTO campanha(campanha_nome, campanha_inicio, campanha_status, campanha_prevista, fk_vacina)VALUES ( ?, ?, ?, ?, ?)RETURNING campanha_id;";
    private final String FINALIZAR_CAMPANHA = "UPDATE campanha SET campanha_final=(SELECT current_date), campanha_status=false WHERE campanha_id=?;";
    private final String BUSCA_CAMPANHA = "SELECT * FROM campanha order by campanha_nome,campanha_status desc";
    private final String BUSCA_CAMPANHA_UNICO = "SELECT * FROM campanha WHERE campanha_id = ?;";
    private final String ATUALIZA_CAMPANHA = "UPDATE campanha SET campanha_nome=? WHERE campanha_id=?;";
    private final String BUSCA_CAMPANHA_ATIVA = "SELECT c.campanha_id, c.campanha_nome, c.campanha_inicio, c.campanha_final, c.campanha_prevista, c.campanha_obs, v.vacina_id, v.vacina_nome, v.vacina_tipo FROM campanha c, vacina v WHERE campanha_status=? AND c.fk_vacina=v.vacina_id";
    private final String BUSCA_ESTADO = "SELECT estado_id, estado_nome FROM estado;";
    private final String BUSCA_CIDADE_P_ESTADO = "SELECT *  FROM cidade WHERE cidade_estado=?";
    private final String BUSCA_CIDADE_P_ID = "SELECT *  FROM cidade WHERE cidade_id=?";
    private final String BUSCA_TODAS_CIDADE = "SELECT *  FROM cidade ";

    //Jobs de serviços
    private final String CAMPANHA_JOB = "UPDATE campanha SET  campanha_status=false, campanha_final=(SELECT current_date) WHERE campanha_final is null and campanha_prevista between campanha_inicio and (SELECT current_date) RETURNING campanha_id";
    private final String ATUALIZA_IDADE = "UPDATE usuario SET usuario_idade= ((current_date - usuario_nascimento )/360)WHERE usuario_idade != ((current_date - usuario_nascimento )/360);";

    public int cadastraCampanha(Campanha campanha) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRAR_CAMPANHA);

            pstmt.setString(1, campanha.getCampanha_nome());
            pstmt.setDate(2, campanha.getCampanha_inicio());
            pstmt.setBoolean(3, campanha.isCampanha_status());
            pstmt.setDate(4, campanha.getCampanha_prevista());
            pstmt.setInt(5, campanha.getVacina().getId_vacina());

            rs = pstmt.executeQuery();
            if (rs.next()) {
                campanha.setCampanha_id(rs.getInt("campanha_id"));
            }
            return campanha.getCampanha_id();

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

    public void finalizaCampanha(Campanha campanha) {
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(FINALIZAR_CAMPANHA);

            pstmt.setLong(1, campanha.getCampanha_id());

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

    public ArrayList<Campanha> buscaCampanha() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVsCal = null;
        ArrayList<Campanha> listaCampanha = new ArrayList<>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_CAMPANHA);
            rsVsCal = pstmt.executeQuery();
            while (rsVsCal.next()) {
                Vacina vacina = new Vacina();
                Campanha campanha = new Campanha();
                campanha.setVacina(vacina);
                campanha.setCampanha_id(rsVsCal.getInt("campanha_id"));
                campanha.setCampanha_nome(rsVsCal.getString("campanha_nome"));
                campanha.setCampanha_inicio(rsVsCal.getDate("campanha_inicio"));
                campanha.setCampanha_final(rsVsCal.getDate("campanha_final"));
                campanha.setCampanha_status(rsVsCal.getBoolean("campanha_status"));
                campanha.getVacina().setId_vacina(rsVsCal.getInt("fk_vacina"));
                campanha.setCampanha_prevista(rsVsCal.getDate("campanha_prevista"));
                campanha.setCampanha_obs(rsVsCal.getString("campanha_obs"));
                listaCampanha.add(campanha);
            }
            return listaCampanha;

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

    public Campanha buscaCampanhaUnico(Campanha camp) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVsCal = null;
        Campanha campanha = new Campanha();

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_CAMPANHA_UNICO);
            pstmt.setInt(1, camp.getCampanha_id());
            rsVsCal = pstmt.executeQuery();
            if (rsVsCal.next()) {
                Vacina vacina = new Vacina();
                campanha.setVacina(vacina);
                campanha.setCampanha_id(rsVsCal.getInt("campanha_id"));
                campanha.setCampanha_nome(rsVsCal.getString("campanha_nome"));
                campanha.setCampanha_inicio(rsVsCal.getDate("campanha_inicio"));
                campanha.setCampanha_final(rsVsCal.getDate("campanha_final"));
                campanha.setCampanha_status(rsVsCal.getBoolean("campanha_status"));
                campanha.getVacina().setId_vacina(rsVsCal.getInt("fk_vacina"));

            }
            return campanha;

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

    public void atualizaCampanha(Campanha campanha) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ATUALIZA_CAMPANHA);
            pstmt.setString(1, campanha.getCampanha_nome());
            pstmt.setLong(2, campanha.getCampanha_id());

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

    public ArrayList<Campanha> buscaCampanhasAtivas() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Campanha> listaCampanha = new ArrayList<>();
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_CAMPANHA_ATIVA);
            pstmt.setBoolean(1, true);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                Vacina vacina = new Vacina();
                Campanha campanha = new Campanha();
                campanha.setVacina(vacina);
                campanha.setCampanha_id(rs.getInt("campanha_id"));
                campanha.setCampanha_nome(rs.getString("campanha_nome"));
                campanha.setCampanha_inicio(rs.getDate("campanha_inicio"));
                campanha.setCampanha_final(rs.getDate("campanha_final"));
                campanha.getVacina().setId_vacina(rs.getInt("vacina_id"));
                campanha.getVacina().setNome(rs.getString("vacina_nome"));
                campanha.getVacina().setTipo(TipoVacina.valueOf(rs.getString("vacina_tipo")));
                campanha.setCampanha_prevista(rs.getDate("campanha_prevista"));
                campanha.setCampanha_obs(rs.getString("campanha_obs"));

                listaCampanha.add(campanha);
            }
            return listaCampanha;

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

    /* API
    public void cadastraLocalEstadoCampanha(Estado estado) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
       
                try {
                    conexao = ConectaBanco.getConexao();
                    pstmt = conexao.prepareStatement(CADASTRO_ESTADO);
                    pstmt.setString(1, estado.getNome());
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
        
    public void cadastraLocalCidadeCampanha(Estado estado) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
       
                try {
                    conexao = ConectaBanco.getConexao();
                    pstmt = conexao.prepareStatement(CADASTRO_CIDADE);
                    pstmt.setString(1, estado.getNome());
                    pstmt.setInt(2, estado.getId());
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
    
    
    public boolean verificaEstadoCampanha(Estado estado) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVsCal = null;
        boolean existe = false;
                try {
                    conexao = ConectaBanco.getConexao();
                    pstmt = conexao.prepareStatement(VERIFICA_ESTADO);
                    pstmt.setString(1, estado.getNome());
                     rsVsCal = pstmt.executeQuery();
                    if (rsVsCal.next()) {
                        if(rsVsCal.getString("estado_nome") == null ? estado.getNome() == null : rsVsCal.getString("estado_nome").equals(estado.getNome())){
                            existe = true;
                            
                        }

                    }
                    return existe;
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
    public boolean verificaCidadeCampanha(String cidade) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVsCal = null;
        boolean existe = false;
                try {
                    conexao = ConectaBanco.getConexao();
                    pstmt = conexao.prepareStatement(VERIFICA_CIDADE);
                    pstmt.setString(1, cidade);
                     rsVsCal = pstmt.executeQuery();
                    if (rsVsCal.next()) {
                        if(rsVsCal.getString("cidade_nome") == null ? cidade == null : rsVsCal.getString("estado_nome").equals(cidade)){
                            existe = true;
                            
                        }

                    }
                    return existe;
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
     */
    public ArrayList<Estado> buscaEstados() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVsCal = null;
        ArrayList<Estado> listaestado = new ArrayList<Estado>();

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_ESTADO);
            rsVsCal = pstmt.executeQuery();

            while (rsVsCal.next()) {
                Estado estado = new Estado();
                estado.setId(rsVsCal.getInt("estado_id"));
                estado.setNome(rsVsCal.getString("estado_nome"));
                listaestado.add(estado);

            }
            return listaestado;

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

    public ArrayList<Cidade> buscaCidadeEstados(int id_estado) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVsCal = null;
        ArrayList<Cidade> listaCidade = new ArrayList<Cidade>();

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_CIDADE_P_ESTADO);
            pstmt.setInt(1, id_estado);
            rsVsCal = pstmt.executeQuery();
            if (rsVsCal.next()) {
                Cidade cidade = new Cidade();
                cidade.setId_cidade(rsVsCal.getInt("cidade_id"));
                cidade.setNome_cidade(rsVsCal.getString("cidade_nome"));
                cidade.setId_estado_cidade(rsVsCal.getInt("cidade_estado"));
                listaCidade.add(cidade);

            }
            return listaCidade;

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

    public Cidade buscaCidadePotId(int id_cidade) {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVsCal = null;
        Cidade cidade = new Cidade();

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_CIDADE_P_ID);
            pstmt.setInt(1, id_cidade);
            rsVsCal = pstmt.executeQuery();
            if (rsVsCal.next()) {
                cidade.setId_cidade(rsVsCal.getInt("cidade_id"));
                cidade.setNome_cidade(rsVsCal.getString("cidade_nome"));
                cidade.setId_estado_cidade(rsVsCal.getInt("cidade_estado"));

            }
            return cidade;

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

    public ArrayList<Cidade> buscaCidadeTodos() {
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rsVsCal = null;
        ArrayList<Cidade> listaCidade = new ArrayList<Cidade>();

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(BUSCA_TODAS_CIDADE);
            rsVsCal = pstmt.executeQuery();
            while (rsVsCal.next()) {
                Cidade cidade = new Cidade();
                cidade.setId_cidade(rsVsCal.getInt("cidade_id"));
                cidade.setNome_cidade(rsVsCal.getString("cidade_nome"));
                cidade.setId_estado_cidade(rsVsCal.getInt("cidade_estado"));
                listaCidade.add(cidade);

            }
            return listaCidade;

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

    public ArrayList<Integer> jobCampanha() {
        Connection conexao = null;
        PreparedStatement pstmt;
        ResultSet rs;

        ArrayList<Integer> listIdCampanhas = new ArrayList<>();

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CAMPANHA_JOB);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                listIdCampanhas.add(rs.getInt("campanha_id"));
            }

            return listIdCampanhas;

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

    public void jobUsuarioIdade() {
        Connection conexao = null;
        PreparedStatement pstmt = null;

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ATUALIZA_IDADE);

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
}
