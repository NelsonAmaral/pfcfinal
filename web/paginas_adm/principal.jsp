<%-- 
    Document   : principal
    Created on : 02/11/2018, 23:03:54
    Author     : nelson_amaral
--%>

<%@page import="model.Funcionario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="../paginas_utilitarias/cabecalho_02.jsp" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Página Principal</title>
    </head>
    <body>
        
         <!--card stats start-->
        <div id="card-stats">
            <div class="row">
                <div class="col s12 m6 l3">
                    <div class="card">
                        <div class="card-content cyan white-text">
                            <p class="card-stats-title">
                                <i class="material-icons">person_outline</i> Total de Usuarios</p>
                            <h4 class="card-stats-number"></h4>
                        </div>
<!--                        <div class="card-action cyan darken-1">
                            <div id="clients-bar" class="center-align"></div>
                        </div>-->
                    </div>
                </div>
                <div class="col s12 m6 l3">
                    <div class="card">
                        <div class="card-content red accent-2 white-text">
                            <p class="card-stats-title">
                                <i class="material-icons">attach_money</i> Total de Vacinas Obrigatorias</p>
                            <h4 class="card-stats-number">#</h4>
<!--                            <p class="card-stats-compare">
                                <i class="material-icons">keyboard_arrow_up</i> 70%
                                <span class="red-text text-lighten-5">last month</span>
                            </p>-->
                        </div>
<!--                        <div class="card-action red darken-1">
                            <div id="sales-compositebar" class="center-align"></div>
                        </div>-->
                    </div>
                </div>
                <div class="col s12 m6 l3">
                    <div class="card">
                        <div class="card-content teal accent-4 white-text">
                            <p class="card-stats-title">
                                <i class="material-icons">trending_up</i> #</p>
                            <h4 class="card-stats-number">#</h4>
<!--                            <p class="card-stats-compare">
                                <i class="material-icons">keyboard_arrow_up</i> 80%
                                <span class="teal-text text-lighten-5">from yesterday</span>
                            </p>-->
                        </div>
<!--                        <div class="card-action teal darken-1">
                            <div id="profit-tristate" class="center-align"></div>
                        </div>-->
                    </div>
                </div>
                <div class="col s12 m6 l3">
                    <div class="card">
                        <div class="card-content deep-orange accent-2 white-text">
                            <p class="card-stats-title">
                                <i class="material-icons">content_copy</i> #</p>
                            <h4 class="card-stats-number">#</h4>
<!--                            <p class="card-stats-compare">
                                <i class="material-icons">keyboard_arrow_down</i> 3%
                                <span class="deep-orange-text text-lighten-5">from last month</span>
                            </p>-->
                        </div>
<!--                        <div class="card-action  deep-orange darken-1">
                            <div id="invoice-line" class="center-align"></div>
                        </div>-->
                    </div>
                </div>
            </div>
        </div>
          
    </body>
    <!-- IMPORTANDO RODAPE DA PAGINA -->
    <jsp:include page="../paginas_utilitarias/rodape_02.jsp" />
</html>
