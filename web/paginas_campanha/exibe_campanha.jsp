<%-- 
    Document   : exibe_campanha
    Created on : 02/11/2019, 22:27:32
    Author     : nelson_amaral
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Campanha"%>
<%-- 
    Document   : principal_campanha
    Created on : 26/10/2019, 10:29:52
    Author     : nelson_amaral
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="../paginas_utilitarias/cabecalho.jsp" />

<!DOCTYPE html>
<main>
    <div class="container">
        <div class="row">            
            <div class="card z-depth-2">
                <div class="card-content">
                    <span class="card-title titulo-tabela center-align letra">Campanhas</span>
                    <center>
                        <div style="overflow: scroll; width: auto; height: 350px; border:solid 1px">
                            <table class="responsive-table">
                                <TR>
                                    <td class="center-align letra">Nome</td>
                                    <td class="center-align letra">Data Inicio</td>
                                    <td class="center-align letra">Previsão de Termino</td> 
                                    <td class="center-align letra">Termino</td> 
                                    <td class="center-align letra">Vacina</td> 
                                    <td class="center-align letra">Status</td> 
                                    <td class="center-align letra">Opções</td> 
                                </TR>

                                <%
                                    ArrayList<Campanha> listcampanhas = (ArrayList<Campanha>) request.getAttribute("Campanhas");
                                    SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy");
                                    for (Campanha listc : listcampanhas) {
                                %>                              
                                <TR>
                                    <td name="nome" class="center-align"><%= listc.getCampanha_nome()%></td>
                                    <td name="tipo" class="center-align"><%= formatarDate.format(listc.getCampanha_inicio())%></td>                                        
                                    <td name="tipo" class="center-align"><%= formatarDate.format(listc.getCampanha_prevista())%></td>
                                    <td name="tipo" class="center-align"><% if (listc.getCampanha_final() == null) {%><a class="blue-tex letra">Em Andamento</a><%} else {%><%= formatarDate.format(listc.getCampanha_final())%><% }%></td>
                                    <td name="tipo" class="center-align"><%= listc.getVacina().getNome()%></td>
                                    <td name="tipo" class="center-align"><%= listc.isCampanha_status() ? "<a class='blue-text letra'>Ativo</a>" : "<a class='red-text letra'>Encerrado</a>"%></td>

                                    <td class="center-align">
                                        <form action="ControleCampanha" method="POST">
                                            <button class="btn waves-effect waves-teal red" type="submit" name="acao" value="Finaliza">Finaliza</button>
                                            <input type="text" name="campanha_id" value="<%= listc.getCampanha_id()%>" hidden/>                                                
                                        </form>
                                    </td>
                                </TR>

                                <% }%>
                            </table>
                        </div>
                    </center>
                </div>
            </div>
        </div>
    </div>
</main>
</body>

<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="../paginas_utilitarias/rodape.jsp" />

</html>
