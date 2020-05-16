<%-- 
    Document   : consultar_restricoes
    Created on : 28/03/2019, 18:35:20
    Author     : Victor Aguiar
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Posto"%>
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
                    <span class="card-title titulo-tabela center-align letra">Consultar Postos</span>

                    <center><div style="overflow: scroll; width: auto; height: 350px; border:solid 1px">
                            <table class="responsive-table">
                                <TR>
                                    <td class="center-align letra">Nome</td>
                                    <td class="center-align letra">Telefone</td>
                                    <td class="center-align letra">Ativo</td> 
                                </TR>

                                <% 
                                    ArrayList<Posto> listposto = (ArrayList<Posto>) request.getAttribute("listposto");

                                    for (Posto listObr : listposto) {%>
                                           
                                    <TR>
                                        <td name="nome" class="center-align"><%=listObr.getPosto_nome()%></td>
                                        <td name="tipo" class="center-align"><%=listObr.getPosto_telefone()%></td>                                        
                                        <td name="tipo" class="center-align"><%=listObr.isPosto_ativo()? "<a class='blue-text letra'>Ativo</a>": "<a class='red-text letra'>Fechado</a>"%></td>

                                        <td class="center-align">
                                            <form action="ControlePosto" method="POST">
                                                <button class="btn waves-effect waves-teal red" type="submit" name="acao" value="Deletar"><i class="material-icons">delete</i></button>
                                                <button class="btn waves-effect waves-teal blue" type="submit" name="acao" value="Editar"><i class="material-icons">edit</i></button>
                                                <input type="text" name="id_posto" value="<%=listObr.getPosto_id()%>" hidden/>                                                
                                            </form>
                                        </td>
                                    </TR>
                                            <% } %>
                            </table>
                        </div>

                        <div class="row">
                            <br>
                            <!--<a class="btn" href="paginas_vacina/cadastrar_vacina.jsp">Cadastrar</a>-->
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

