<%-- 
    Document   : exibe_vacinas_calendarioobr
    Created on : 20/04/2019, 13:53:01
    Author     : nelson_amaral
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="../paginas_utilitarias/cabecalho.jsp" />

<!-- Modal de exbição dos intervalos -->
<div id="modalIntervalos" class="modal">
    <div class="modal-content">
        <div class="row">
            <div class="col s8">
                <h4 class="black-text">Codigo: <a id="calendarioOB_ID" class="blue-text letra calendarioOB_ID"></a></h4>
            </div>
            <div class="col s4 right">
                <a href="#!" class="modal-close btn-large waves-effect waves-green btn-flat">Voltar</a>
            </div>
        </div>

        <!-- Tabela exibida via Ajax -->
        <div id="exibetabelaAJAX"></div>
        
    </div>
</div>

<script>
    $(document).ready(function () {
        $('.modal').modal();
    });

</script>


<main>
    <div class="container">
        <div class="row">            
            <div class="card z-depth-2">
                <div class="card-content">
                    <span class="card-title titulo-tabela letra center-align">Calendario Obrigatorio</span>

                    <div class="row">
                        <div class="col s3">
                            <label class="black-text left-align" for="txtPesquisaNome">Pesquisa Nome Vacina<i class="material-icons left">search</i></label>

                            <input id="filtrar-PesquisaNomeVacina">
                        </div>


                        <div class='col s3'>
                            <label class="black-text left-align" for="txtTipo">Pesquisa Tipo Vacina<i class="material-icons left">search</i></label>
                            <select class="browser-default" id="filtrar-PesquisaTipoVacina" name="txtPesquisaTipo" required>
                                <option value="" selected>Selecione...</option>
                                <option value="Vacinas_vivas_atenuadas">Vacinas vivas atenuadas</option>
                                <option value="Vacinas_mortas_inactivadas">Vacinas mortas inactivadas</option>
                                <option value="Vacinas_sub_unitarias">Vacinas sub unitárias</option>
                            </select>
                        </div>
                    </div>



                    <div class="card z-depth-2">
                        <div class="card-content">
                            <center><div style="overflow: scroll; width: auto; height: 350px; border:solid 1px">
                                    <table class="responsive-table">

                                        <tr>
                                            <td class="center-align letra">Vacina</td>  
                                            <td class="center-align letra">Tipo</td>
                                            <td class="center-align letra">Total de Dose</td> 
                                            <td class="center-align letra">Intervalo Doses</td>  

                                            <td class="center-align letra">Excluir</td>
                                        </tr>
                                        <c:forEach var="calendario" items="${listaVacinas}">
                                            <tr class="vacina_nome vacina_ciclo vacina_tipo">

                                                <td class="center-align blue-text letra info-VacinaNome"><c:out value="${calendario.vacina.nome}"/></td>
                                                <td class="center-align blue-text letra info-TipoVacina"><c:out value="${calendario.vacina.tipo}"/></td>
                                                <td class="center-align blue-text letra"><c:out value="${calendario.dose}"/></td>
                                                <td class="center-align blue-text blue-text letra"><a href="#modalIntervalos" onclick="visualizarmodalintervalos(${calendario.id_calendarioObr})" class="modal-trigger black-text"><i class="material-icons">remove_red_eye</i></a></td>

                                                <td class="center-align">
                                                    <form action="ControlerCalendarioObr" method="POST">
                                                        <button class="btn waves-effect waves-teal red" type="submit" name="acao" value="Deletar"><i class="material-icons">delete</i></button>
                                                        <input type="text" name="calendarioOB_ID" value="${calendario.id_calendarioObr}" hidden/>
                                                    </form>
                                                </td>

                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div></center>
                        </div>
                    </div>


                    <br>
                    <div class="container">
                        <div class="row right">
                            <form action="ControleAcesso" method="POST">
                                <input value="Voltar" name="acao" hidden>
                                <button class="btn" type="submit">Voltar</button>
                            </form>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<script>

    function visualizarmodalintervalos(calendarioOB_ID) {
        document.getElementById("calendarioOB_ID").innerHTML = calendarioOB_ID;
        buscarintervalos(calendarioOB_ID);
    }
    ;

</script>

</body>



<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="../paginas_utilitarias/rodape.jsp" />
<script src="framework/js/Busca_Ajax/Busca_exibevacinas_calendarioOB/Buscar-intervalos_doses.js" type="text/javascript"></script>

<!-- IMPORTANDO ARQUIVOS JS PARA FILTRO DA TABELA -->
<script src="framework/js/Filtro_Paginas/Filtro_exibecalendarioobr/Filtro_PesquisaClicoVacina.js" type="text/javascript"></script>
<script src="framework/js/Filtro_Paginas/Filtro_exibecalendarioobr/Filtro_PesquisaNomeVacina.js" type="text/javascript"></script>
<script src="framework/js/Filtro_Paginas/Filtro_exibecalendarioobr/Filtro_PesquisaTipoVacina.js" type="text/javascript"></script>


</html>
