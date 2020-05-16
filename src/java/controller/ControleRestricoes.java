/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.RestricaoDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Restricao;
import util.ValidaFormes;

/**
 *
 * @author Victor Aguiar
 */
public class ControleRestricoes extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = ValidaFormes.formulario(request.getParameter("acao"));
            switch (acao) {
                case "Cadastrar":
                    cadastro(request, response);
                    break;
                case "Listar":
                    listarRestricoes(request, response);
                    break;
                case "Deletar":
                    delete(request, response);
                    break;
                case "Editar":
                    editar(request, response);
                    break;
                case "Confirma":
                    atualizaRestricao(request, response);
                    break;
                case "Cadastrar Vacinas":
                    boxVacinas(request, response);
                    break;
                default:
                    break;
            }

        } catch (Exception erro) {
            System.out.println("Erro (ControleRestricoes)" + erro);
            RequestDispatcher rd = request.getRequestDispatcher("paginas_erro/erro.jsp");
            rd.forward(request, response);
        }
    }

    private void cadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Restricao restricao = new Restricao();
        restricao.setRestricao_nome(ValidaFormes.formulario(request.getParameter("txtNome")));
        restricao.setRestricao_tipo(ValidaFormes.formulario(request.getParameter("txtTipo")));
        restricao.setRestricao_status(true);

        RestricaoDAO restricaoDAO = new RestricaoDAO();
        restricaoDAO.cadastaNovoRestricao(restricao);

        ArrayList<Restricao> listrestricao = restricaoDAO.consultarRestricao();

        request.setAttribute("listarestricaoz", listrestricao);
        RequestDispatcher rd = request.getRequestDispatcher("/paginas_restricoes/consultar_restricoes.jsp");
        rd.forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Restricao restricao = new Restricao();
        restricao.setRestricao_id(Integer.parseInt(ValidaFormes.formulario(request.getParameter("id_restricao"))));

        RestricaoDAO restricaoDAO = new RestricaoDAO();
        restricaoDAO.deletarRestricao(restricao);

        ArrayList<Restricao> listrestricaoda = restricaoDAO.consultarRestricao();

        request.setAttribute("listarestricaoz", listrestricaoda);
        RequestDispatcher rdd = request.getRequestDispatcher("/paginas_restricoes/consultar_restricoes.jsp");
        rdd.forward(request, response);
    }

    //Metodo referencia o select no forme do cadastro da vacina
    private void boxVacinas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RestricaoDAO restricaoDAO = new RestricaoDAO();
        ArrayList<Restricao> listrestricaoz = restricaoDAO.consultarRestricao();

        request.setAttribute("listarestricao", listrestricaoz);
        RequestDispatcher rdz = request.getRequestDispatcher("/paginas_vacina/cadastrar_vacina.jsp");
        rdz.forward(request, response);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Restricao restricao = new Restricao();
        restricao.setRestricao_nome(ValidaFormes.formulario(request.getParameter("nome_restricao")));
        restricao.setRestricao_tipo(ValidaFormes.formulario(request.getParameter("tipo_restricao")));
        restricao.setRestricao_id(Integer.parseInt(ValidaFormes.formulario(request.getParameter("id_restricao"))));

        request.setAttribute("restricao", restricao);
        RequestDispatcher rdz = request.getRequestDispatcher("paginas_restricoes/atualiza_restricoes.jsp");
        rdz.forward(request, response);
    }

    private void atualizaRestricao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Restricao restricao = new Restricao();
        restricao.setRestricao_nome(ValidaFormes.formulario(request.getParameter("txtNome")));
        restricao.setRestricao_tipo(ValidaFormes.formulario(request.getParameter("txtTipo")));
        restricao.setRestricao_id(Integer.parseInt(ValidaFormes.formulario(request.getParameter("txtId"))));

        RestricaoDAO restricaoDAO = new RestricaoDAO();
        restricaoDAO.atualizaRestricao(restricao);

        listarRestricoes(request, response);
    }

    private void listarRestricoes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RestricaoDAO restricaoDAO = new RestricaoDAO();
        ArrayList<Restricao> listrestricao = restricaoDAO.consultarRestricao();

        request.setAttribute("listarestricaoz", listrestricao);
        RequestDispatcher rd = request.getRequestDispatcher("/paginas_restricoes/consultar_restricoes.jsp");
        rd.forward(request, response);
    }

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
