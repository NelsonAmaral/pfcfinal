<%-- 
    Document   : exibir_usuario
    Created on : 25/11/2018, 05:52:21
    Author     : nelson_amaral
--%>
<%@page import="model.Funcionario"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="../paginas_utilitarias/cabecalho.jsp" />

<%
    Funcionario usuariologado = (Funcionario) session.getAttribute("usuarioAutenticado");

    System.out.println("NIVEL ACESSO " + usuariologado.getPerfil());
%>

<!DOCTYPE html>
<main>
    <!-- -->
    <script>
        function VariavelAUXnomeID(id) {

            var id = id;



            window.open('ControleVacina?acao=ListaRestricoes&id=' + id, 'popup', 'width=700,height=500,scrolling=auto,top=0,left=0');
        }
    </script>
    <div class="container">
        <div class="row">            
            <div class="card z-depth-2">
                <div class="card-content">
                    <span class="card-title titulo-tabela center-align">Consultar Vacinas</span>
                    <% String msg = (String) request.getAttribute("msg");
       if (msg != null) {%><center><a class="letra red-text"><%=msg%></a></center><%}%>

                    <center><div style="overflow: scroll; width: auto; height: 350px; border:solid 1px">
                            <table class="responsive-table">
                                <TR>
                                    <td class="center-align letra">Vacina</td>
                                    <td class="center-align letra">Tipo</td> 

                                    <% if (usuariologado.getPerfil().toString() == "ADMINISTRADOR") {%>
                                    <td class="center-align letra">Opção</td>   
                                    <% }%>
                                </TR>
                                <c:forEach var="vacinas" items="${listavacina}">

                                    <TR>
                                        <td class="center-align"><c:out value="${vacinas.nome}"/></td>
                                        <td class="center-align"><c:out value="${vacinas.tipo}"/></td>

                                        <% if (usuariologado.getPerfil().toString() == "ADMINISTRADOR") {%>
                                        <td class="center-align">
                                            <form action="ControleVacina" method="GET">
                                                <button onclick="confirmandoExclusao()" class="btn waves-effect waves-teal red" type="submit" name="acao" value="Deletar"><i class="material-icons">delete</i></button>
                                                <button class="btn waves-effect waves-teal blue" type="submit" name="acao" value="Editar Vacina"><i class="material-icons">edit</i></button>
                                                <button class="btn waves-effect waves-teal yellow" type="button" name="acao" onclick="VariavelAUXnomeID(${vacinas.id_vacina})" value="Atualiza_restricao"><i class="material-icons">edit</i> Restrições</button>
                                                <input type="text" name="id_vacina" value="${vacinas.id_vacina}" hidden/>
                                            </form>
                                        </td>
                                        <% }%>
                                    </TR>
                                </c:forEach>
                            </table>
                        </div></center>

                    <% if (usuariologado.getPerfil().toString() == "ADMINISTRADOR") {%>
                    <div class="col s12 row">
                        <form action="ControleRestricoes" method="POST">
                            <br>
                            <input type="submit" class="btn" name="acao" value="Cadastrar Vacinas"> 
                        </form>
                    </div>
                    <% }%>
                </div>
            </div>
        </div>
    </div>
</main>
</body>

<script>
    function confirmandoExclusao() {

        var result = confirm('Deseja realmente excluir está vacina? Lembrando que será excluido toda configuração de calendario!');

        if (result) {

            return true;

        } else {
            event.preventDefault();
        };

    }
</script>

<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="../paginas_utilitarias/rodape.jsp" />

</html>
