/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.FuncionarioDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.PerfilDeAcesso;
import model.Usuario;
import dao.UsuarioDAO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import job.JobIdade;
import job.Jobcampanha;
import model.Funcionario;
import org.quartz.SchedulerException;
import util.ValidaFormes;

/**
 *
 * @author nelson_amaral
 */
@WebServlet(name = "ControleAcesso", urlPatterns = {"/ControleAcesso"})
public class ControleAcesso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String acao = ValidaFormes.formulario(request.getParameter("acao"));
            switch (acao) {
                case "Acesso":
                    acesso(request, response);
                    break;
                case "Voltar":
                    voltar(request, response);
                    break;
                case "Sair":
                    sair(request, response);
                    break;
                case "AcessoDependente":
                    acessoDependente(request, response);
                    break;
                default:
                    break;
            }
        } catch (Exception erro) {
            System.out.println("Erro (ControleAcesso): " + erro);
            RequestDispatcher rd = request.getRequestDispatcher("paginas_erro/erro.jsp");
            rd.forward(request, response);
        }
    }
    private void acesso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        Jobcampanha job = new Jobcampanha();
        JobIdade jobi = new JobIdade();
        try {
            job.startJobCampanha();
            jobi.jobUsuarioIdade();
        } catch (SchedulerException ex) {
            Logger.getLogger(ControleAcesso.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        String login = ValidaFormes.formulario(request.getParameter("txtRg"));
        String senha = ValidaFormes.formulario(request.getParameter("txtSenha"));

        if (login.length() < 7) {

            acessoFuncionario(request, response, login, senha);

        } else if (login.length() <= 12) {

            acessoUsuario(request, response, login, senha);

        } else {

            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            request.setAttribute("msg", "Rg ou Senha Incorreto!");
            rd.forward(request, response);
        }
        
    }
    
    private void acessoFuncionario (HttpServletRequest request, HttpServletResponse response,String cofen, String senha) throws ServletException, IOException {
       
        Funcionario funcionario = new Funcionario();
        funcionario.setCofen(cofen);
        funcionario.setSenha(senha);
       
        UsuarioDAO usuarioDAO =new UsuarioDAO();
        Usuario usuario = new Usuario();
        
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        Funcionario funcionarioAutenticado = funcionarioDAO.autenticaFuncionario(funcionario);
        
        //se o usuario existe no banco de dados.
        if (funcionarioAutenticado != null) {
            
            usuario.setUsuario_id(funcionarioAutenticado.getUsuario_id());
            
            usuario = usuarioDAO.buscaUsuarioUnico(usuario);
            
            funcionarioAutenticado.setNome(usuario.getNome());
            
            //cria uma sessao para o usuario
            HttpSession sessaoUsuario = request.getSession();
            sessaoUsuario.setAttribute("usuarioAutenticado", funcionarioAutenticado);

            PerfilDeAcesso nivelacesso = funcionarioAutenticado.getPerfil();
            switch (nivelacesso) {
                case ENFERMEIRO:
                    //redireciona para a pagina princiapal
                    response.sendRedirect("paginas_adm/principal.jsp");
                    break;
                case ADMINISTRADOR:
                    //redireciona para a pagina princiapal
                    response.sendRedirect("paginas_adm/principal.jsp");
                    break;
                case USUARIO:
                    //redireciona para a pagina princiapal
                    response.sendRedirect("paginas_paciente/principal_paciente.jsp");
                    break;
                default:
                    break;
            }

        } else {
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            request.setAttribute("msg", "Rg ou Senha Incorreto!");
            rd.forward(request, response);
        }
    }
    
    private void acessoUsuario(HttpServletRequest request, HttpServletResponse response,String rg, String senha) throws ServletException, IOException {
      
        String msg = null;
        
        Usuario usuario = new Usuario();
        usuario.setRg(rg);
        usuario.setSenha(senha);
        usuario.setResponsavel(0);
        usuario.setStatus(true);
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario UsuarioAutenticado = usuarioDAO.autenticaUsuario(usuario);
        
        //se o usuario existe no banco de dados.
        if (UsuarioAutenticado.getUsuario_id() != 0) {
            
            //cria uma sessao para o usuario
            HttpSession sessaoUsuario = request.getSession();
            sessaoUsuario.setAttribute("usuarioAutenticado", UsuarioAutenticado);
            
            request.setAttribute("datanascimento", UsuarioAutenticado.getNascimento());
            request.setAttribute("idade", UsuarioAutenticado.getIdade());
            RequestDispatcher rd = request.getRequestDispatcher("paginas_paciente/principal_paciente.jsp");
            rd.forward(request, response);
            
        } else {
            
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            request.setAttribute("msg", "Rg ou Senha Incorreto!");
            rd.forward(request, response);
            
        }
    }
    
    private void voltar(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        HttpSession sessaoUsuario = request.getSession();

        sessaoUsuario.setAttribute("usuarioAutenticado", sessaoUsuario.getAttribute("usuarioAutenticado"));

        //redireciona para a pagina principal
        response.sendRedirect("paginas_adm/principal.jsp");
    }
    
    private void sair(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession sessaoUsuario = request.getSession();
        sessaoUsuario.removeAttribute("usuarioAutenticado");
        response.sendRedirect("index.jsp");
    }
    
    private void acessoDependente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario UsuarioAutenticado = new Usuario();
            
            UsuarioAutenticado.setUsuario_id(Integer.parseInt(request.getParameter("usuario_id")));
            
            UsuarioAutenticado = usuarioDAO.buscaUsuarioUnico(UsuarioAutenticado);
            
            //cria uma sessao para o usuario
            HttpSession sessaoUsuario = request.getSession();
            sessaoUsuario.setAttribute("usuarioAutenticado", UsuarioAutenticado);
            
            //Verifica se o usuario tem dependentes
            int quntDeResponsaveis = usuarioDAO.consultarQuntDependentesDoUsuario(UsuarioAutenticado);
            
            ArrayList<Usuario> listdependentes = new ArrayList<>();
            
            if(quntDeResponsaveis > 0){
                
                listdependentes = usuarioDAO.buscarTodosDependentesDoUsuario(UsuarioAutenticado);
                
            }
            
            int id_responsaveis = UsuarioAutenticado.getResponsavel();
            
            request.setAttribute("listdependentes",  listdependentes);
            request.setAttribute("responsaveis",  id_responsaveis);
            request.setAttribute("datanascimento", UsuarioAutenticado.getNascimento());
            request.setAttribute("idade", UsuarioAutenticado.getIdade());
            RequestDispatcher rd = request.getRequestDispatcher("paginas_paciente/principal_paciente.jsp");
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
