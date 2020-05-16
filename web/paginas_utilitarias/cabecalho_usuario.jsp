<%-- 
    Document   : Cabecalho
    Created on : 25/11/2018, 12:08:07
    Author     : victo
--%>

<%@page import="model.Funcionario"%>
<%@page import="model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Css da biblioteca MATERIALIZE -->
        <link href="framework/materialize/css/materialize.css" rel="stylesheet" type="text/css"/>
        <!-- Arquivo Css da bilitoteca materialize -->
        <link href="framework/materialize/css/materialize.min.css" rel="stylesheet" type="text/css"/>
        <!-- IMPORT ICONES PARA UTILIZAÇÃO-->
        <link href="framework/css/Css_Icones_Materialize/ImportICons.css" rel="stylesheet" type="text/css"/>

        <title>ICE</title>
    <nav>

        <script type="text/javascript">
            $(function () {
                $.mask.definitions['~'] = "[+-]";
                $("#date").mask("99/99/9999", {placeholder: "mm/dd/yyyy", completed: function () {
                        alert("completed!");
                    }});
                $("#celular").mask("(99) 99999-9999");
                $("#telefone").mask("9999-9999");
            });
        </script>
        
        
        
        <div class="nav-wrapper">
            <a class="brand-logo">ICE</a>
                 <%
                    //recupera o usuario da sessao
                     Funcionario funcionario = (Funcionario) session.getAttribute("usuarioAutenticado");
                                        if (funcionario != null) {

                %>
               <a class="brand-logo center white-text">Bem-vindo, <%= funcionario.getNome()%>!</a>
                    <%}%>
            <ul id="nav-mobile" class="right hide-on-med-and-down">
           
                <li><a href="paginas_adm/principal.jsp">Paginas Inicial</a></li>
                <li><a href="">Opção</a></li>
                <li><a href="ControleAcesso?acao=Sair">Sair</a></li>
            </ul>
        </div>
    </nav>

</head>

</html>
