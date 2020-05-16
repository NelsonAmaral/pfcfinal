<%-- 
    Document   : atualiza_vacina_calendarioobr
    Created on : 20/04/2019, 23:38:52
    Author     : nelson_amaral
--%>
<%@page import="model.CalendarioObrigatorio"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="../paginas_utilitarias/cabecalho.jsp" />

<%
    CalendarioObrigatorio calendario = (CalendarioObrigatorio) request.getAttribute("vacina");
%>
<br>
<main>
    <div class="container">
        <div class="row">            
            <div class="card z-depth-2">
                <div class="card-content">
                    <span class="card-title titulo-tabela center-align">Atualizar Calendário Obrigatório</span>
                    <form action="ControlerCalendarioObr" method="POST">
                        <table class="responsive-table">
                            <tr>
                                <td class="center-align letra">Vacina</td>  
                                <td class="center-align letra">Doses</td>
                                <td class="center-align letra">Intervalo</td>
                                <td class="center-align letra">Tipo</td>

                                <td class="center-align letra">Dias</td>
                                <td class="center-align letra">Mes</td>
                                <td class="center-align letra">Anos</td>


                            </tr>

                            <tr>

                                <td class="center-align"><%= calendario.getVacina().getNome()%></td>
                                <td class="center-align"><%= calendario.getVacina().getDose()%></td>
                                <td class="center-align"><%= calendario.getVacina().getIntervalo()%></td>
                                <td class="center-align"><%= calendario.getVacina().getTipo()%></td>


                                <td class="center-align"><input type="text" name="txtDia" pattern="[0-9]+$" required/></td>
                                <td class="center-align"><input type="text" name="txtMes" pattern="[0-9]+$" required/></td>
                                <td class="center-align"><input type="text" name="txtAno" pattern="[0-9]+$" required/></td>


                                <td class="center-align">

                                    <button class="btn waves-effect waves-teal blue" type="submit" name="acao" value="CofirmarAtualizacao"><i class="material-icons">edit</i></button>
                                    <input type="text" name="id_vacina" value="<%= calendario.getId_calendarioObr()%>" hidden/>

                                </td>

                            </tr>

                        </table>
                    </form>
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
</div>
</main>
</body>

<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="../paginas_utilitarias/rodape.jsp" />
</html>