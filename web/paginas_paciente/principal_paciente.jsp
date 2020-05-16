<%-- 
    Document   : home_comum
    Created on : 11/11/2018, 14:14:08
    Author     : nelson_amaral
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="../paginas_utilitarias/cabecalho_03.jsp" />

<%
    //recupera o usuario da sessao
    Usuario usuario = (Usuario) session.getAttribute("usuarioAutenticado");
    System.out.println("" + usuario.getUsuario_id());

    if (usuario != null);

%>

<!-- Css Único Do Calendario -->
<link href="framework/css/principal_paciente/calendario.css" rel="stylesheet" type="text/css"/>

<!-- Data atual -->
<%    
    Date data = new Date();

    SimpleDateFormat formatarDate = new SimpleDateFormat("MM-yyyy");
%>

<!-- Id para aux da busca para a lista de vacinas do mes do usuario -->
<input id="usuario_id" value="<%= usuario.getUsuario_id()%>" hidden>

<body>
    <div class="row">
        <div class="col s12">
            <div class="card z-depth-2">
                <div class="card-content">
                    <div class="col s4">

                        <!-- Input responsavel -->
                        <input id="nascimento" value="<%= request.getAttribute("datanascimento")%>"hidden>

                        <!-- Exibir a data -->
                        <a class="blue-text letra">Tempo de vida </a><div class="black-text letra" id="nascimentoA"></div>

                    </div>
                    <div class="col s7">
                        <div class="row">
                            <div class="col s2">
                                <a class="btn" onclick="dataanterior()"><i class="material-icons">fast_rewind</i></a>
                            </div>
                            <div class="col s3">
                                <center><input readonly class="center-align" id="datd" value="<%= formatarDate.format(data)%>"></center>
                            </div>
                            <div class="col s2">
                                <a class="btn" onclick="dataposterior()"><i class="material-icons">fast_forward</i></a>
                            </div>
                        </div>
                            
                    </div>
                    
                            <div class="row">
                                <div class="col s12">
                                    <a class="letra" id="LongDate"></a>
                                </div>
                            </div>   
                    
                    <!-- Div para exibir a tabela do calendario / InnerHTML --> 
                    <div id="CalendarioCarregado"></div>
                </div>
            </div>
        </div>
    </div>
</body>

<div class="container">
<div class="card z-depth-2 ">
    <div class="card-content">
        <div class="row">
            <div class="center-align">
                <h5 class="titulo-tabela letra center-align">Informações <a id="nomevacina"></a></h5>
            </div>
        </div>
        
        <div class="center-align">
        <div id="descricao"></div>
        </div>
    </div>
</div>
    </div>


<!-- Função para exibir a descricao da vacina para o usuario -->
<script>
    function descricao(descricao, vacina) {
        document.getElementById("descricao").innerHTML = descricao;
        document.getElementById("nomevacina").innerHTML = vacina;
    }
</script>

<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="../paginas_utilitarias/rodape.jsp" />
<!-- Busca Tabela Calendario-->
<script src="framework/js/Busca_Ajax/Busca_Calendario_Principal/Buscar-meses_calendario.js" type="text/javascript"></script>
<!-- Automação Botões calendario -->
<script src="framework/js/principal_paciente/calendario.js" type="text/javascript"></script>