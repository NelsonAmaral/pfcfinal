/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.EnderecoDAO;
import dao.UsuarioDAO;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Endereco;
import model.Usuario;
import util.Email;
import util.TratamentoDeData;
import util.ValidaFormes;
import util.RegradeNegocio;

/**
 *
 * @author nelson_amaral
 */
@WebServlet(name = "ControleUsuarioNaoLogado", urlPatterns = {"/ControleUsuarioNaoLogado"})
public class ControleUsuarioNaoLogado extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String acao = ValidaFormes.formulario(request.getParameter("acao"));
            switch (acao) {
                case "Cadastrar": {
                    cadastro(request, response);
                    break;
                }

            }
        } catch (Exception erro) {
            System.out.println("Erro (ControleUsuarioNaoLogado)" + erro);
            RequestDispatcher rd = request.getRequestDispatcher("paginas_usuario/erro.jsp");
            rd.forward(request, response);
        }
    }

    private void cadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException {

        RegradeNegocio regras = new RegradeNegocio();
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        //variavel auxilar para mensagem ao usuario
        String msg;

        Usuario usuario = new Usuario();
        usuario.setStatus(true);
                      
        TratamentoDeData tratamentedata = new TratamentoDeData();
        usuario.setDatacadastro(tratamentedata.buscaDataAtual());
             
        if ("".equals(request.getParameter("txtIdade"))) {
            usuario.setIdade(0);
        } else {
            usuario.setIdade(Integer.parseInt(ValidaFormes.formulario(request.getParameter("txtIdade"))));
        }
        
        usuario.setNome(ValidaFormes.formulario(request.getParameter("txtNome")));
        usuario.setRg(ValidaFormes.removeMascara(request.getParameter("txtRg")));
        usuario.setCpf(ValidaFormes.removeMascara(request.getParameter("txtCpf")));
        usuario.setSenha(ValidaFormes.formulario(request.getParameter("txtSenha")));
        usuario.setEmail(ValidaFormes.formulario(request.getParameter("txtEmail")));
        usuario.setTelefone(Long.parseLong(ValidaFormes.removeMascara(request.getParameter("txtTelefone"))));
        usuario.setCelular(Long.parseLong(ValidaFormes.removeMascara(request.getParameter("txtCelular"))));
        usuario.setTiposague(ValidaFormes.formulario(request.getParameter("txtTiposague")));
        usuario.setPeso(Float.parseFloat(ValidaFormes.formulario(request.getParameter("txtPeso"))));
        usuario.setAltura(Float.parseFloat(ValidaFormes.formulario(request.getParameter("txtAtura"))));
        usuario.setNascimento(Date.valueOf((request.getParameter("txtDate"))));
        usuario.getEndereco().setLogradouro(ValidaFormes.formulario(request.getParameter("txtLog")));
        usuario.getEndereco().setNumero(Integer.parseInt(request.getParameter("txtNumero")));
        usuario.getEndereco().setCidade(ValidaFormes.formulario(request.getParameter("txtCidade")));
        usuario.getEndereco().setBairro(ValidaFormes.formulario(request.getParameter("txtBairro")));
        usuario.getEndereco().setComplemento(ValidaFormes.formulario(request.getParameter("txtComplemento")));
        usuario.getEndereco().setCep(request.getParameter("txtCep"));
        usuario.getEndereco().setUf(ValidaFormes.formulario(request.getParameter("txtUf")));

        //Consultando se RG ou CPF ou EMAIL já existente
        msg = regras.consultando_RG_CPF_EMAIL_existente(usuario);

        if ("".equals(msg)) {
            
            usuario.setRg_responsavel(ValidaFormes.removeMascara(request.getParameter("txtRgResponsavel")));
            
            //Validando se o campo responsavel foi preenchido e se o responsavel é menor de 18 anos
            int retorno = regras.consultaResponsavelMenorDe18(usuario);
            if (retorno == 0) {

                msg = "Não foi possivel cadastrar usuario, responsavel adicionado é menor de idade";

                request.setAttribute("msg", msg);
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
            }

            //Verificando se o endereco a ser cadastro já existe
            regras.consultandoEnderecoJaExistente(usuario);

            usuario.setUsuario_id(usuarioDAO.cadastaNovoUsuario(usuario));

            Email email = new Email();

            if (usuario.getUsuario_id() != 0 && !"".equals(usuario.getEmail()) && usuario.getResponsavel() == 0) {

                //Enviando e-mail de confirmação               
                email.emailPrimerioCadastro(usuario);

            } else if (usuario.getUsuario_id() != 0 && !"".equals(usuario.getEmail()) && usuario.getResponsavel() != 0) {

                //Enviando e-mail de confirmação 
                email.emailCadastroDeDependente(usuario);

            }

            msg = "Cadastrado com Sucesso";

        }

        request.setAttribute("msg", msg);
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
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
