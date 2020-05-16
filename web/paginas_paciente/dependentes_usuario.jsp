<%-- 
    Document   : dependentes_usuario
    Created on : 10/11/2019, 14:18:14
    Author     : Victor_Aguiar
--%>

<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="../paginas_utilitarias/cabecalho_03.jsp" />

<!DOCTYPE html>
<html>
    <body>

        <div class="card z-depth-2">
            <div class="card-content">
                <center><h5 class="card-title letra titulo-tabela center-align">Tabela de Dependentes</h5></center>

                <div class="row">
                    <div class="col s3"></div>
                    <div class="col s6">
                        <div class="card z-depth-2">
                            <div class="card-content">
                                <table class="responsive-table">
                                    <TR>
                                        <td class="letra center-align">Nome</td>
                                        <td class="letra center-align">Data de Nascimento</td>
                                        <td class="letra center-align">Rg</td>
                                        <td class="letra center-align">Entrar</td>             
                                    </TR>

                                    <%                ArrayList<Usuario> listDependentes = (ArrayList<Usuario>) request.getAttribute("listdependentes");
                                        for (Usuario usuario_dependentes : listDependentes) {
                                    %>
                                    <TR>
                                        <td class="center-align"><%= usuario_dependentes.getNome()%></td>
                                        <td class="center-align"><%= usuario_dependentes.getNascimento() %></td>
                                        <td class="center-align"><%= usuario_dependentes.getRg()%></td>
                                        <td class="center-align">
                                            <form action="ControleAcesso" method="POST">
                                                <input name="usuario_id" value="<%= usuario_dependentes.getUsuario_id()%>" hidden>
                                                <button type="submit" name="acao" value="AcessoDependente" class="grenn-text"><i class="material-icons">fast_forward</i></button>
                                            </form>
                                        </td>             
                                    </TR>
                                    <% }%>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>



<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="../paginas_utilitarias/rodape.jsp" />
