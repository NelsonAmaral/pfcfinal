<%-- 
    Document   : listar_VacinasdoMesAJAX
    Created on : 24/10/2019, 00:19:44
    Author     : Victor_Aguiar
--%>

<%@page import="model.CalendarioObrigatorio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page import="java.util.ArrayList"%>


<% 
    ArrayList<CalendarioObrigatorio> exibirVacinas = (ArrayList<CalendarioObrigatorio>) request.getAttribute("listavacinasusuario");

    Integer id_usuario = (Integer) request.getAttribute("id_usuario");
%>

<!-- Msg para o usuario --> 
<% String msg = (String) request.getAttribute("msg"); if (msg != null) {%><center><a class="letra red-text"><%=msg%></a></center><%}%>
   

<% for (CalendarioObrigatorio list : exibirVacinas) {%>
<div class="col s4 vacinas">
    <div class="card z-depth-2">
        <div class="card-content">
            <form action="../ControleCardenetaUsuario" method="POST">
            <a class="letra black-text left">Vacina: &nbsp&nbsp<a class="letra left blue-text info-vacina"> <%=list.getVacina().getNome()%></a></a><br>
            <a class="letra left black-text">Tipo V: &nbsp&nbsp<a class="letra left blue-text"><%=list.getVacina().getTipo()%></a></a><br>
            <a class="letra left black-text">Qtd Dose: &nbsp&nbsp<a class="letra left blue-text"><%=list.getIntervalov().getDose()%></a></a><br>
            
            <input name="id_usuario" value="<%= id_usuario %>" hidden>
            <input name="acao" value="CadastrarVacinaObrigatoria" hidden>
            <input name="dose" value="<%=list.getIntervalov().getDose()%>" hidden>
            <input name="calendariobr_id" value="<%= list.getId_calendarioObr() %>" hidden>
            <button class="center-align" onclick="Confirmaacao()"><li class="material-icons green-text letra123">check</li></button>
            </form>
        </div>
    </div>
</div>
<% }%>

