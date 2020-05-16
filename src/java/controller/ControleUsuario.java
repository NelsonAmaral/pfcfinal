/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.FuncionarioDAO;
import dao.RestricaoDAO;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Usuario;
import dao.UsuarioDAO;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;
import model.Restricao;
import util.Email;
import util.TratamentoDeData;
import util.ValidaFormes;
import util.RegradeNegocio;

/**
 *
 * @author nelson_amaral
 */
@WebServlet(name = "ControleUsuario", urlPatterns = {"/ControleUsuario"})
public class ControleUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String acao = ValidaFormes.formulario(request.getParameter("acao"));
            switch (acao) {
                case "Cadastrar": {
                    cadastro(request, response);
                    break;
                }
                case "Consultar Usuarios": {
                    consultarUsuarioPorRG(request, response);
                    break;
                }
                case "Editar": {
                    editar(request, response);
                    break;
                }
                case "Comfirma": {
                    comfirma(request, response);
                    break;
                }
                case "Deletar": {
                    deletar(request, response);
                    break;
                }
                case "ConsultarUsuarioEspecifico": {
                    consultarusuarioespecifico(request, response);
                    break;
                }
                case "PerfilUsuario": {
                    perfilusuario(request, response);
                    break;
                }
                case "DependentesUsuario": {
                    dependentesusuario(request, response);
                    break;
                }
                case "AtualizarDadosUsuario": {
                    atualizarDadosUsuario(request, response);
                    break;
                }
                case "AtualizarSenha": {
                    atualizarSenhaUsuario(request, response);
                    break;
                }
                case "CriarPrimeiroAcessoDependente": {
                    criarPrimeiroAcessoDependente(request, response);
                    break;
                }
                case "CadastraRestricaoUsuario": {
                    cadastraRestricaoUsuario(request, response);
                    break;
                }
                case "DeletarRestricaoUsuario": {
                    deletarRestricaoUsuario(request, response);
                    break;
                }

                default:
                    break;
            }
        } catch (Exception erro) {
            System.out.println("Erro (ControleUsuario)" + erro);
            RequestDispatcher rd = request.getRequestDispatcher("paginas_erro/erro.jsp");
            rd.forward(request, response);
        }
    }

    private void cadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException {

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        RegradeNegocio regras = new RegradeNegocio();

        //variavel auxilar para mensagem ao usuario
        String msg;

        Usuario usuario = new Usuario();
        usuario.setStatus(true);
        
        if ("".equals(request.getParameter("txtIdade"))) {
            usuario.setIdade(0);
        } else {
            usuario.setIdade(Integer.parseInt(ValidaFormes.formulario(request.getParameter("txtIdade"))));
        }

        TratamentoDeData tratamentedata = new TratamentoDeData();
        usuario.setDatacadastro(tratamentedata.buscaDataAtual());
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

                msg = "Não foi possivel cadastrar usuario, "
                        + "responsavel adicionado é menor de idade";

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

        }

        request.setAttribute("msg", msg);
        consultarPacientes(request, response);
    }

    private void consultarPacientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("msg", request.getAttribute("msg"));
        response.sendRedirect("paginas_usuario/exibir_usuario.jsp?msg="+request.getAttribute("msg"));

//        RequestDispatcher rd = request.getRequestDispatcher("paginas_usuario/exibir_usuario.jsp");
//        rd.forward(request, response);
    }

    private void consultarUsuarioPorRG(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario usuario = new Usuario();
        usuario.setRg(request.getParameter("usuario_rg"));

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ArrayList<Usuario> listaUsuario = usuarioDAO.exibirUsuarios(usuario);

        String msg;

        if (listaUsuario.isEmpty()) {
            msg = "Pessoa não cadastrada";
        } else {
            msg = "Encontrada(o)";
        }

        request.setAttribute("msg", msg);
        request.setAttribute("lista", listaUsuario);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_usuario/Exibir_UsuarioPorRgAJAX.jsp");
        rd.forward(request, response);

    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario usuarioModelo = new Usuario();
        usuarioModelo.setUsuario_id(Integer.parseInt(ValidaFormes.formulario(request.getParameter("id"))));

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioModelo = usuarioDAO.buscaUsuarioUnico(usuarioModelo);

        Usuario usuarioResponsavel = usuarioDAO.buscarResponsavelDoUsuarioPorId(usuarioModelo);

        request.setAttribute("usuarioResponsavel", usuarioResponsavel);
        request.setAttribute("user", usuarioModelo);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_usuario/atualizar_usuario.jsp");
        rd.forward(request, response);
    }

    private void comfirma(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String msg;

        RegradeNegocio regras = new RegradeNegocio();

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        Usuario usuario = new Usuario();
        usuario.setUsuario_id(Integer.parseInt(ValidaFormes.formulario(request.getParameter("txtId"))));
        usuario.setNome(ValidaFormes.formulario(request.getParameter("txtNome")));
        usuario.setCpf(ValidaFormes.removeMascara(request.getParameter("txtCpf")));
        usuario.setRg(ValidaFormes.removeMascara(request.getParameter("txtRg")));
        usuario.setSenha(ValidaFormes.formulario(request.getParameter("txtSenha")));
        usuario.setEmail(ValidaFormes.formulario(request.getParameter("txtEmail")));
        usuario.setCelular(Long.parseLong(ValidaFormes.removeMascara(request.getParameter("txtCelular"))));
        usuario.setTelefone(Long.parseLong(ValidaFormes.removeMascara(request.getParameter("txtTelefone"))));
        usuario.setTiposague(ValidaFormes.formulario(request.getParameter("txtTiposague")));
        usuario.setPeso(Float.parseFloat(ValidaFormes.formulario(request.getParameter("txtPeso"))));
        usuario.setAltura(Float.parseFloat(ValidaFormes.formulario(request.getParameter("txtAtura"))));
        usuario.setNascimento(Date.valueOf(ValidaFormes.formulario(request.getParameter("txtDate"))));
        usuario.getEndereco().setLogradouro(ValidaFormes.formulario(request.getParameter("txtLog")));
        usuario.getEndereco().setNumero(Integer.parseInt(request.getParameter("txtNumero")));
        usuario.getEndereco().setCidade(ValidaFormes.formulario(request.getParameter("txtCidade")));
        usuario.getEndereco().setBairro(ValidaFormes.formulario(request.getParameter("txtBairro")));
        usuario.getEndereco().setComplemento(ValidaFormes.formulario(request.getParameter("txtComplemento")));
        usuario.getEndereco().setCep(request.getParameter("txtCep"));
        usuario.getEndereco().setUf(ValidaFormes.formulario(request.getParameter("txtUf")));

        usuario.setRg_responsavel(ValidaFormes.removeMascara(request.getParameter("txtRgResponsavel")));
        //Validando se o campo responsavel foi preenchido e se o responsavel é menor de 18 anos
        int retorno = regras.consultaResponsavelMenorDe18(usuario);
        if (retorno == 0) {

            msg = "Não foi possivel atualizar usuario, "
                    + "responsavel adicionado é menor de idade ou invalido";

            request.setAttribute("msg", msg);
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        }

        //Verificando se o endereco a ser cadastro já existe
        regras.consultandoEnderecoJaExistente(usuario);

        //Verifica se o campo senha está preenchido para alterar a senha
        if (!"".equals(usuario.getSenha())) {

            usuarioDAO.atualizarSenhaUsuario(usuario);

        }

        Email email = new Email();
        if (usuario.getUsuario_id() != 0 && !"".equals(usuario.getEmail()) && usuario.getResponsavel() != 0) {

            //Enviando e-mail de confirmação 
            email.emailCadastroDeDependente(usuario);

        }

        usuarioDAO.atualizar_usuario(usuario);

        msg = "atualizado com sucesso";

        request.setAttribute("msg", msg);
        consultarPacientes(request, response);
    }

    private void deletar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario usuario = new Usuario();
        usuario.setUsuario_id(Integer.parseInt(ValidaFormes.formulario(request.getParameter("id"))));
       
        usuario.setStatus(!Boolean.parseBoolean(request.getParameter("status")));
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.excluirUsuario(usuario);

        consultarPacientes(request, response);
    }

    private void consultarusuarioespecifico(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String msg = null;

        String rg = ValidaFormes.removeMascara(request.getParameter("usuario_rg"));

        Usuario usuario = new Usuario();
        usuario.setRg(rg);
        usuario.setStatus(true);

        //REGRA DE NEGOCIO
        usuario.setIdade(18);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuario = usuarioDAO.exibirUsuarioEspecifico(usuario);

        FuncionarioDAO funcDAO = new FuncionarioDAO();
        ArrayList<Integer> listaFuncionariosAtivos = funcDAO.buscaTodosFuncionariosAtivos();

        if (usuario.getUsuario_id() == 0) {

            msg = "Usuario não encontrado ou menor de idade!";

        } else {

            //Verifica se já é um funcionario
            for (int id_funcionarios : listaFuncionariosAtivos) {

                if (id_funcionarios == usuario.getUsuario_id()) {

                    msg = "Está pessoa já é um funcionario!";

                }

            }
        }

        request.setAttribute("msg", msg);
        request.setAttribute("usuario_pesquisa", usuario);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_adm/busca_usuario_Especifico_cadastrarFuncAJAX.jsp");
        rd.forward(request, response);
    }

    private void perfilusuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String msg = "";

        Usuario usuarioModelo = new Usuario();
        usuarioModelo.setUsuario_id(Integer.parseInt(ValidaFormes.formulario(request.getParameter("usuario_id"))));

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioModelo = usuarioDAO.buscaUsuarioUnico(usuarioModelo);

        //Verifica se o usuario tem dependentes
        int quntDeResponsaveis = usuarioDAO.consultarQuntDependentesDoUsuario(usuarioModelo);

        ArrayList<Usuario> listdependentes = new ArrayList<>();

        if (quntDeResponsaveis > 0) {

            listdependentes = usuarioDAO.buscarTodosDependentesDoUsuario(usuarioModelo);

        }

        if (request.getAttribute("msg") == null) {
            msg = (String) request.getAttribute("msg");
        }

        //Buscando Restricao do Usuario
        Restricao restricao = new Restricao();
        usuarioModelo.setRestricao(restricao);

        RestricaoDAO restricaoDAO = new RestricaoDAO();

        //Consultando restrições do usuario
        ArrayList<Restricao> listrestricaousuario = restricaoDAO.consultarRestricoesUsuario(usuarioModelo);

        //Consultando todas as restrições 
        ArrayList<Restricao> listrestricoes = restricaoDAO.consultarRestricao();

        request.setAttribute("restricoes", listrestricoes);
        request.setAttribute("restricaousuario", listrestricaousuario);
        request.setAttribute("msg", msg);
        request.setAttribute("listdependentes", listdependentes);
        request.setAttribute("usuario", usuarioModelo);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_paciente/perfil_usuario.jsp");
        rd.forward(request, response);
    }

    private void dependentesusuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario usuarioModelo = new Usuario();
        usuarioModelo.setUsuario_id(Integer.parseInt(ValidaFormes.formulario(request.getParameter("usuario_id"))));

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioModelo = usuarioDAO.buscaUsuarioUnico(usuarioModelo);

        //Verifica se o usuario tem dependentes
        int quntDeResponsaveis = usuarioDAO.consultarQuntDependentesDoUsuario(usuarioModelo);

        ArrayList<Usuario> listdependentes = new ArrayList<>();

        if (quntDeResponsaveis > 0) {

            listdependentes = usuarioDAO.buscarTodosDependentesDoUsuario(usuarioModelo);

        }

        request.setAttribute("listdependentes", listdependentes);
        request.setAttribute("responsaveis", quntDeResponsaveis);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_paciente/dependentes_usuario.jsp");
        rd.forward(request, response);
    }

    private void atualizarDadosUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String tipo_update = request.getParameter("update");
        
        Usuario usuario = new Usuario();
        usuario.setUsuario_id(Integer.parseInt(request.getParameter("id_usuario")));

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        String msg;

        switch (tipo_update) {
            case "fisico": {

                usuario.setPeso(Float.parseFloat(request.getParameter("txtPeso")));
                usuario.setAltura(Float.parseFloat(request.getParameter("txtAltura")));

                usuarioDAO.atualizarFisicoUsuario(usuario);

                break;
            }
            case "contato": {

                usuario.setCelular(Long.parseLong(ValidaFormes.removeMascara(request.getParameter("txtCelular"))));
                usuario.setTelefone(Long.parseLong(ValidaFormes.removeMascara(request.getParameter("txtTelefone"))));
                usuario.setEmail(ValidaFormes.formulario(request.getParameter("txtEmail")));

                usuarioDAO.atualizarContatosUsuario(usuario);
                break;
            }
            default:
                break;
        }

        msg = "Atualizado com sucesso!";

        request.setAttribute("usuario_id", usuario.getUsuario_id());
        request.setAttribute("acao", "AcessoDependente");
        request.setAttribute("msg", msg);
        RequestDispatcher rd = request.getRequestDispatcher("/ControleUsuario?acao=PerfilUsuario&msg=Atualizado&usuario_id=" + usuario.getUsuario_id());
        rd.forward(request, response);
    }

    private void atualizarSenhaUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario usuario = new Usuario();
        usuario.setUsuario_id(Integer.parseInt(request.getParameter("txtId")));
        usuario.setRg(request.getParameter("txtRG"));
        usuario.setSenha(request.getParameter("txtsenhaatual"));

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        String msg;
        Usuario usuarioautenticado = usuarioDAO.autenticaUsuario(usuario);

        if (usuarioautenticado.getUsuario_id() != 0) {

            usuario.setSenha(request.getParameter("txtnovasenha"));

            usuarioDAO.atualizarSenhaUsuario(usuario);

            msg = "Senha atualizada";

        } else {

            msg = "Não foi possivel atualizar a Senha";

        }

        request.setAttribute("usuario_id", usuario.getUsuario_id());
        request.setAttribute("acao", "AcessoDependente");
        request.setAttribute("msg", "Atualizado");
        RequestDispatcher rd = request.getRequestDispatcher("/ControleUsuario?acao=PerfilUsuario&msg=Atualizado&usuario_id=" + usuario.getUsuario_id());
        rd.forward(request, response);
    }

    private void criarPrimeiroAcessoDependente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String msg;

        Usuario usuario = new Usuario();
        usuario.setUsuario_id(Integer.parseInt(request.getParameter("txtId")));
        usuario.setNome(request.getParameter("txtNomeUsuario"));
        usuario.setCpf(request.getParameter("txtCPF"));
        usuario.setEmail(request.getParameter("txtEmailnovamente"));
        usuario.setSenha(request.getParameter("txtnovasenha"));

        //Consultando se RG ou CPF ou EMAIL já existente
        RegradeNegocio validaInf = new RegradeNegocio();
        msg = validaInf.consultando_RG_CPF_EMAIL_existente(usuario);

        if ("".equals(msg)) {

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            
            //Buscando informação todas informaçõe do dependente antes do UPDATE
            Usuario usuarioInf = usuarioDAO.buscaUsuarioUnico(usuario);
            
            //Realizando o UPDATE
            int usuario_id = usuarioDAO.criarPrimeiroAcessoDependente(usuario);

            if (usuario_id != 0) {

                Email email = new Email();
                email.emailPrimerioCadastro(usuario);
                
                //Enviado e-mail para antigo responsavel informando 
                usuario.setEmail(usuarioInf.getEmail());
                email.emailDesligamentoDeDependente(usuario);
                
                msg = "Login ativado com sucesso!";

            } else {

                msg = "Não foi possivel criar o primeiro acesso (Entre em contato com o posto mais proximo)";

            }

        } else {

            msg = "Atenção " + msg + " repita a operação com informações diferentes!";

        }

        //Destruindo sessao do usuario
        HttpSession sessaoUsuario = request.getSession();
        sessaoUsuario.removeAttribute("usuarioAutenticado");

        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        request.setAttribute("msg", msg);
        rd.forward(request, response);
    }

    private void cadastraRestricaoUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario usuario = new Usuario();
        usuario.setUsuario_id(Integer.parseInt(request.getParameter("usuario_id")));

        Restricao restricao = new Restricao();
        usuario.setRestricao(restricao);

        RestricaoDAO restricaoDAO = new RestricaoDAO();

        String[] restricaofk = request.getParameterValues("txtRestricaoFK");
        if (restricaofk != null) {
            for (String r : restricaofk) {

                usuario.getRestricao().setRestricao_id(Integer.parseInt(r));

                //Verifica se o usuario já tem a restricão cadastrada
                Integer resultado = restricaoDAO.verificarRestricaoUsuario(usuario);

                //Caso já tenha será apenas alterado o status para ativoS
                if (resultado != 0) {
                    restricaoDAO.ativarRestricaoUsuario(usuario);
                } else {
                    restricaoDAO.cadastrarNovaRestricaoUsuario(usuario);
                }
            }
        }

        request.setAttribute("usuario_id", usuario.getUsuario_id());
        request.setAttribute("acao", "AcessoDependente");
        request.setAttribute("msg", "Atualizado");
        RequestDispatcher rd = request.getRequestDispatcher("/ControleUsuario?acao=PerfilUsuario&msg=Atualizado&usuario_id=" + usuario.getUsuario_id());
        rd.forward(request, response);
    }

    private void deletarRestricaoUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario usuario = new Usuario();
        usuario.setUsuario_id(Integer.parseInt(request.getParameter("usuario_id")));

        Restricao restricao = new Restricao();
        usuario.setRestricao(restricao);
        usuario.getRestricao().setRestricao_id(Integer.parseInt(request.getParameter("id_restricao")));

        RestricaoDAO restricaoDAO = new RestricaoDAO();

        restricaoDAO.atualizarRestricaoUsuario(usuario);

        request.setAttribute("usuario_id", usuario.getUsuario_id());
        request.setAttribute("acao", "AcessoDependente");
        request.setAttribute("msg", "Deletado");
        RequestDispatcher rd = request.getRequestDispatcher("/ControleUsuario?acao=PerfilUsuario&msg=Atualizado&usuario_id=" + usuario.getUsuario_id());
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
