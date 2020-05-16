<%-- 
    Document   : principal
    Created on : 02/11/2018, 23:03:54
    Author     : nelson_amaral
--%>

<%@page import="model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="../paginas_utilitarias/cabecalho.jsp" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Página Principal</title>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <h3 class="center-align">Home Funcionario</h3>
                <div class="col s12">
                    <div class="card z-depth-2">
                        <div class="card-content">
                            <div class="row">
                                <div class="collection">
                                    <form action="../ControleUsuario" method="POST">
                                        <li><input type="submit" name="acao" value="Consultar Pacientes"></li>      
                                        <input type="hidden" name="id" value="${lista.id}"/></a>
                                    </form>
                                    <br>
                                    <form action="../ControleVacina" method="POST">
                                        <li><input name="acao" value="Consultar Vacinas" type="submit"></li>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <!-- IMPORTANDO RODAPE DA PAGINA -->
    <jsp:include page="../paginas_utilitarias/rodape.jsp" />
</html>
