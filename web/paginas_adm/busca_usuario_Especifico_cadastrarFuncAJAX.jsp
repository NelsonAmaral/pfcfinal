<%-- 
    Document   : listar_calendario
    Created on : 12/05/2019, 16:48:05
    Author     : Victor_Aguiar
--%>

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

<% if (request.getAttribute("msg") != null){ %>

 <h6 class="letra center-align black-text"><%=request.getAttribute("msg") %></h6>
 
<% }else{ %>

        <table>
            <thead>
                <tr>
                    <td><div class="center-align letra">Checkbox</div></td>
                    <td><div class="center-align letra">Nome</div></td>
                    <td><div class="center-align letra">Cpf</div></td>
                    <td><div class="center-align letra">Rg</div></td>
                </tr>
            </thead>

            <tbody>

                    <tr>
                        <td><div class="center-align blue-text letra"><input type="checkbox" value="${usuario_pesquisa.usuario_id}" name="txtUsuarioFK" required></div></td>
                        <td><div class="center-align blue-text letra">${usuario_pesquisa.nome}</div></td>
                        <td><div class="center-align blue-text letra">${usuario_pesquisa.cpf}</div></td>
                        <td><div class="center-align blue-text letra">${usuario_pesquisa.rg}</div></td>

                    </tr>

            </tbody>
        </table>

<% } %>