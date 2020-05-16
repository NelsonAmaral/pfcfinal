/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CalendarioObrigatorioDAO;
import dao.CardenetaUsuarioDAO;
import dao.IntervaloVacinacaoDAO;
import dao.UsuarioDAO;
import dao.VacinaDAO;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CadernetaUsuario;
import model.CalendarioObrigatorio;
import model.Funcionario;
import model.IntervaloVacinacao;
import model.Usuario;
import model.Vacina;
import util.CalendarioObrRegraNegocio;
import util.MesAnosEmDiasCadastroCalendarioObrigatorio;
import util.TratamentoDeData;
import util.ValidaFormes;

/**
 *
 * @author nelson_amaral
 */
@WebServlet(name = "ControlerCalendarioObr", urlPatterns = {"/ControlerCalendarioObr"})
public class ControlerCalendarioObr extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String acao = ValidaFormes.formulario(request.getParameter("acao"));

            switch (acao) {
                case "Cadastrar": {
                    cadastroVacinaNoCalendarioObr(request, response);
                    break;
                }
                case "Consultar": {
                    exibeVacinaNoCalendarioObr(request, response);
                    break;
                }
                case "Editar": {
                    editarVacinaCalendario(request, response);
                    break;
                }
                case "Confirmar": {
                    cofirmarCadastro(request, response);
                    break;
                }
                case "Deletar": {
                    excluirVacinaCalendario(request, response);
                    break;
                }
                case "CofirmarAtualizacao": {
                    atualizaVacinaCalendario(request, response);
                    break;
                }
                case "listarmesescalendario": {
                    listarmesescalendario(request, response);
                    break;
                }
                case "ConsultarVacinasObMesAtual": {
                    exibeVacinasObdoMes(request, response);
                    break;
                }
                default:
                    break;
            }
        } catch (Exception erro) {
            System.out.println("Erro (ControlerCalendarioObr)" + erro);
            RequestDispatcher rd = request.getRequestDispatcher("paginas_usuario/erro.jsp");
            rd.forward(request, response);
        }
    }

    //exibe todas as vacinas não cadastradas no calendario
    private void cadastroVacinaNoCalendarioObr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        VacinaDAO vacinaDAO = new VacinaDAO();
        ArrayList<Vacina> listaVacina = vacinaDAO.ConsultarVacinasCalendario();

        request.setAttribute("listaVacinas", listaVacina);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_calendarioobr/cadastro_vacinas_calendarioobr.jsp");
        rd.forward(request, response);
    }

    //exibe todas as vacinas cadastradas no calendario
    private void exibeVacinaNoCalendarioObr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Vacina vacina = new Vacina();
        vacina.setStatus(true);
        vacina.setStatusCalendario(true);

        VacinaDAO vacinaDAO = new VacinaDAO();
        ArrayList<Vacina> listavacinas = vacinaDAO.ConsultarVacinasCadastradasCalendarioOB(vacina);

        CalendarioObrigatorioDAO calendarioDAO = new CalendarioObrigatorioDAO();

        ArrayList<CalendarioObrigatorio> listaVsCalendario = new ArrayList<>();

        for (Vacina objvacina : listavacinas) {

            CalendarioObrigatorio calendarioob = new CalendarioObrigatorio();
            calendarioob.setVacina(objvacina);

            calendarioDAO.buscaVacinaCalendarioObr(calendarioob);

            listaVsCalendario.add(calendarioob);
        }

        request.setAttribute("listaVacinas", listaVsCalendario);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_calendarioobr/exibe_vacinas_calendarioobr.jsp");
        rd.forward(request, response);

    }

    //cadastrar os dados da nova vacina do calendario Obr no Banco
    public void cofirmarCadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Funcionario funcionario = new Funcionario();
        funcionario.setId_funcionario(Integer.parseInt(ValidaFormes.formulario(request.getParameter("idfuncionario"))));

        Vacina vacina = new Vacina();
        vacina.setId_vacina(Integer.parseInt(ValidaFormes.formulario(request.getParameter("idVacina"))));

        CalendarioObrigatorio calendario = new CalendarioObrigatorio();
        TratamentoDeData cvDate = new TratamentoDeData();
        calendario.setDateCadastro(cvDate.buscaDataAtual());
        calendario.setHoraCadastro(cvDate.buscaHoraAtual());
        calendario.setStatus(true);
        calendario.setVacina(vacina);
        calendario.setFuncionario(funcionario);
        calendario.setComentario("");
        calendario.setDose(Integer.parseInt(ValidaFormes.formulario(request.getParameter("txtDose"))));

        CalendarioObrigatorioDAO calendarioDAO = new CalendarioObrigatorioDAO();
        calendario.setId_calendarioObr(calendarioDAO.cadastraVacinaCalendarioObr(calendario));

        VacinaDAO vacinaDAO = new VacinaDAO();

        //alterando status da vacina para (CALENeDARIO_OBRIGATORIO CRIADO PARA A VACINA A RESPEITO)
        if (calendario.getId_calendarioObr() == 0) {
            response.sendRedirect("paginas_erro/desenvolvimento.jsp");
        } else {
            //Setando stats que desejamos que sejá alterado 
            vacina.setStatus(true);

            //chamando a classe dao para alterar o campo 
            vacinaDAO.atualizar_campo_vacinaCalendarioob(vacina);
        }

        MesAnosEmDiasCadastroCalendarioObrigatorio anosMesParaDias = new MesAnosEmDiasCadastroCalendarioObrigatorio();

        int txtIdadeouMes = Integer.parseInt(request.getParameter("txtIdadeouMes"));
        
        int tipo = Integer.parseInt(request.getParameter("txtTipo"));
        
        int DoseInicialdiasDeVida;
        
        //Verificando se o tipo selecionado foi ANOS DE VIDA ou MESES DE VIDA
        if(tipo ==  0){
            
            DoseInicialdiasDeVida = anosMesParaDias.anoParaDias(txtIdadeouMes);
            
        }else{
            
            DoseInicialdiasDeVida = anosMesParaDias.mesParaDias(txtIdadeouMes);
            
        }

        int intervalo = anosMesParaDias.meseanosParaDias(request.getParameter("txtIntervalo"));
        
        IntervaloVacinacao intervaloVacinacao = new IntervaloVacinacao();
        intervaloVacinacao.setCalendarioObr(calendario);
        intervaloVacinacao.setDose(Integer.parseInt(request.getParameter("txtDose")));
        intervaloVacinacao.setAtivoOuNao(true);

        String[] intervalosPersonalizados = request.getParameterValues("objintervaloarray");

        CalendarioObrRegraNegocio regraNegocio = new CalendarioObrRegraNegocio();

        if (intervalo == 0) {

            //Instanciando um arrayList 
            List<String> list = Arrays.asList(intervalosPersonalizados);

            //Criando variavel String[] para receber os dados extraidos do array de string do formulario
            String[] arraydein = null;

            //For para listar e adicionar a variavel criada a cima separado por virguça
            for (String valor : list) {

                //Adicionando utilizando o SPLIT (METODO PARA SEPARA O ARRAY)
                arraydein = valor.split(",");

            }

            //Chamando a função para cadastrar o intervalo personalizado
            // intervalopersonalizado(intervaloVacinacao, arraydein, DoseInicialdiasDeVida);
            regraNegocio.intervalopersonalizado(intervaloVacinacao, arraydein, DoseInicialdiasDeVida);

        } else {

            //Chamando a função para cadastrar intervalo fixo
            //intervalofixo(intervaloVacinacao, DoseInicialdiasDeVida, intervalo);
            regraNegocio.intervalofixo(intervaloVacinacao, DoseInicialdiasDeVida, intervalo);

        }

        cadastroVacinaNoCalendarioObr(request, response);

    }

    //Altera o campo status calendario como falso dentro da vacina 
    public void excluirVacinaCalendario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CalendarioObrigatorioDAO caledarioDAO = new CalendarioObrigatorioDAO();

        CalendarioObrigatorio calendario = new CalendarioObrigatorio();
        calendario.setId_calendarioObr(Integer.parseInt(request.getParameter("calendarioOB_ID")));

        //Criando list retorno calendarioOBR
        IntervaloVacinacao intervalovacinacao = new IntervaloVacinacao();
        intervalovacinacao.setAtivoOuNao(true);
        intervalovacinacao.setCalendarioObr(calendario);

        //Consultando todos os interlavos de vacinacao do determinado calendarioOB
        IntervaloVacinacaoDAO intervalovacinacaDAO = new IntervaloVacinacaoDAO();

        ArrayList<IntervaloVacinacao> listcalendario = intervalovacinacaDAO.buscaUnicaIntervaloVacinacao(intervalovacinacao);

        //Lopping para desativar todos os interlavos do calendario especifico
        for (IntervaloVacinacao retornoDAO : listcalendario) {

            retornoDAO.setAtivoOuNao(false);
            retornoDAO.setCalendarioObr(calendario);

            intervalovacinacaDAO.excluirIntervaloVacinacaoPorDose(retornoDAO);

        }

        //Excluindo Logicamente o calendario Obrigatorio da vacina especifica
        calendario.setStatus(false);
        caledarioDAO.excluirVacinaCalendarioObrPorId(calendario);

        //Buscando o Id da vacina que esta cadastrado no calendario OBR
        int id_vacina = caledarioDAO.buscaIdVacinaCalendarioobrEspecifico(calendario);

        //Setando as informção e excluindo logicamente a vacina
        Vacina vacina = new Vacina();
        vacina.setStatus(false);
        vacina.setId_vacina(id_vacina);

        VacinaDAO vacinaDAO = new VacinaDAO();
        vacinaDAO.atualizar_campo_vacinaCalendarioob(vacina);

        exibeVacinaNoCalendarioObr(request, response);
    }

    //Busca a vacina do calendario Obrigatorio e envia para o forme de atualização
    public void editarVacinaCalendario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        CalendarioObrigatorio calendario = new CalendarioObrigatorio();
//        CalendarioObrigatorioDAO calendarioDAO = new CalendarioObrigatorioDAO();
//        VacinaDAO vacinaDAO = new VacinaDAO();
//        calendario.setId_calendarioObr(Integer.parseInt(request.getParameter("id_Calendario")));
//
//        calendario = calendarioDAO.buscaUnicaVacinaCalendarioObr(calendario.getId_calendarioObr());
//
//        calendario.setVacina(vacinaDAO.buscarVacinaUnica(calendario.getVacina().getId_vacina()));
//
//        request.setAttribute("vacina", calendario);
//        RequestDispatcher rd = request.getRequestDispatcher("paginas_calendarioobr/atualiza_vacina_calendarioobr.jsp");
//        rd.forward(request, response);

    }

    //Atualiza a quantidades de dias vividos necessários para a inserção do medicamentos
    public void atualizaVacinaCalendario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TratamentoDeData cvDate = new TratamentoDeData();
        CalendarioObrigatorioDAO calendarioDAO = new CalendarioObrigatorioDAO();
        CalendarioObrigatorio calendarioObr = new CalendarioObrigatorio();

        calendarioObr.getVacina().setId_vacina(Integer.parseInt(request.getParameter("id_vacina")));

        int dias = Integer.parseInt(request.getParameter("txtDia"));
        int mes = Integer.parseInt(request.getParameter("txtMes"));
        int ano = Integer.parseInt(request.getParameter("txtAno"));

        calendarioObr.setComentario(request.getParameter("txtComentario"));

        calendarioDAO.atualizaVacinaCalendarioObr(calendarioObr);

        exibeVacinaNoCalendarioObr(request, response);

    }

    //
    private void listarmesescalendario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {

        //Modelos
        Usuario usuario = new Usuario();
        Vacina vacina = new Vacina();
        CalendarioObrigatorio calendariobbrigatorio = new CalendarioObrigatorio();
        TratamentoDeData tratamentodata = new TratamentoDeData();
        IntervaloVacinacao intervalov = new IntervaloVacinacao();

        //Daos
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        CalendarioObrigatorioDAO calendarioobrDAO = new CalendarioObrigatorioDAO();
        VacinaDAO vacinaDAO = new VacinaDAO();

        ArrayList<Vacina> listvacinas = new ArrayList<>();

        int dia = Integer.parseInt(request.getParameter("dia"));
        int mes = Integer.parseInt(request.getParameter("mes"));
        int usuario_id = Integer.parseInt(request.getParameter("usuario_id"));

        CalendarioObrRegraNegocio regraNegocio = new CalendarioObrRegraNegocio();

        usuario.setUsuario_id(usuario_id);

        usuario = usuarioDAO.buscaUsuarioUnico(usuario);

        //CRIAR METHODU   01/01/2020
        String dataformulada = request.getParameter("dia") + "/" + request.getParameter("mes") + "/" + request.getParameter("ano");

        long diasVida_usuario = regraNegocio.CalcularNascimentoEmDiasVida(usuario.getNascimento(), dataformulada);

        calendariobbrigatorio = regraNegocio.ConsultaAFaixaDiasDeVidaMesAtual(diasVida_usuario, dia, mes, calendariobbrigatorio);

        calendariobbrigatorio.setStatus_intervalov(true);
        calendariobbrigatorio.setStatus(true);

        ArrayList<CalendarioObrigatorio> listVacinasPorFaixaDeVida = calendarioobrDAO.buscarVacinasPorFaixasdeVidaParaocalendario(calendariobbrigatorio);

        //EXECUTA ESTE PRIMEIRO
        for (CalendarioObrigatorio calendario : listVacinasPorFaixaDeVida) {

            int retorno_idvacina = vacinaDAO.ConsultarVacinaComrestricoesIguaisAoUsuario(calendario.getVacina().getId_vacina(), usuario_id);

            if (retorno_idvacina == 0) {
                Vacina vacinalop = new Vacina();
                vacinalop.setId_vacina(calendario.getVacina().getId_vacina());
                listvacinas.add(vacinalop);

            }
        }

        //METHORD A PARTE
        ArrayList<CalendarioObrigatorio> listdevacinausuario = new ArrayList<>();

        CardenetaUsuarioDAO cardenetaDAO = new CardenetaUsuarioDAO();
        
        for (Vacina listVacinasUsuarioPodeTomar : listvacinas) {

            calendariobbrigatorio.setVacina(vacina);
            calendariobbrigatorio.setIntervalov(intervalov);
            calendariobbrigatorio.setStatus(true);
            calendariobbrigatorio.getIntervalov().setAtivoOuNao(true);
            
            calendariobbrigatorio.getVacina().setId_vacina(listVacinasUsuarioPodeTomar.getId_vacina());

            CalendarioObrigatorio calendobr = calendarioobrDAO.buscarVacinasParaExibirNoCalendarioUsuario(calendariobbrigatorio);
            

       
            //CONSULTAR SE O USUARIO TEM ESTA VACINA
            CadernetaUsuario cardenetausuario = new CadernetaUsuario();
            cardenetausuario.getCalendarioObrigatorio().setId_calendarioObr(calendobr.getId_calendarioObr());
            cardenetausuario.getUsuario().setUsuario_id(usuario.getUsuario_id());
            cardenetausuario.getCalendarioObrigatorio().getIntervalov().setDose(calendobr.getIntervalov().getDose());
            cardenetausuario.setCaderneta_dose(calendobr.getIntervalov().getDose());
            CalendarioObrigatorio calendobrU = cardenetaDAO.consultarVacinaNaCadernetaUsuario(cardenetausuario);       
            
             if(calendobrU.getId_calendarioObr() != 0){
                 calendobrU.setVacinacao_concluida(1);
                 listdevacinausuario.add(calendobrU);
            }else{
                listdevacinausuario.add(calendobr);
            }
        
        }

        int diasdomes = tratamentodata.retornaTamanhoDoMesReferente(mes);

        request.setAttribute("listavacinasusuario", listdevacinausuario);
        request.setAttribute("totaldiasmes", diasdomes);
        request.setAttribute("diasdevidanoiniciomes", calendariobbrigatorio.getDiasvidainiciomes());
        RequestDispatcher rd = request.getRequestDispatcher("paginas_paciente/listar_calendarioAjax.jsp");
        rd.forward(request, response);

    }

    private void exibeVacinasObdoMes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {

        String msg = null;

        //Modelos
        Usuario usuario = new Usuario();
        Vacina vacina = new Vacina();
        CalendarioObrigatorio calendariobbrigatorio = new CalendarioObrigatorio();
        IntervaloVacinacao intervalov = new IntervaloVacinacao();

        //Daos
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        CalendarioObrigatorioDAO calendarioobrDAO = new CalendarioObrigatorioDAO();
        VacinaDAO vacinaDAO = new VacinaDAO();

        ArrayList<CalendarioObrigatorio> listVacinasPorFaixaDeVida = new ArrayList<>();
        ArrayList<Vacina> listvacinas = new ArrayList<>();

        Date data = new Date();

        SimpleDateFormat formatarData = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatarDataCadastro = new SimpleDateFormat("01/MM/yyyy");

        CalendarioObrRegraNegocio regraNegocio = new CalendarioObrRegraNegocio();

        String usuario_rg = request.getParameter("rg_usuario");
        usuario.setRg(usuario_rg);

        usuario = usuarioDAO.buscaUsuarioPorRg(usuario);
        
        //Verificando se a consulta funcionou
        if (usuario.getUsuario_id() != 0) {
            int usuario_id = usuario.getUsuario_id();

            //Dias de vida atual do usuario
            Long diasdevidaatual = regraNegocio.CalcularNascimentoEmDiasVida(usuario.getNascimento(), formatarData.format(data));
            
            //Dias de vida que o usuario estava quando realizou o cadastro no sistema
            Long diasdevidacadastro = regraNegocio.CalcularNascimentoEmDiasVida(usuario.getNascimento(), formatarDataCadastro.format(usuario.getDatacadastro()));

            calendariobbrigatorio.setDiasvidainiciomes(Integer.valueOf(diasdevidacadastro.toString()));
            calendariobbrigatorio.setDiasvidafinalmes(Integer.valueOf(diasdevidaatual.toString()));
            calendariobbrigatorio.setStatus_intervalov(true);
            calendariobbrigatorio.setStatus(true);

            listVacinasPorFaixaDeVida = calendarioobrDAO.buscarVacinasPorFaixasdeVidaParaocalendario(calendariobbrigatorio);

            //EXECUTA LISTA DE VACINAS PARA CONSULTAR AS VACINAS DISPONIVEIS PARA O USUARIO
            for (CalendarioObrigatorio calendario : listVacinasPorFaixaDeVida) {

                //Consulta restrições igual ao do usuario
                int retorno_idvacina = vacinaDAO.ConsultarVacinaComrestricoesIguaisAoUsuario(calendario.getVacina().getId_vacina(), usuario_id);

                if (retorno_idvacina == 0) {
                    
                    Vacina vacinalop = new Vacina();
                    vacinalop.setId_vacina(calendario.getVacina().getId_vacina());
                    listvacinas.add(vacinalop);

                }
            }

        } else {

            msg = "Usuario não encontrado";

        }

        //METHORD A PARTE
        ArrayList<CalendarioObrigatorio> listdevacinausuario = new ArrayList<>();

        //Contador auxiliar
        int cont = 0;

        CardenetaUsuarioDAO cardenetaDAO = new CardenetaUsuarioDAO();
        
        for (Vacina listVacinasUsuarioPodeTomar : listvacinas) {

            calendariobbrigatorio.setVacina(vacina);
            calendariobbrigatorio.setIntervalov(intervalov);
            calendariobbrigatorio.setStatus(true);
            calendariobbrigatorio.getIntervalov().setAtivoOuNao(true);
            calendariobbrigatorio.getVacina().setId_vacina(listVacinasUsuarioPodeTomar.getId_vacina());

            
            CalendarioObrigatorio calendobr = calendarioobrDAO.buscarVacinasParaExibirNoCalendarioUsuario(calendariobbrigatorio);
            

            //CONSULTAR SE O USUARIO TEM ESTA VACINA
            CadernetaUsuario cardenetausuario = new CadernetaUsuario();
            cardenetausuario.getCalendarioObrigatorio().setId_calendarioObr(calendobr.getId_calendarioObr());
            cardenetausuario.getUsuario().setUsuario_id(usuario.getUsuario_id());
            cardenetausuario.getCalendarioObrigatorio().getIntervalov().setDose(calendobr.getIntervalov().getDose());
            cardenetausuario.setCaderneta_dose(calendobr.getIntervalov().getDose());
            CalendarioObrigatorio calendobrU = cardenetaDAO.consultarVacinaNaCadernetaUsuario(cardenetausuario);          
            
            if(calendobrU.getId_calendarioObr() == 0){
                listdevacinausuario.add(calendobr);
            }
            
            //Saber quantos registros possui
            cont = cont + 1;
        }

        if (cont == 0) {
            msg = "Usuario não possui nenhuma vacina obrigatória neste mês!";
        } else if(msg == null){
            msg = "Resultado da pesquisa!";
        }

        request.setAttribute("msg", msg);
        request.setAttribute("id_usuario", usuario.getUsuario_id());
        request.setAttribute("listavacinasusuario", listdevacinausuario);
        RequestDispatcher rd = request.getRequestDispatcher("paginas_enfermeiro/listar_VacinasdoMesAJAX.jsp");
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
