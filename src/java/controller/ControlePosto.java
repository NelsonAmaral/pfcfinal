/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.EnderecoDAO;
import dao.PostoDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Endereco;
import model.Posto;
import util.ValidaFormes;

/**
 *
 * @author nelson_amaral
 */
@WebServlet(name = "ControlePosto", urlPatterns = {"/ControlePosto"})
public class ControlePosto extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = ValidaFormes.formulario(request.getParameter("acao"));
            switch (acao) {
                case "Cadastrar":
                    cadastroPosto(request, response);
                    break;
                case "Listar":
                    listarPosto(request, response);
                    break;
                case "Deletar":
                    delete(request, response);
                    break;
                case "Editar":
                    editar(request, response);
                    break;
                case "Confirma":
                    atualizaPosto(request, response);
                    break;                 
                default:
                    break;
            }

        } catch (Exception erro) {
            System.out.println("Erro (ControlePosto)" + erro);
            RequestDispatcher rd = request.getRequestDispatcher("paginas_erro/erro.jsp");
            rd.forward(request, response);
        }
    }

    private void cadastroPosto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Posto posto = new Posto();
        posto.setPosto_nome(ValidaFormes.formulario(request.getParameter("txtNome")));
        posto.setPosto_telefone(Long.parseLong(ValidaFormes.removeMascara(request.getParameter("txtTelefone"))));
      
        Endereco endereco = new Endereco();
        posto.setPosto_endereco(endereco);
        
        endereco.setLogradouro(request.getParameter("txtLog"));
        endereco.setNumero(Integer.parseInt(request.getParameter("txtNumero")));
        endereco.setCidade(request.getParameter("txtCidade"));
        endereco.setBairro(request.getParameter("txtBairro"));
        endereco.setComplemento(request.getParameter("txtComplemento"));
        endereco.setCep(ValidaFormes.removeMascara(request.getParameter("txtCep")));
        endereco.setUf(request.getParameter("txtUf"));
        
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        //Verificando se o endereco a ser cadastro já existe
        int id_endereco = enderecoDAO.consultarEnderecoExistente(endereco);

        if (id_endereco == 0) {
            endereco.setId_endereco(enderecoDAO.cadastraNovoEndereco(endereco));
        } else {
            endereco.setId_endereco(id_endereco);
        }
        
        PostoDAO postoDAO = new PostoDAO();       
        postoDAO.cadastraPosto(posto);
        
        listarPosto(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Posto posto = new Posto();
        posto.setPosto_id(Integer.parseInt(request.getParameter("id_posto")));
        
        PostoDAO postoDAO = new PostoDAO();
        
        posto = postoDAO.buscaPostosUnico(posto);
              
        posto.setPosto_ativo(!posto.isPosto_ativo());
        
        postoDAO.excluirPosto(posto);
        
        listarPosto(request, response); 
  
    }



    private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Posto posto = new Posto();
        posto.setPosto_id(Integer.parseInt(request.getParameter("id_posto")));
        
        PostoDAO postoDAO = new PostoDAO();
        posto = postoDAO.buscaPostosUnico(posto);
        
        request.setAttribute("posto", posto);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_posto/atualiza_posto.jsp");
        rd.forward(request, response);
    }

    private void atualizaPosto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Posto posto = new Posto();
        posto.setPosto_nome(ValidaFormes.formulario(request.getParameter("txtNome")));
        posto.setPosto_telefone(Long.parseLong(ValidaFormes.removeMascara(request.getParameter("txtTelefone"))));
        posto.setPosto_id(Integer.parseInt(request.getParameter("id_posto")));
        PostoDAO postoDAO = new PostoDAO();
        postoDAO.atualizaPosto(posto);
        
        listarPosto(request, response);       
    }

    private void listarPosto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PostoDAO postoDAO = new PostoDAO();
        ArrayList<Posto> listposto = postoDAO.buscaPostos();

        request.setAttribute("listposto", listposto);
        RequestDispatcher rd = request.getRequestDispatcher("/paginas_posto/exibe_posto.jsp");
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
