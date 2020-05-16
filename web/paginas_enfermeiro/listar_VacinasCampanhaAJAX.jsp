<%-- 
    Document   : listar_VacinasdoMesAJAX
    Created on : 24/10/2019, 00:19:44
    Author     : Victor_Aguiar
--%>

<%@page import="model.Campanha"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page import="java.util.ArrayList"%>


<%
    ArrayList<Campanha> exibirVacinasCampanha = (ArrayList<Campanha>) request.getAttribute("campanhaspermitidas");

    Integer id_usuario = (Integer) request.getAttribute("id_usuario");
%>

<!-- Msg para o usuario --> 
<% String msg = (String) request.getAttribute("msg");
    if (msg != null) {%><center><a class="letra red-text"><%=msg%></a></center><%}%>


<% for (Campanha vacinascampanha : exibirVacinasCampanha) {%>
<div class="col s4 vacinas">
    <div class="card z-depth-2">
        <div class="card-content">
            <form action="../ControleCardenetaUsuario" method="POST">
                <a class="letra left black-text">Campanha: &nbsp&nbsp<a class="letra left blue-text"> <%= vacinascampanha.getCampanha_nome()%></a></a><br>
                <a class="letra left black-text">Período &nbsp&nbsp<a class="letra left blue-text"><%= vacinascampanha.getCampanha_inicio()%> / <%= vacinascampanha.getCampanha_prevista()%></a></a><br>
                <a class="letra black-text left">Vacina: &nbsp&nbsp<a class="letra left blue-text info-vacina"> <%= vacinascampanha.getVacina().getNome()%></a></a><br>
                <a class="letra left black-text">Tipo V: &nbsp&nbsp<a class="letra left blue-text"> <%= vacinascampanha.getVacina().getTipo()%></a></a><br>

                <input name="id_usuario" value="<%= id_usuario%>" hidden>
                <input name="acao" value="CadastrarVacinaCampanha" hidden>
                <input name="campanha_id" value="<%= vacinascampanha.getCampanha_id()%>" hidden>
                <button class="center-align" onclick="Confirmaacao()"><li class="material-icons green-text letra123">check</li></button>
            </form>
        </div>
    </div>
</div>
<% }%>


