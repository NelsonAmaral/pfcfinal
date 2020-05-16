<%-- 
    Document   : Exibir_UsuarioPorRgAJAX
    Created on : 20/05/2019, 00:44:59
    Author     : Victor_Aguiar
--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h6 class="letra center-align blue-text"><%=request.getAttribute("msg") %></h6>
        <table class="responsive-table">
            <tr>
                <td class="center-align letra">Nome</td>  
                <td class="center-align letra">CPF</td>     
                <td class="center-align letra">Celular</td>
                <td class="center-align letra">Telefone</td>
                <td class="center-align letra">Tiposague</td>
                <td class="center-align letra">Peso</td>
                <td class="center-align letra">Altura</td>
                <td class="center-align letra">Nascimento</td>
                <td class="center-align letra">Login</td>
                <td class="center-align letra">Alterar</td>
            </tr>
            
            
            <%   
            ArrayList<Usuario> listusuario = (ArrayList<Usuario>) request.getAttribute("lista");
            SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy");
            for (Usuario listu : listusuario){
            %>
                <tr>

                <td class="center-align"><%= listu.getNome() %></td>
                <td class="center-align"><%= listu.getCpf() %></td>
                <td class="center-align"><%= listu.getCelular() %></td>
                <td class="center-align"><%= listu.getTelefone() %></td>
                <td class="center-align"><%= listu.getTiposague() %></td>
                <td class="center-align"><%= listu.getPeso() %></td>
                <td class="center-align"><%= listu.getAltura() %></td>               
                <td class="center-align"><%= formatarDate.format(listu.getNascimento()) %></td>

                <td class="center-align">
                    <form action="../ControleUsuario" method="POST">

                        <input name="status" value="<%= listu.isStatus()%>" hidden>
                        <input type="text" name="id" value="<%= listu.getUsuario_id() %>" hidden/>

                        <% if (listu.isStatus() == true) { %>
                        <button class="btn waves-effect waves-teal red" type="submit" name="acao" value="Deletar">Excluir</button>
                        <% } else {%>
                        <button class="btn waves-effect waves-teal blue" type="submit" name="acao" value="Deletar">Ativar</button>     
                        <% }%>
                    </form> 
                </td>
                
                <td class="center-align">
                    <form action="../ControleUsuario" method="POST">
                        <button class="btn waves-effect waves-teal blue" type="submit" name="acao" value="Editar"><i class="material-icons">edit</i></button>
                        <input type="text" name="id" value="<%= listu.getUsuario_id() %>" hidden/>
                    </form>
                </td>

                </tr>
            <% } %>
        </table>

    </body>
</html>
