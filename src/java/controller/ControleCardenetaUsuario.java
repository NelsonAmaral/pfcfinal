/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CardenetaUsuarioDAO;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CadernetaUsuario;
import model.Funcionario;
import util.TratamentoDeData;
import util.ValidaFormes;

/**
 *
 * @author Victor_Aguiar
 */
public class ControleCardenetaUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String acao = ValidaFormes.formulario(request.getParameter("acao"));
            switch (acao) {
                case "CadastrarVacinaObrigatoria":
                    cadastrarVacinaObrigatoria(request, response);
                    break;
                case "CadastrarVacinaCampanha":
                    cadastrarVacinaCampanha(request, response);
                    break;
                case "CadastrarVacina":
                    cadastrarVacina(request, response);
                    break;
                case "ConsultarHistoricoUsuario":
                    consultarHistorico(request, response);
                    break;
                default:
                    break;
            }
        } catch (Exception erro) {
            System.out.println("Erro (ControleEndereco): " + erro);
            javax.servlet.RequestDispatcher rd = request.getRequestDispatcher("paginas_erro/erro.jsp");
            rd.forward(request, response);
        }

    }

    private void cadastrarVacinaObrigatoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String msg;

        CadernetaUsuario cadernetausuario = new CadernetaUsuario();

        cadernetausuario.getCalendarioObrigatorio().setId_calendarioObr(Integer.parseInt(request.getParameter("calendariobr_id")));
        cadernetausuario.setCaderneta_dose(Integer.parseInt(request.getParameter("dose")));
        
        //Atribuindo ID do usuario
        cadernetausuario.getUsuario().setUsuario_id(Integer.parseInt(request.getParameter("id_usuario")));

        //Recuperando usaurio da sessao
        HttpSession sessaoUsuario = request.getSession();
        Funcionario funcionario = (Funcionario) sessaoUsuario.getAttribute("usuarioAutenticado");

        cadernetausuario.getFuncionario().setId_funcionario(funcionario.getId_funcionario());
        cadernetausuario.getPosto().setPosto_id(funcionario.getPosto().getPosto_id());
        
        TratamentoDeData cvDate = new TratamentoDeData();       
        cadernetausuario.setDateCadastro(cvDate.buscaDataAtual());
        cadernetausuario.setHoraCadastro(cvDate.buscaHoraAtual());
        
        CardenetaUsuarioDAO cardenetausuarioDAO = new CardenetaUsuarioDAO();
        cardenetausuarioDAO.cadastrarVacinaObrigatoria(cadernetausuario);

        msg = "Cadastrado com Sucesso!";

        response.sendRedirect("paginas_enfermeiro/registrar_vacinasUsuario.jsp?msg=Cadastrado com Sucesso");


    }

    private void cadastrarVacinaCampanha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String msg;

        CadernetaUsuario cardenetausuario = new CadernetaUsuario();

        cardenetausuario.getCampanha().setCampanha_id(Integer.parseInt(request.getParameter("campanha_id")));

        //Atribuindo ID do usuario
        cardenetausuario.getUsuario().setUsuario_id(Integer.parseInt(request.getParameter("id_usuario")));

        //Recuperando usaurio da sessao
        HttpSession sessaoUsuario = request.getSession();
        Funcionario funcionario = (Funcionario) sessaoUsuario.getAttribute("usuarioAutenticado");

        cardenetausuario.getFuncionario().setId_funcionario(funcionario.getId_funcionario());
        cardenetausuario.getPosto().setPosto_id(funcionario.getPosto().getPosto_id());
        
        TratamentoDeData cvDate = new TratamentoDeData();       
        cardenetausuario.setDateCadastro(cvDate.buscaDataAtual());
        cardenetausuario.setHoraCadastro(cvDate.buscaHoraAtual());
        
        CardenetaUsuarioDAO cardenetausuarioDAO = new CardenetaUsuarioDAO();
        cardenetausuarioDAO.cadastrarVacinaCampanha(cardenetausuario);

        msg = "Cadastrado com Sucesso!";

        response.sendRedirect("paginas_enfermeiro/registrar_vacinasUsuario.jsp?msg=Cadastrado com Sucesso");
    }

    private void cadastrarVacina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String msg;

        CadernetaUsuario cardenetausuario = new CadernetaUsuario();

        cardenetausuario.getVacina().setId_vacina(Integer.parseInt(request.getParameter("vacina_id")));
        cardenetausuario.getVacina().setDescricao(ValidaFormes.formulario(request.getParameter("descricao")));

        //Atribuindo ID do usuario
        cardenetausuario.getUsuario().setUsuario_id(Integer.parseInt(request.getParameter("id_usuario")));

        //Recuperando usaurio da sessao
        HttpSession sessaoUsuario = request.getSession();
        Funcionario funcionario = (Funcionario) sessaoUsuario.getAttribute("usuarioAutenticado");

        cardenetausuario.getFuncionario().setId_funcionario(funcionario.getId_funcionario());
        cardenetausuario.getPosto().setPosto_id(funcionario.getPosto().getPosto_id());
        
        TratamentoDeData cvDate = new TratamentoDeData();       
        cardenetausuario.setDateCadastro(cvDate.buscaDataAtual());
        cardenetausuario.setHoraCadastro(cvDate.buscaHoraAtual());
        
        CardenetaUsuarioDAO cardenetausuarioDAO = new CardenetaUsuarioDAO();
        cardenetausuarioDAO.cadastrarVacina(cardenetausuario);

        msg = "Cadastrado com Sucesso!";

        response.sendRedirect("paginas_enfermeiro/registrar_vacinasUsuario.jsp?msg="+ msg);
    }
    
    private void consultarHistorico(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String msg;

        CadernetaUsuario cardenetausuario = new CadernetaUsuario();

        //Atribuindo ID do usuario
        cardenetausuario.getUsuario().setUsuario_id(Integer.parseInt(request.getParameter("usuario_id")));
        
        if(request.getParameter("idenf").equals("false")){
            
            TratamentoDeData dataatual = new TratamentoDeData();
            
            cardenetausuario.setCaderneta_datainicio(dataatual.buscarDataRetroativa(3));

            cardenetausuario.setCardeneta_datafinal(dataatual.buscaDataAtual());


        }else{
            
            cardenetausuario.setCaderneta_datainicio(Date.valueOf(request.getParameter("txtDataInicio")));
            cardenetausuario.setCardeneta_datafinal(Date.valueOf(request.getParameter("txtDataFinal")));
        }
        
        CardenetaUsuarioDAO cardenetausuarioDAO = new CardenetaUsuarioDAO();
        
        ArrayList<CadernetaUsuario> listVacinasObrigatorias = cardenetausuarioDAO.consultarVacinasObrigatoriasJaInseridas(cardenetausuario);
        ArrayList<CadernetaUsuario> listVacinasCampanhas = cardenetausuarioDAO.consultarVacinaCampanhaJaInseridas(cardenetausuario);
        ArrayList<CadernetaUsuario> listVacinas = cardenetausuarioDAO.consultarVacinaJaInseridas(cardenetausuario);

        request.setAttribute("listVacinasObrigatorias", listVacinasObrigatorias);
        request.setAttribute("listVacinasCampanhas", listVacinasCampanhas);
        request.setAttribute("listVacinas", listVacinas);
        javax.servlet.RequestDispatcher rd = request.getRequestDispatcher("paginas_paciente/historico_usuario.jsp");
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
