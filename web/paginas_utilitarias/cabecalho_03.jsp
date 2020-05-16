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
        <!-- CORE CSS-->
        <link href="framework/css/themes/fixed-menu/materialize.css" rel="stylesheet" type="text/css"/>
        <link href="framework/css/themes/fixed-menu/style.css" rel="stylesheet" type="text/css"/>
        <!-- Custome CSS-->
        <link href="framework/css/custom/custom.css" rel="stylesheet" type="text/css"/>
        <!-- IMPORTANDO BIBLIOTECA DE ICONES -->
        <link href="framework/css/Css_Icones_Materialize/ImportICons.css" rel="stylesheet" type="text/css"/>
        <!-- JS para funcionar Modais JQUERY 1.9.0 -->
        <script src="framework/js/jQuery.js" type="text/javascript"></script>
        <!-- Biblioteca da mascara dos inputs-->
        <script src="framework/jquery.maskedinput-master/src/jquery.maskedinput.js" type="text/javascript"></script>
        <!-- Css de letras-->
        <link href="framework/css/css_letras.css" rel="stylesheet" type="text/css"/>
        <title>ICE</title>
    <body>

        <script type="text/javascript">
            $(function () {
                $("#data").mask("9999-99-99");
                $(".celular").mask("(99) 99999-9999");
                $(".telefone").mask("(99) 9999-9999");
                $(".cpfMaskara").mask("999.999.999.99");
                $(".rgMaskara").mask("99.999.999.9", {maxlength: false});
                $(".alturamaskara").mask("9.99");
                $(".cepmaskara").mask("99999999");
            });
        </script>


        <%
            //recupera o usuario da sessao
            Usuario usuario = (Usuario) session.getAttribute("usuarioAutenticado"); %>


        <!-- Start Page Loading -->
        <div id="loader-wrapper">
            <div id="loader"></div>
            <div class="loader-section section-left"></div>
            <div class="loader-section section-right"></div>
        </div>
        <!-- End Page Loading -->
        <!-- START HEADER -->
        <header id="header" class="page-topbar">
            <!-- start header nav-->
            <div class="navbar-fixed">
                <nav class="navbar-color">
                    <div class="nav-wrapper">
                        <ul class="left">
                            <li>
                                <h1 class="logo-wrapper">
                                    <a href="index-2.html" class="brand-logo darken-1">
                                        <span class="logo-text hide-on-med-and-down">ICE</span>
                                    </a>
                                </h1>
                            </li>
                        </ul>
                        <div class="header-search-wrapper hide-on-med-and-down">
                            <i class="material-icons">search</i>
                            <input type="text" name="Search" class="header-search-input z-depth-2" placeholder="Indisponivel" />
                        </div>
                        <ul class="right hide-on-med-and-down">
                            <li>
                                <a href="javascript:void(0);" class="waves-effect waves-block waves-light translation-button" data-activates="translation-dropdown">
                                    <span class="flag-icon flag-icon-gb"></span>
                                </a>
                            </li>

                            <% if (usuario.getResponsavel() != 0) {%>
                            <li>
                                <form action="ControleAcesso" method="POST">
                                    <center><a class="waves-effect waves-cyan black-text">
                                            <input type="text" value="<%= usuario.getResponsavel()%>" name="usuario_id" hidden/>
                                            <button type="submit" name="acao" class="waves-effect waves-light btn-small btn red pulse" title="Voltar ao usuario responsável" value="AcessoDependente"><li class="material-icons yellow-text">person</li></button>
                                        </a></center>
                                </form> 
                            </li>
                            <% } %>

                            <li>
                                <a href="javascript:void(0);" class="waves-effect waves-block waves-light toggle-fullscreen">
                                    <i class="material-icons">settings_overscan</i>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0);" class="waves-effect waves-block waves-light profile-button" data-activates="profile-dropdown">
                                    <span class="avatar-status avatar-online">
                                        <img src="framework/Imagens/logo.png" alt="avatar">
                                        <i></i>
                                    </span>
                                </a>
                            </li>
                            <li>
                                <a href="#" data-activates="chat-out" class="waves-effect waves-block waves-light chat-collapse">
                                    <i class="material-icons">format_indent_increase</i>
                                </a>
                            </li>
                        </ul>

                        <!-- profile-dropdown -->
                        <ul id="profile-dropdown" class="dropdown-content">
                            <li>
                                <a href="ControleAcesso?acao=Sair" class="grey-text text-darken-1">
                                    <i class="material-icons">keyboard_tab</i> Logout</a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div>
            <!-- end header nav-->
        </header>
        <!-- END HEADER -->
        <!-- START MAIN -->
        <div id="main">
            <!-- START WRAPPER -->
            <div class="wrapper">
                <!-- START LEFT SIDEBAR NAV-->
                <aside id="left-sidebar-nav">
                    <ul id="slide-out" class="side-nav fixed leftside-navigation">
                        <li class="user-details cyan darken-2">
                            <div class="row">
                                <div class="col col s4 m4 l4">
                                    <img src="framework/Imagens/logo.png" alt="" class="circle responsive-img valign profile-image cyan">
                                </div>
                                <div class="col col s8 m8 l8">
                                    <ul id="profile-dropdown-nav" class="dropdown-content">
                                        <li>
                                            <form action="ControleUsuario" method="POST">
                                                <a class="grey-text text-darken-1">
                                                    <input type="text" value="<%= usuario.getUsuario_id()%>" name="usuario_id" hidden/>
                                                    <input type="text" name="acao" value="PerfilUsuario" hidden/> 
                                                    <input type="submit" class="black-text" value="Perfil"><i class="material-icons blue-text">face</i>
                                                </a>
                                            </form>                                              
                                        </li>
                                        <li>
                                            <a href="help.jsp" class="grey-text text-darken-1">
                                                <i class="material-icons">live_help</i> Help</a>
                                        </li>
                                        <li class="divider"></li>
                                        <li>
                                            <a href="ControleAcesso?acao=Sair" class="grey-text text-darken-1">
                                                <i class="material-icons">keyboard_tab</i> Logout</a>
                                        </li>
                                    </ul>
                                    
                                    <% if (usuario != null) {%>
                                    <a class="btn-flat dropdown-button waves-effect waves-light white-text profile-btn" href="#" data-activates="profile-dropdown-nav"><%= usuario.getNome()%><i class="mdi-navigation-arrow-drop-down right"></i></a>
                                    <%}%>
                                    
                                    <p class="user-roal red-text">Usuario</p>
                                </div>
                            </div>
                        </li>
                        <li class="no-padding">
                            <ul class="collapsible" data-collapsible="accordion">
                                <li class="bold">
                                    <form action="ControleAcesso" method="POST">
                                        <a class="collapsible-header waves-effect waves-cyan active"><i class="material-icons blue-text">dashboard</i>
                                            <input type="text" value="<%= usuario.getUsuario_id()%>" name="usuario_id" hidden/>
                                            <input type="text" name="acao" value="AcessoDependente" hidden/> 
                                            <input type="submit" class="black-text" value="Inicio">
                                        </a>
                                    </form> 
                                </li>

                                <% if (usuario.getResponsavel() == 0) {%>

                                <li class="bold">
                                    <a class="collapsible-header waves-effect waves-cyan">
                                        <i class="material-icons blue-text">face</i>
                                        <span class="letra123 nav-text">Dependentes</span>
                                    </a>
                                    <div class="collapsible-body">
                                        <ul>
                                            <li>
                                                <form action="ControleUsuario" method="POST">
                                                    <a class="collapsible-header waves-effect waves-cyan black-text"><i class="material-icons blue-text">keyboard_arrow_right</i>
                                                        <input type="text" value="<%=usuario.getUsuario_id()%>" name="usuario_id" hidden/>
                                                        <input type="submit" name="acao" value="DependentesUsuario" /> 
                                                    </a>
                                                </form> 
                                            </li>
                                        </ul>
                                    </div>
                                </li>
                                <% }%>


                                <li class="bold">
                                    <a class="collapsible-header waves-effect waves-cyan">
                                        <i class="material-icons blue-text">web</i>
                                        <span class="nav-text letra123">Histório Vacinação</span>
                                    </a>
                                    <div class="collapsible-body">
                                        <ul>
                                            <li>
                                                 <form action="ControleCardenetaUsuario" method="POST">
                                                    <a class="collapsible-header waves-effect waves-cyan black-text"><i class="material-icons blue-text">keyboard_arrow_right</i>
                                                        <input type="text" value="<%=usuario.getUsuario_id()%>" name="usuario_id" hidden/>    
                                                        <input type="text" name="idenf" value="false" hidden/> 
                                                        <input type="submit" name="acao" value="ConsultarHistoricoUsuario" /> 
                                                    </a>
                                                </form> 
                                            </li>
                                        </ul>
                                    </div>
                                </li>
                                <li>
                                    <a target="_blank">
                                        <i class="material-icons blue-text">help_outline</i>
                                        <span class="nav-text letra123">Support</span>
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul> 

                    <a href="#" data-activates="slide-out" class="sidebar-collapse btn-floating btn-medium waves-effect waves-light hide-on-large-only">
                        <i class="material-icons">menu</i>
                    </a>
                </aside>
                <!-- END LEFT SIDEBAR NAV-->

