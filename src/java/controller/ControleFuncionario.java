/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.FuncionarioDAO;
import dao.PostoDAO;
import dao.UsuarioDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Funcionario;
import model.PerfilDeAcesso;
import model.Posto;
import model.Usuario;
import util.ValidaFormes;

/**
 *
 * @author Victor_Aguiar
 */
@WebServlet(name = "ControleFuncionario", urlPatterns = {"/ControleFuncionario"})
public class ControleFuncionario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String acao = ValidaFormes.formulario(request.getParameter("acao"));
            switch (acao) {
                case "Cadastrar": {
                    cadastro(request, response);
                    break;
                }
                case "Exibir": {
                    exibir(request, response);
                    break;
                }
                case "Editar": {
                    editar(request, response);
                    break;
                }
                case "Confirmar": {
                    comfirma(request, response);
                    break;
                }
                case "Deletar": {
                    excluir(request, response);
                    break;
                }
                case "Atualizar": {
                    atualizar(request, response);
                    break;
                }
                case "AtivarLoginFuncionario": {
                    ativarLoginFuncionario(request, response);
                    break;
                }
                default:
                    break;
            }
        } catch (Exception erro) {
            System.out.println("Erro (ControleFuncionario): " + erro);
            RequestDispatcher rd = request.getRequestDispatcher("/erro.jsp");
            rd.forward(request, response);
        }

    }

    private void cadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        ArrayList<Funcionario> listaFuncionario = funcionarioDAO.exibeFuncionarios();
       
        PostoDAO postoDAO = new PostoDAO();
        ArrayList<Posto> listPostos = postoDAO.buscaPostosAtivos();
        
        request.setAttribute("listaPostos", listPostos);
        request.setAttribute("listaFuncionario", listaFuncionario);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_adm/cadastroFuncionario.jsp");
        rd.forward(request, response);

    }

    private void comfirma(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Funcionario funcionario = new Funcionario();
        funcionario.setCofen(ValidaFormes.formulario(request.getParameter("txtConfen")));
        funcionario.setSenha(ValidaFormes.formulario(request.getParameter("txtSenha")));
        funcionario.setUsuario_id(Integer.parseInt(request.getParameter("txtUsuarioFK")));
        funcionario.getPosto().setPosto_id(Integer.parseInt(request.getParameter("txtfkposto")));
        funcionario.setStatus(true);

        String perfil = ValidaFormes.formulario(request.getParameter("txtPerfil"));
        switch (perfil) {
            case "ADMINISTRADOR":
                funcionario.setPerfil(PerfilDeAcesso.ADMINISTRADOR);
                break;
            case "ENFERMEIRO":
                funcionario.setPerfil(PerfilDeAcesso.ENFERMEIRO);
                break;
            default:
                break;
        }

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        
        //Consulta se o usuario 
        int id_funcionario = funcionarioDAO.consultaFuncionarioPorUsuarioUnico(funcionario);
        
        String msg = null;
        
        if(id_funcionario != 0){
            
          msg = "Funcionario já cadastrado! *(Verifique se o funcionario está desativado)*";
            
        }else{
            
          funcionarioDAO.cadastrarFuncionario(funcionario);  
            
        }      

        request.setAttribute("msg", msg);
        exibir(request, response);
    }

    private void atualizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Funcionario funcionario = new Funcionario();
        funcionario.setId_funcionario(Integer.parseInt(ValidaFormes.formulario(request.getParameter("id_fun_atual"))));
        funcionario.setCofen(ValidaFormes.formulario(request.getParameter("txtConfen")));
        funcionario.setSenha(ValidaFormes.formulario(request.getParameter("txtSenha")));
        funcionario.setUsuario_id(Integer.parseInt(ValidaFormes.formulario(request.getParameter("txtUsuarioFK"))));
        funcionario.getPosto().setPosto_id(Integer.parseInt(request.getParameter("txtPostoId")));
        funcionario.setStatus(true);

        String perfil = ValidaFormes.formulario(request.getParameter("txtPerfil"));
        switch (perfil) {
            case "ADMINISTRADOR":
                funcionario.setPerfil(PerfilDeAcesso.ADMINISTRADOR);
                break;
            case "ENFERMEIRO":
                funcionario.setPerfil(PerfilDeAcesso.ENFERMEIRO);
                break;
            default:
                break;
        }

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        if (!"".equals(funcionario.getSenha())){
            funcionarioDAO.atualizaSenhaFuncionario(funcionario);
        }
        
        funcionarioDAO.atualizaFuncionario(funcionario);

        String msg = "Atualizado com sucesso!";
        
        request.setAttribute("msg", msg);
        exibir(request, response);
    }

    private void exibir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        ArrayList<Funcionario> listaFuncionario = funcionarioDAO.exibeFuncionarios();

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        for (Funcionario func : listaFuncionario) {

            Usuario usuario = new Usuario();
            usuario.setUsuario_id(func.getUsuario_id());

            usuario = usuarioDAO.buscaUsuarioUnico(usuario);

            func.setNome(usuario.getNome());
        }
        
        ArrayList<Funcionario> listaFuncionarioDesativados = funcionarioDAO.consultaFuncionariosDesativados();
        
        request.setAttribute("msg", request.getAttribute("msg"));
        request.setAttribute("listaFuncionarioDesativados", listaFuncionarioDesativados);
        request.setAttribute("lista", listaFuncionario);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_adm/exibirFuncionario.jsp");
        rd.forward(request, response);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Funcionario funcionario = new Funcionario();
        funcionario.setId_funcionario(Integer.parseInt(ValidaFormes.formulario(request.getParameter("id_funcionario"))));

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        funcionario = funcionarioDAO.buscaFuncionario(funcionario.getId_funcionario());

        Usuario usuario = new Usuario();
        usuario.setUsuario_id(funcionarioDAO.buscaUsuarioIDFuncionario(funcionario.getId_funcionario()));

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuario = usuarioDAO.buscaUsuarioUnico(usuario);

        PostoDAO postoDAO = new PostoDAO();
        ArrayList<Posto> listpostos = postoDAO.buscaPostosAtivos();
        
        request.setAttribute("funcionario", funcionario);
        request.setAttribute("usuario", usuario);
        request.setAttribute("listaPostos", listpostos);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_adm/atualizaFuncionario.jsp");
        rd.forward(request, response);

    }

    private void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Funcionario funcionario = new Funcionario();
        funcionario.setId_funcionario(Integer.parseInt((ValidaFormes.formulario(request.getParameter("id_funcionario")))));
        funcionario.setStatus(false);

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        
        int QuantidadeLoginsAtivos = funcionarioDAO.consultaQntDeLoginsFuncionariosAtivosNoSistema();
        
         String msg;
        
        if(QuantidadeLoginsAtivos < 2){
            
           msg = "Não foi possivel excluir, o sistema necessita no minimo de um login ativo!";
            
        }else{
         
          funcionarioDAO.alterarStatusLoginFuncionario(funcionario);
          
          msg = "Funcionario desativado com sucesso!";
        }
       

        request.setAttribute("msg", msg);
        exibir(request, response);
    }
    
    private void ativarLoginFuncionario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Funcionario funcionario = new Funcionario();
        funcionario.setId_funcionario(Integer.parseInt((ValidaFormes.formulario(request.getParameter("id_funcionario")))));
        funcionario.setStatus(true);

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();          
        funcionarioDAO.alterarStatusLoginFuncionario(funcionario);
                 
        String msg = "Funcionario Ativado com sucesso!";

        request.setAttribute("msg", msg);
        exibir(request, response);
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
