/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CalendarioObrigatorioDAO;
import dao.IntervaloVacinacaoDAO;
import dao.RestricaoDAO;
import dao.UsuarioDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Vacina;
import dao.VacinaDAO;
import model.CalendarioObrigatorio;
import model.IntervaloVacinacao;
import model.Restricao;
import model.TipoVacina;
import model.Usuario;
import util.ValidaFormes;

/**
 *
 * @author nelson_amaral
 */
@WebServlet(name = "ControleVacina", urlPatterns = {"/ControleVacina"})
public class ControleVacina extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = ValidaFormes.formulario(request.getParameter("acao"));
            switch (acao) {
                case "Cadastrar Vacinas": {
                    cadastro(request, response);
                    break;
                }
                case "Consultar Vacinas": {
                    consultarVacinas(request, response);
                    break;
                }
                case "Deletar": {
                    deletar(request, response);
                    break;
                }
                case "Editar Vacina": {
                    editarVacina(request, response);
                    break;
                }
                case "Confirma": {
                    confirma(request, response);
                    break;
                }
                case "ConsultarTodasVacinas": {
                    consultarTodasVacinas(request, response);
                    break;
                }
                /**
                 * *ControleVacina_has_restricao**
                 */
                case "CadastraRestricao": {
                    cadastraV_H_R(request, response);
                    break;
                }
                case "DeletaRestricao": {
                    deleteV_H_R(request, response);
                    break;
                }
                case "ListaRestricoes": {
                    listarV_H_R(request, response);
                    break;
                }
                default:
                    break;
            }
        } catch (Exception erro) {
            System.out.println("Erro (ControleVacina)" + erro);
            RequestDispatcher rd = request.getRequestDispatcher("paginas_usuario/erro.jsp");
            rd.forward(request, response);
        }
    }

    private void cadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Vacina vacina = new Vacina();
        vacina.setNome(ValidaFormes.formulario(request.getParameter("txtNome")));
        vacina.setDescricao(ValidaFormes.formulario(request.getParameter("txtdescricao")));
        vacina.setStatus(true);

        String tipo = (ValidaFormes.formulario(request.getParameter("txtTipo")));
        switch (tipo) {
            case "Vacinas_vivas_atenuadas":
                vacina.setTipo(TipoVacina.Vacinas_vivas_atenuadas);
                break;
            case "Vacinas_mortas_inactivadas":
                vacina.setTipo(TipoVacina.Vacinas_mortas_inactivadas);
                break;
            case "Vacinas_sub_unitarias":
                vacina.setTipo(TipoVacina.Vacinas_sub_unitarias);
                break;
            default:
                break;
        }

        VacinaDAO vacinaDAO = new VacinaDAO();
        vacina.setId_vacina(vacinaDAO.cadastaNovoVacina(vacina));

        String[] restricaofk = request.getParameterValues("txtRestricaoFK");
        if (restricaofk != null) {
            for (String r : restricaofk) {

                int id_restricao = Integer.parseInt(r);
                int id_vacina = vacina.getId_vacina();
                vacinaDAO.cadastroVacina_Has_Restricao(id_restricao, id_vacina);

            }
        }

        request.setAttribute("msg", "Cadastrado com Sucesso");
        consultarVacinas(request, response);
    }

    private void consultarVacinas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        VacinaDAO vacinaDAO = new VacinaDAO();

        ArrayList<Vacina> listaVacina = vacinaDAO.consultarVacinas();

        request.setAttribute("msg", request.getAttribute("msg"));
        request.setAttribute("listavacina", listaVacina);
        RequestDispatcher rd = request.getRequestDispatcher("/paginas_vacina/consultar_vacina.jsp");
        rd.forward(request, response);
    }

    private void deletar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Vacina vacina = new Vacina();
        vacina.setId_vacina(Integer.parseInt(ValidaFormes.formulario(request.getParameter("id_vacina"))));

        CalendarioObrigatorio calendarioobrigatorio = new CalendarioObrigatorio();
        calendarioobrigatorio.setVacina(vacina);
        calendarioobrigatorio.setStatus(false);

        CalendarioObrigatorioDAO calendarioobrigatorioDAO = new CalendarioObrigatorioDAO();
        calendarioobrigatorio = calendarioobrigatorioDAO.excluirVacinaCalendarioObrPorIdVacina(calendarioobrigatorio);

        IntervaloVacinacao intervalovacinacao = new IntervaloVacinacao();
        intervalovacinacao.setCalendarioObr(calendarioobrigatorio);

        IntervaloVacinacaoDAO intervalovacinacaoDAO = new IntervaloVacinacaoDAO();
        intervalovacinacaoDAO.excluirIntervaloVacinacao(intervalovacinacao);

        VacinaDAO vacinaDAO = new VacinaDAO();
        vacinaDAO.excluirVacina(vacina.getId_vacina());

        String msg = "Excluido com Sucesso!";

        request.setAttribute("msg", msg);
        consultarVacinas(request, response);
    }

    private void editarVacina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Vacina vacina = new Vacina();
        VacinaDAO vacinaDAO = new VacinaDAO();

        vacina.setId_vacina(Integer.parseInt(ValidaFormes.formulario(request.getParameter("id_vacina"))));
        
        vacina = vacinaDAO.buscarVacinaUnica(vacina);

        request.setAttribute("vacina", vacina);
        RequestDispatcher rd = request.getRequestDispatcher("/paginas_vacina/atualizar_vacina.jsp");
        rd.forward(request, response);
    }

    private void confirma(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Vacina vacina = new Vacina();

        vacina.setNome(ValidaFormes.formulario(request.getParameter("txtNome")));
        vacina.setTipo(TipoVacina.valueOf(ValidaFormes.formulario(request.getParameter("txtTipo"))));       
        vacina.setId_vacina(Integer.parseInt(ValidaFormes.formulario(request.getParameter("txtId"))));

        vacina.setDescricao(ValidaFormes.formulario(request.getParameter("txtdescricao")));

        VacinaDAO vacinaDAO = new VacinaDAO();
        vacinaDAO.atualizar_vacina(vacina);

        String msg = "Atualizado com Sucesso!";

        request.setAttribute("msg", msg);
        consultarVacinas(request, response);
    }

    /**
     * *********************************ControleVacina_has_restricao*******************************************
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void cadastraV_H_R(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id_vacina = Integer.parseInt(request.getParameter("id_vacina"));

        VacinaDAO vacinaDAO = new VacinaDAO();

        String[] restricaofk = request.getParameterValues("txtRestricaoFK");
        if (restricaofk != null) {
            for (String r : restricaofk) {
                int id_restricao = Integer.parseInt(r);

                vacinaDAO.cadastroVacina_Has_Restricao(id_restricao, id_vacina);

            }
        }

        String msg = "Restrição adicionada com sucesso!";

        request.setAttribute("msg", msg);
        listaV_H_R2(request, response, id_vacina);
    }

    private void deleteV_H_R(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id_restricao = Integer.parseInt(request.getParameter("id_restricao"));
        int id_vacina = Integer.parseInt(request.getParameter("id_vacina"));

        VacinaDAO vacinaDAO = new VacinaDAO();
        vacinaDAO.excluirVacina_Has_Restricao(id_vacina, id_restricao);

        String msg = "Restrição retirada com sucesso!";

        request.setAttribute("msg", msg);
        listaV_H_R2(request, response, id_vacina);
    }

    private void listarV_H_R(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id_vacina = Integer.parseInt(request.getParameter("id"));

        listaV_H_R2(request, response, id_vacina);

    }

    private void listaV_H_R2(HttpServletRequest request, HttpServletResponse response, int id_vacina) throws ServletException, IOException {

        ArrayList<Integer> listaV_H_R = new ArrayList<>();

        Vacina vacina = new Vacina();
        vacina.setId_vacina(id_vacina);

        VacinaDAO vacinaDAO = new VacinaDAO();

        ArrayList<Restricao> listRestricao;
        //Buscas as Restricoes relacionadas o id da vacina
        listRestricao = vacinaDAO.exibirVacinaHasRestricao(vacina);

        RestricaoDAO restricaoDAO = new RestricaoDAO();
        ArrayList<Restricao> listaRestricaoVacina = new ArrayList<>();

        //Busca toda as restriçoes relacionadas aquela vacina
        for (Restricao restricao : listRestricao) {

            listaRestricaoVacina.add(restricaoDAO.consultarUnicaRestricao(restricao.getRestricao_id()));

        }

        vacina.setRestricoes(listaRestricaoVacina);

        //Busca todas as restricoes
        ArrayList<Restricao> listaRestricao = restricaoDAO.consultarRestricao();

        request.setAttribute("msg", request.getAttribute("msg"));
        request.setAttribute("listaRestricoes", listaRestricao);
        request.setAttribute("vacina", vacina);
        RequestDispatcher rdz = request.getRequestDispatcher("/paginas_vacina/vacina_altera_restricoes.jsp");
        rdz.forward(request, response);
    }

    private void consultarTodasVacinas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String msg;

        Usuario usuario = new Usuario();
        usuario.setRg(request.getParameter("usuario_rg"));

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuario = usuarioDAO.buscaUsuarioPorRg(usuario);

        ArrayList<Vacina> listaVacinas = new ArrayList<>();
        
        if (usuario.getUsuario_id() != 0) {
            
            VacinaDAO vacinaDAO = new VacinaDAO();
           
            listaVacinas = vacinaDAO.consultarVacinas();
            
            msg = "Resultado da pesquisa!";
            
        } else {

            msg = "Usuario não encontrado!";
            
        }

        request.setAttribute("msg", msg);
        request.setAttribute("id_usuario", usuario.getUsuario_id());
        request.setAttribute("listavacinas", listaVacinas);
        RequestDispatcher rd = request.getRequestDispatcher("/paginas_enfermeiro/listar_VacinasAJAX.jsp");
        rd.forward(request, response);
    }

    /**
     * ********************************************************************************************************
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
