<%-- 
<%-- 
    Document   : cadastar_vacina
    Created on : 25/11/2018, 11:21:48
    Author     : nelson_amaral
--%>

<%@page import="model.Restricao"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="/paginas_utilitarias/cabecalho.jsp" />

<%
    Restricao restricao = (Restricao) request.getAttribute("restricao");
%>

<!DOCTYPE html>

<main>

    <div class="container">
        <div class="row">
            <div class="col s12">
                <div class="card z-depth-2">
                    <div class="card-content">
                        <span class="card-title titulo-tabela center-align letra">Atualiza Restricões</span>
                        <form action="ControleRestricoes" method="POST">
                            <div class="row">
                                <% %>
                                <div class="col s6 input-field hoverable">
                                    <input type="text" name="txtNome" value="<%= restricao.getRestricao_nome()%>" id="txtNome" class="hoverable validate" placeholder="Maximo 30 caracteres" size="30" maxlength=30 required>
                                    <label class="black-text" for="txtNome">Restrições<i class="material-icons left">account_box</i></label>                                   
                                </div>
                                    <div class="col s6 input-field">
                                        <label class="black-text" for="txtTipo">Descrição<i class="material-icons left">account_box</i></label>                                             
                                        <br>
                                        <textarea style="height: 150px" type="text" name="txtTipo" id="txtTipo" class="validate" placeholder="Maximo 30 caracteres" required><%= restricao.getRestricao_tipo()%></textarea>
                                     </div>
                            </div>
                            <input type="text" name="txtId" value="<%= restricao.getRestricao_id()%>" hidden>
                            <input class="btn left" type="submit" value="Confirma" name="acao" >
                        </form>
                        <div class="right">
                            <a href="ControleRestricoes?acao=Listar" class="btn blue">Voltar</a>
                        </div>
                        <br>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
</body>

<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="/paginas_utilitarias/rodape.jsp" />

</html>
