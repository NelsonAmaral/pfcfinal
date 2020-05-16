<%-- 
    Document   : vacina_altera_restricoes
    Created on : 13/04/2019, 16:15:26
    Author     : nelson_amaral
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Restricao"%>
<%@page import="model.Vacina"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="../paginas_utilitarias/cabecalho.jsp" />



<br>
<main>
    <div class="container">
        <div class="row">            
            <div class="card z-depth-2">
                <div class="card-content">
                    <span class="card-title titulo-tabela center-align">Tabela Retrições</span>
                    <% String msg = (String) request.getAttribute("msg");
                        if (msg != null) {%><center><a class="letra red-text"><%=msg%></a></center><%}%>

                    <table class="striped">
                        <tr>
                            <td class="center-align letra">Nome</td>  
                            <td class="center-align letra">Descrição</td>
                            <td class="center-align letra">Deletar</td> 

                        </tr>
                        <%

                            Vacina vacina = (Vacina) request.getAttribute("vacina");
                            ArrayList<Restricao> listaRestricao = (ArrayList<Restricao>) request.getAttribute("listaRestricoes");;
                            if (vacina.getRestricoes() != null) {
                                for (Restricao r : vacina.getRestricoes()) {
                        %>

                        <tr>
                            <td class="center-align"><%=r.getRestricao_nome()%></td>
                            <td name="tipo" class="center-align"><a href="#" onclick="tiporestricao('<%=r.getRestricao_tipo()%>')"><i class="material-icons">chat</i></a></td> 

                            <td class="center-align">
                                <form action="ControleVacina" method="POST">
                                    <button type="submit" name="acao" value="DeletaRestricao"><i class="material-icons">delete</i></button>
                                    <input type="text" name="id_restricao" value="<%=r.getRestricao_id()%>" hidden/>
                                    <input type="text" name="id_vacina"value="<%=vacina.getId_vacina()%>"  hidden/>
                                </form>
                            </td>
                        </tr>

                        <%}
                            }%> 
                    </table>
                    <br>
                    <div class="container">
                        <div class="row right">
                            <form action="ControleAcesso" method="POST">
                                <input value="Voltar" name="acao" hidden>
                                <button class="btn" type="submit">Voltar</button>
                            </form>

                        </div>
                        <div>
                            <!-- Modal Trigger -->
                            <a href="#modal1" class="waves-effect waves-light btn modal-trigger" >Cadastrar Restricão</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal Structure -->
    <div id="modal1" class="modal">
        <div class="modal-content">
            <form action="ControleVacina" method="POST">
                <h4>Adicionar nova Restrições</h4>

                <center>
                    <div style="overflow: scroll; width: auto; height: 350px; border:solid 1px">
                        <table class="striped">
                            <TR>
                                <td class="center-align">Checkbox</td>
                                <td class="center-align">Restricão</td>
                                <td class="center-align">Descrição</td>
                            </TR>

                            <% for (Restricao lr : listaRestricao) {

                                    boolean igual = false;
                                    for (Restricao lv : vacina.getRestricoes()) {
                                        if (lr.getRestricao_id() == lv.getRestricao_id()) {
                                            igual = true;
                                            break;
                                        }
                                    }

                                    if (igual == false) {
                            %>


                            <TR>
                                <td class="center-align"><input type='checkbox' name="txtRestricaoFK" value="<%=lr.getRestricao_id()%>"></td>
                                <td class="center-align"><%=lr.getRestricao_nome()%></td>
                                <td name="tipo" class="center-align"><a href="#" onclick="tiporestricao('<%=lr.getRestricao_tipo()%>')"><i class="material-icons">chat</i></a></td>
                            </TR>
                            <% }
                                }%>

                        </table>
                    </div>
                </center>

                <div class="modal-footer">
                    <input type="text" name="id_vacina"value="<%=vacina.getId_vacina()%>"  hidden/>
                    <input name="acao" value="CadastraRestricao" hidden>
                    <button type="submit" class="btn red right modal-close">Adicionar nova Restrições</button>
                </div>
            </form>
        </div>
    </div>
</div>
</main> 


<script>

    $(document).ready(function () {
        $('.modal').modal();

        $(".button-collapse").sideNav();

        $(".slider").slider();

        $('.modal-trigger').leanModal({
            dismissible: true, //Pode ser fechado ao clicar fora do modal
            opacity: .6, // Opacidade do fundo da modal
            in_duration: 500, // Duração da transição de abrir modal
            out_duration: 500 // /duração da transação de fechar modal
        });
    });

    function tiporestricao(descricao) {
        alert(descricao);
    }
</script>

</body>

<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="../paginas_utilitarias/rodape.jsp" />
</html>