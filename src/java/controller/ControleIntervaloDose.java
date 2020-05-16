/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.IntervaloVacinacaoDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CalendarioObrigatorio;
import model.IntervaloVacinacao;
import util.ValidaFormes;

/**
 *
 * @author Victor_Aguiar
 */
@WebServlet(name = "ControleIntervaloDose", urlPatterns = {"/ControleIntervaloDose"})
public class ControleIntervaloDose extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
            try {
            String acao = ValidaFormes.formulario(request.getParameter("acao"));

            switch (acao) {
                case "listarintervalos": {
                    listarintervalos_unicosCalendarioOB(request, response);
                    break;
                }
                default:
                    break;
            }
        } catch (Exception erro) {
            System.out.println("Erro (ControleIntervaloDose)" + erro);
            RequestDispatcher rd = request.getRequestDispatcher("paginas_usuario/erro.jsp");
            rd.forward(request, response);
        }
        
    }

    public void listarintervalos_unicosCalendarioOB(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
              
        CalendarioObrigatorio calendarioOB = new CalendarioObrigatorio();   
        calendarioOB.setId_calendarioObr(Integer.parseInt(ValidaFormes.formulario(request.getParameter("calendarioob_id"))));
        
        IntervaloVacinacao intervalovacinacao = new IntervaloVacinacao();
        intervalovacinacao.setCalendarioObr(calendarioOB);
        intervalovacinacao.setAtivoOuNao(true); 
        
        IntervaloVacinacaoDAO intervalovacinacaoDAO = new IntervaloVacinacaoDAO();

        ArrayList<IntervaloVacinacao> listaintervalos = intervalovacinacaoDAO.buscaUnicaIntervaloVacinacao(intervalovacinacao);
        
        ArrayList<IntervaloVacinacao> listaExibicaoWeb = new ArrayList<>();
        
        for (IntervaloVacinacao objintervalo : listaintervalos) {
        
            double ano = objintervalo.getDias() / 365;
            
            double resto = objintervalo.getDias() % 365;
            double mes = resto / 30;
            
            String AnosdeVida = (int) ano+" anos e "+ (int) mes+ " mêses";
            
            objintervalo.setanosdeVida(AnosdeVida);
            
            listaExibicaoWeb.add(objintervalo);
        
        }
        
        request.setAttribute("listaintervalos", listaExibicaoWeb);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_calendarioobr/tabela_listar_intervalos_calendarioobAJAX.jsp");
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
