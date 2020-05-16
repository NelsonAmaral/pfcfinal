/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CampanhaDAO;
import dao.CidadeCampanhaDAO;
import dao.CidadeDAO;
import dao.UsuarioDAO;
import dao.VacinaDAO;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Campanha;
import model.Cidade;
import model.Estado;
import model.Usuario;
import model.Vacina;
import util.Email;
import util.ValidaFormes;

/**
 *
 * @author nelson_amaral
 */
@WebServlet(name = "ControleCampanha", urlPatterns = {"/ControleCampanha"})
public class ControleCampanha extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = ValidaFormes.formulario(request.getParameter("acao"));
            switch (acao) {
                case "Cadastro":
                    cadastroVacinaNaCampanha(request, response);
                    break;
                case "Criar":
                    cadastroCampanha(request, response);
                    break;
                case "Listar":
                    exibeCampanhas(request, response);
                    break;
                case "Finaliza":
                    finalizaCampanha(request, response);
                    break;
                case "ListVacinasDasCampanhasUsuario":
                    listVacinasCampanhas(request, response);
                    break;
                default:
                    break;
            }

        } catch (IOException | ServletException erro) {
            System.out.println("Erro (ControleCampanha)" + erro);
            RequestDispatcher rd = request.getRequestDispatcher("../paginas_erro/erro.jsp");
            rd.forward(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ControleCampanha.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cadastroCampanha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Campanha campanha = new Campanha();
        campanha.setCampanha_nome(request.getParameter("txtNome"));
        campanha.setCampanha_inicio(Date.valueOf(request.getParameter("txtDateInicio")));
        campanha.setCampanha_prevista(Date.valueOf(request.getParameter("txtDateFinal")));
        campanha.setCampanha_status(true);

        Vacina vacina = new Vacina();
        vacina.setId_vacina(Integer.parseInt(request.getParameter("txtVacinasFK")));
        campanha.setVacina(vacina);
        CampanhaDAO campanhaDAO = new CampanhaDAO();

        campanha.setCampanha_id(campanhaDAO.cadastraCampanha(campanha));

        
        CidadeCampanhaDAO cidadecampanhaDAO = new CidadeCampanhaDAO();
        
        String[] cidades = request.getParameterValues("chcidade");
        if (cidades != null) {
            for (String c : cidades) {          
                campanha.getCidade().setId_cidade(Integer.parseInt(c));
                cidadecampanhaDAO.cadastraCidadeCampanha(campanha);
            }
        }

        campanha.getCidade().setCidades(cidades);
        
        Email email = new Email();

        ArrayList<Usuario> listUsuariosAtingidos = email.CampanhaAberta(campanha);

        //Consultando dados das cidades
        CidadeDAO cidadeDAO = new CidadeDAO();
        Cidade cidade = new Cidade();

        ArrayList<Cidade> listCidades = new ArrayList<>();

        for (String cidade_id : campanha.getCidade().getCidades()) {

            cidade.setId_cidade(Integer.parseInt(cidade_id));

            listCidades.add(cidadeDAO.consultarDadosCidade(cidade));
        }

        request.setAttribute("cidades", listCidades);
        request.setAttribute("campanha", campanha);
        request.setAttribute("listUsuarios", listUsuariosAtingidos);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_campanha/relatorio_envioCampanha.jsp");
        rd.forward(request, response);
    }

    private void cadastroVacinaNaCampanha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        VacinaDAO vacinaDAO = new VacinaDAO();
        ArrayList<Vacina> listaVacina = vacinaDAO.consultarVacinas();

        CampanhaDAO campanhaDAO = new CampanhaDAO();
        ArrayList<Estado> listaEstados = campanhaDAO.buscaEstados();
//        for(Estado es: listaEstados){
//            
//            es.setCidade(campanhaDAO.buscaCidadeEstados(es.getId()));
//            
//        }
        ArrayList<Cidade> listaCidade = campanhaDAO.buscaCidadeTodos();

        request.setAttribute("listaEstados", listaEstados);
        request.setAttribute("listaCidade", listaCidade);
        request.setAttribute("listaVacinas", listaVacina);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_campanha/cadastro_campanha.jsp");
        rd.forward(request, response);
    }

    private void finalizaCampanha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Campanha campanha = new Campanha();
        campanha.setCampanha_id(Integer.parseInt(request.getParameter("campanha_id")));

        CampanhaDAO campanhaDAO = new CampanhaDAO();
        campanhaDAO.finalizaCampanha(campanha);

        exibeCampanhas(request, response);

    }

    private void exibeCampanhas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CampanhaDAO campanhaDAO = new CampanhaDAO();
        ArrayList<Campanha> listaCampanha = campanhaDAO.buscaCampanha();

        VacinaDAO vacinaDAO = new VacinaDAO();
        Vacina vacina = new Vacina();

        for (Campanha cp : listaCampanha) {
            vacina.setId_vacina(cp.getVacina().getId_vacina());
            cp.setVacina(vacinaDAO.buscarVacinaUnica(vacina));
        }

        request.setAttribute("Campanhas", listaCampanha);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_campanha/exibe_campanha.jsp");
        rd.forward(request, response);

    }

    private void listVacinasCampanhas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {

        String msg = null;

        //Modelos
        Usuario usuario = new Usuario();

        //Daos
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        VacinaDAO vacinaDAO = new VacinaDAO();

        String usuario_rg = request.getParameter("usuario_rg");
        usuario.setRg(usuario_rg);

        usuario = usuarioDAO.buscaUsuarioPorRg(usuario);

        //Contador auxiliar
        int cont = 0;

        ArrayList<Campanha> CampanhasPermitidasUsuario = new ArrayList<>();

        //Verificando se a consulta funcionou
        if (usuario.getUsuario_id() != 0) {
            
            int usuario_id = usuario.getUsuario_id();

            CampanhaDAO campanhaDAO = new CampanhaDAO();
            ArrayList<Campanha> listcampanhasativas = campanhaDAO.buscaCampanhasAtivas();

            for (Campanha listcampanha : listcampanhasativas) {

                int retorno_idvacina = vacinaDAO.ConsultarVacinaComrestricoesIguaisAoUsuario(listcampanha.getVacina().getId_vacina(), usuario_id);

                if (retorno_idvacina == 0) {

                    CampanhasPermitidasUsuario.add(listcampanha);

                    //Saber quantos registros tem
                    cont = cont + 1;

                }
            }

        } else {

            msg = "Usuario não encontrado";

        }

        if (cont > 0 & msg == null) {
            msg = "Resultado da pesquisa!";
        } else if (msg == null) {
            msg = "Usuario não possui nenhuma vacina obrigatória neste mês!";
        }

        request.setAttribute("msg", msg);
        request.setAttribute("id_usuario", usuario.getUsuario_id());
        request.setAttribute("campanhaspermitidas", CampanhasPermitidasUsuario);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_enfermeiro/listar_VacinasCampanhaAJAX.jsp");
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
