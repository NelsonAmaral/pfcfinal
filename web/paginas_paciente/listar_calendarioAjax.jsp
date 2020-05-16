<%-- 
    Document   : listar_calendario
    Created on : 12/05/2019, 16:48:05
    Author     : Victor_Aguiar
--%>

<%@page import="model.CalendarioObrigatorio"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%-- 
    Document   : exibir_usuario
    Created on : 25/11/2018, 05:52:21
    Author     : nelson_amaral
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page import="java.util.ArrayList"%>


<%
    int diasiniciomesvida = (Integer) request.getAttribute("diasdevidanoiniciomes");

    int totaldediasdomes = (Integer) request.getAttribute("totaldiasmes");
    int totalformulado = totaldediasdomes + 1;

    ArrayList<CalendarioObrigatorio> exibirVacinasCardeneta = (ArrayList<CalendarioObrigatorio>) request.getAttribute("listavacinasusuario");
%>

<table>
    <thead>
        <tr>
            <td colspan="7" class="semana dia letra center-align">Listando Os Dias Do Mês Selecionado</td>
        </tr>
    </thead>

    <tbody>
        <% int quebralinha = 0;%>

        <% for (int cont = 1; cont < totalformulado;) {
                int et = 0; %>

        <% if (quebralinha == 7) { %> <TR>  <% }
            quebralinha = quebralinha + 1; %>

            <% for (CalendarioObrigatorio a : exibirVacinasCardeneta) { %>

            <% if (a.getIntervalov().getDias() == diasiniciomesvida) {%>

            
             <% if (a.getVacinacao_concluida() == 1) {%>
             <td class="dia"><a href="#" onclick="M.toast({html: '<%=a.getIntervalov().getDose() + " dose " + a.getVacina().getNome()%>', classes: 'rounded'}); descricao('<%= a.getVacina().getDescricao() %>','<%= a.getVacina().getNome() %>');" title="Já Vacinado" class="letra123 tooltipped green-text" title="<%= a.getVacina().getNome()%>"><i class="material-icons" style="font-size: 30px">colorize</i><% //a.getVacina().getNome()%></a></td>

            <% } else {%>
            <td class="dia"><a href="#" onclick="M.toast({html: '<%=a.getIntervalov().getDose() + " dose " + a.getVacina().getNome()%>', classes: 'rounded'}); descricao('<%= a.getVacina().getDescricao() %>','<%= a.getVacina().getNome() %>');" class="letra123 tooltipped" title="<%= a.getVacina().getNome()%>"><i class="material-icons" style="font-size: 30px">colorize</i><% //a.getVacina().getNome()%></a></td>
            <% } %>
            
            <% et = 1;
                      }

                  } %>        

            <% if (et != 1) {%> 

            <td class="dia"><%=cont%></td>   

            <% et = 0;
                 } %>

            <% if (quebralinha == 7) { %>

        <TR>

            <% quebralinha = 0;
                    }
                    cont = cont + 1;
                    diasiniciomesvida = diasiniciomesvida + 1;
           }%>

    </tbody>
</table>