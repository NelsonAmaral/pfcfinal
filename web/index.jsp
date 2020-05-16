<%-- 
    Document   : index
    Created on : 01/11/2018, 20:26:42
    Author     : nelson_amaral
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="msapplication-tap-highlight" content="no">
        <title>ICE-Imunização</title> 

        <!-- Css da  biblioteca materialize .min.css-->
        <link href="framework/css/themes/fixed-menu/materialize.min.css" rel="stylesheet" type="text/css"/>
        
        <link href="framework/css/themes/fixed-menu/materialize.css" rel="stylesheet" type="text/css"/>
        
        <!-- Style da biblitoteca materialize -->
        <link href="framework/css/Paginas_index/style.min.css" rel="stylesheet" type="text/css"/>

        <!-- IMPORT ICONES PARA UTILIZAÇÃO-->
        <link href="framework/css/Css_Icones_Materialize/ImportICons.css" rel="stylesheet" type="text/css"/>

        <!-- Custome CSS Page-Center-->    
        <link href="framework/css/Paginas_index/page-center.css" rel="stylesheet" type="text/css"/>
        
        <!-- Css exclusivo para pagina de login -->
        <link href="framework/css/Paginas_index/css_index.css" rel="stylesheet" type="text/css"/>
        
        <!-- Css exclusivo para  -->
        <link href="framework/css/css_letras.css" rel="stylesheet" type="text/css"/>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Autenticação</title>
    </head>
    
    <style type="text/css">

#popup {


background: #fff;

filter:Alpha(opacity=30);

}
    </style>
    
    <body class="img">     
        <div id="login-page" class="row">
            <div class="col s12 z-depth-4 card-panel" style="background-color: rgba(123, 123, 123, 0.5)">    
                <div class="row">
                    <div class="input-field col s12 center">
                        <img src="framework/Imagens/logo.png" alt="" class="circle responsive-img valign profile-image-login"/>
                        <p class="center login-form-text "></p>
                    </div>
                </div> 

                <%
                    String msg = (String) request.getAttribute("msg");
                    if (msg != null) {
                %>

                <div class="row center-align">
                    <h6 class="letra" style="color: #D50000;">AVISO</h6><h6 class="letra" style="color: #ffea00;"><%=msg %></h6>
                </div>
                <%}%>

                <form class="login-form" action="ControleAcesso" method="POST">
                    <div class="row margin">           
                        <div class="input-field col s12">
                            <i class="material-icons mdi-social-person-outline prefix">perm_identity</i>
                            <input id="rg" name='txtRg' placeholder="Digite seu Rg / Confen" type="text" size="12" maxlength=12>
                            <label for="rg" class="center-align">RG / Confen</label>
                        </div>
                    </div>
                    <div class="row margin">
                        <div class="input-field col s12">
                            <i class="material-icons mdi-action-lock-outline prefix">lock_outline</i>
                            <input id="password" name='txtSenha' placeholder="Digite sua senha" type="password" size="20" maxlength=20>
                            <label for="password">Senha</label>
                        </div>
                    </div>               
                    <div class="row">
                        <div class="input-field col s12">
                            <input type="submit" value="Acesso" name="acao" class="btn waves-effect waves-light col s12">
                        </div>
                    </div>                   
                    <div class="row">        
                        <div class="input-field col s6 m6 l6">
                            <p class="margin right-align medium-small"><a href="cadastro_usuario_index.jsp">Cadastrar-se</a></p>
                        </div>          
                    </div>                    
                </form>
            </div>
        </div>
    </body>




    <!-- jQuery Library -->
    <script src="framework/js/jquery-3.2.1.min.js" type="text/javascript"></script>
    <!--materialize js-->
    <script src="framework/js/materialize.js" type="text/javascript"></script>
    <!--prism-->
    <script src="framework/vendors/prism/prism.js" type="text/javascript"></script>
    <!--scrollbar-->
    <script src="framework/vendors/perfect-scrollbar/perfect-scrollbar.min.js" type="text/javascript"></script>
    <!--plugins.js -->
    <script src="framework/js/plugins.js" type="text/javascript"></script>
    <!--custom-script.js -->
    <script src="framework/js/min/custom-min.js" type="text/javascript"></script>
</html>
