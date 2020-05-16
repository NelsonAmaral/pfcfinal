/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.EnderecoDAO;
import dao.UsuarioDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Endereco;
import model.Usuario;
import util.ValidaFormes;

/**
 *
 * @author nelson_amaral
 */
@WebServlet(name = "ControleEndereco", urlPatterns = {"/ControleEndereco"})
public class ControleEndereco extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String acao = ValidaFormes.formulario(request.getParameter("acao"));
            switch (acao) {
                case "Atualizar":
                    atualizar(request, response);
                    break;
                case "Editar Endereco":
                    buscaEnderecoUsuario(request, response);
                    break;
                case "Sair":
                    //   sair(request, response);
                    break;
                default:
                    break;
            }
        } catch (Exception erro) {
            System.out.println("Erro (ControleEndereco): " + erro);
            RequestDispatcher rd = request.getRequestDispatcher("paginas_erro/erro.jsp");
            rd.forward(request, response);
        }
    }

    private int cadastra(Endereco endereco) {

        EnderecoDAO enderecoDAO = new EnderecoDAO();
        enderecoDAO.consultarEnderecoExistente(endereco);

        //Verificando se o endereco a ser cadastro já existe
        int id_endereco = enderecoDAO.consultarEnderecoExistente(endereco);

        if (id_endereco == 0) {
            endereco.setId_endereco(enderecoDAO.cadastraNovoEndereco(endereco));
        } else {
            endereco.setId_endereco(id_endereco);
        }

        return endereco.getId_endereco();
    }

    private void buscaEnderecoUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Endereco endereco = new Endereco();
        Usuario usuario = new Usuario();

        usuario.setUsuario_id(Integer.parseInt(ValidaFormes.formulario(request.getParameter("usuario_id"))));

        endereco.setId_endereco(usuarioDAO.enderecoUsuario(usuario));

        EnderecoDAO enderecoDAO = new EnderecoDAO();
        endereco = enderecoDAO.consultarId(endereco.getId_endereco());

        request.setAttribute("teste", endereco);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_paciente/perfil_usuario.jsp");
        rd.forward(request, response);

    }

    private void atualizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario usuario = new Usuario();
        usuario.setUsuario_id(Integer.parseInt(ValidaFormes.formulario(request.getParameter("id_usuario"))));

        usuario.getEndereco().setLogradouro(ValidaFormes.formulario(request.getParameter("txtLog")));
        usuario.getEndereco().setNumero(Integer.parseInt(request.getParameter("txtNumero")));
        usuario.getEndereco().setCidade(ValidaFormes.formulario(request.getParameter("txtCidade")));
        usuario.getEndereco().setBairro(ValidaFormes.formulario(request.getParameter("txtBairro")));
        usuario.getEndereco().setComplemento(ValidaFormes.formulario(request.getParameter("txtComplemento")));
        usuario.getEndereco().setCep(request.getParameter("txtCep"));
        usuario.getEndereco().setUf(ValidaFormes.formulario(request.getParameter("txtUf")));
        
        //Verificando se o endereco a ser cadastro já existe
        EnderecoDAO enderecoDAO = new EnderecoDAO();
        int id_endereco = enderecoDAO.consultarEnderecoExistente(usuario.getEndereco());

        if (id_endereco == 0) {
            usuario.getEndereco().setId_endereco(enderecoDAO.cadastraNovoEndereco(usuario.getEndereco()));
        } else {
            usuario.getEndereco().setId_endereco(id_endereco);
        }
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.atualizarEndereco(usuario);

        request.setAttribute("usuario_id", usuario.getUsuario_id());
        request.setAttribute("acao", "AcessoDependente");
        request.setAttribute("msg", "Atualizado");
        RequestDispatcher rd = request.getRequestDispatcher("/ControleUsuario?acao=PerfilUsuario&msg=Atualizado&usuario_id="+usuario.getUsuario_id());
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
