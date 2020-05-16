<%-- 
  Document   : cadastro_vacinas_calendarioobr
  Created on : 20/04/2019, 13:51:34
  Author     : nelson_amaral
--%>
<%@page import="model.Funcionario"%>
<%@page import="model.Vacina"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.CalendarioObrigatorio"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="../paginas_utilitarias/cabecalho.jsp" />

<%
    ArrayList<Vacina> listaVacina = (ArrayList<Vacina>) request.getAttribute("listaVacinas");

    //recupera o usuario da sessao
    Funcionario funcionario = (Funcionario) session.getAttribute("usuarioAutenticado");
    if (funcionario != null) {
        System.out.println("id " + funcionario.getId_funcionario());


%>


<br>

<div id="modalcadastrarvacina" class="modal">
    <br>
    <center><h4 class="black-text">Cadastrar Calendario</h4></center>

    <div class="row"> 
        <div class="col s6">
            <input class="letra center-align" id="nomevacina" disabled>
        </div>
        <div class="col s6">
            <input class="letra center-align" id="tipovacina" disabled>
        </div>
    </div>

    <form action="ControlerCalendarioObr" method="Post">
        <!-- input setado id da vacina para cadastro -->
        <input id="idVacina" name="idVacina" hidden>
        <input name="idfuncionario" value="<%=funcionario.getId_funcionario()%>" hidden>
        <% }%>
        <div class="card z-depth-2">
            <div class="card-content">
                <center><h5 class="black-text">Adicione a data inicial do calendario</h5></center><br>
                <div class="row">

                    <div class="col s3"></div>
                    <div class="col s3 input-field hoverable">
                        <input title="Este campo define a idade inicial que aparecerá para o usuario" type="text" onchange="validarPreenchimento()" name="txtIdadeouMes" id="txtAnos" class="hoverable validate center-align" placeholder="000" size="3" maxlength=3 required>
                        <label class="black-text" for="txtIdadeouMes">Idade/Mêses<i class="material-icons left">account_box</i></label>                                   
                    </div>
                    <div class='col s4'>
                        <label class="black-text left-align" for="txtTipo">Tipo<i class="material-icons left">assignment</i></label>                     
                        <select title="" class="browser-default" onchange="validarPreenchimento()" id="txtTipo" name="txtTipo" required>
                            <option value="" disabled selected>Selecione...</option>
                            <option value="0">Anos de Vida</option>
                            <option value="1">Meses de Vida</option>
                        </select>
                    </div>                   
                </div>
            </div>
        </div>

        <div class="card z-depth-2">
            <div class="card-content">
                <center><h5 class="black-text">Adicionando Quant de Doses e Intervalos para Vacinação</h5></center><br>

                <div class="row">
                    <!-- DIASDEVIDA / COMENTARIO / QUANT DOSE / INTERVALO DOSE / TIPO (CICLICO ou SEQUENCIAL) / BLOCK_RESTRINGI VACINA A SER INSERIDA SEM TOMAR A PRIMEIRA -->
                    <div class="row">
                         <div class="col s3"></div>
                        <div class="col s3">
                            <label class="black-text" for="txtDose">Dose<i class="material-icons left">account_box</i></label>   
                            <input type="text" name="txtDose" id="txtDose" class="hoverable validate center-align" placeholder="00" size="2" maxlength=2 required>                                
                        </div>
                        <div class='col s4'>
                            <label class="black-text left-align" for="txtMes">Intervalo<i class="material-icons left">assignment</i></label>
                            <select class="browser-default" onchange="CamposPersoanlizados()" id="txtIntervalo" name="txtIntervalo">
                                <option value="" selected>Selecione...</option>
                                <option value="0">Personalizado</option>
                                <option value="1">1 Mês (30 dias)</option>
                                <option value="2">2 Mês (60 dias)</option>
                                <option value="3">3 Mês (90 dias)</option>
                                <option value="4">4 Mês (120 dias)</option>
                                <option value="5">5 Mês (150 dias)</option>
                                <option value="6">6 Mês (180 dias)</option>
                                <option value="7">7 Mês (210 dias)</option>
                                <option value="8">8 Mês (240 dias)</option>
                                <option value="9">9 Mês (270 dias)</option>
                                <option value="10">10 Mês (300 dias)</option>
                                <option value="11">11 Mês (330 dias)</option>
                                <option value="01">1 Ano</option>
                                <option value="02">2 Ano</option>
                                <option value="03">3 Ano</option>
                                <option value="04">4 Ano</option>
                                <option value="05">5 Ano</option>
                                <option value="06">6 Ano</option>
                                <option value="07">7 Ano</option>
                                <option value="08">8 Ano</option>
                                <option value="09">9 Ano</option>
                                <option value="010">10 Ano</option>
                                <!-- Personalizado = 0 -->
                            </select>
                        </div>
                    </div>

                    <div class="row">
                        <div id="mensagemUsuario"></div>
                        <div id="campospersonalizados">
                            <div class="col s2"></div>
                            <div class="col s6">
                                <label class="black-text left-align" for="txtMes">Adicionando Intervalos Personalizados <a id="NumberDose">DOSE 2</a><i class="material-icons left">assignment</i></label>
                                <select class="browser-default" id="txtIntervalosPersonalizados" name="txtIntervalosPersonalizados">
                                    <option value="" disabled selected>Selecione...</option>
                                    <option value="1">1 Mês (30 dias)</option>
                                    <option value="2">2 Mês (60 dias)</option>
                                    <option value="3">3 Mês (90 dias)</option>
                                    <option value="4">4 Mês (120 dias)</option>
                                    <option value="5">5 Mês (150 dias)</option>
                                    <option value="6">6 Mês (180 dias)</option>
                                    <option value="7">7 Mês (210 dias)</option>
                                    <option value="8">8 Mês (240 dias)</option>
                                    <option value="9">9 Mês (270 dias)</option>
                                    <option value="10">10 Mês (300 dias)</option>
                                    <option value="11">11 Mês (330 dias)</option>
                                    <option value="01">1 Ano</option>
                                    <option value="02">2 Ano</option>
                                    <option value="03">3 Ano</option>
                                    <option value="04">4 Ano</option>
                                    <option value="05">5 Ano</option>
                                    <option value="06">6 Ano</option>
                                    <option value="07">7 Ano</option>
                                    <option value="08">8 Ano</option>
                                    <option value="09">9 Ano</option>
                                    <option value="010">10 Ano</option>
                                </select>
                            </div>
                            <div class="col s3">  
                                <button type="button" class="btn" onclick='AdicionarIntervaloPersonalizadoArray()'>Adicionar</button>
                            </div>
                        </div>
                    </div>

                    <input name='objintervaloarray' id='objintervaloarray' hidden>

                    <div class="row">
                        <center><button type="submit" name="acao" value="Confirmar" class="btn">Cadastrar Calendario OB</button></center>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <div class="modal-footer">
        <a href="#!" class="modal-close waves-effect waves-green btn-flat">Voltar</a>
    </div>
</div>

        
<main>
    <div class="container">
        <div class="row">            
            <div class="card z-depth-2">
                <div class="card-content">
                    <span class="card-title titulo-tabela center-align letra">Listando Vacinas Sem Calendario Obrigatório</span>
                    <br>
                    <div class="col s2"></div>
                    <div class="col s8">
                        <center><div style="overflow: scroll; width: auto; height: 350px; border:solid 1px">
                                <table class="striped">
                                    <TR>
                                        <td class="center-align letra">Vacina</td>
                                        <td class="center-align letra">Tipo</td>
                                        <td class="center-align letra">Cadastrar</td>
                                    </TR>

                                    <%for (Vacina v : listaVacina) {%>
                                    <TR>
                                        <td class="center-align"><%=v.getNome()%></td>
                                        <td class="center-align"><%=v.getTipo()%></td>
                                        <td class="center-align"><a href="#modalcadastrarvacina" onclick="PassarParametrosModal('<%= v.getNome()%>', '<%= v.getTipo()%>', '<%= v.getId_vacina()%>')" class="modal-trigger btn bnt-float"><i class="material-icons">add</i></a></td>
                                    </TR>
                                    <%}%>
                                </table>
                            </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
</body>


<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="../paginas_utilitarias/rodape.jsp" />

<script src="framework/js/Cadastro_Vacinas_calendarioOBr/funcoes.js" type="text/javascript"></script>
</html>