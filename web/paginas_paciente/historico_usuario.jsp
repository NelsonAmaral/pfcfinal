<%-- 
    Document   : historico_usuario
    Created on : 17/11/2019, 23:17:54
    Author     : Victor_Aguiar
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="model.CadernetaUsuario"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="../paginas_utilitarias/cabecalho_03.jsp" />

<%
    Usuario usuarios = (Usuario) session.getAttribute("usuarioAutenticado");

    ArrayList<CadernetaUsuario> listVacinasObr = (ArrayList<CadernetaUsuario>) request.getAttribute("listVacinasObrigatorias");

    ArrayList<CadernetaUsuario> listVacinasCampanha = (ArrayList<CadernetaUsuario>) request.getAttribute("listVacinasCampanhas");

    ArrayList<CadernetaUsuario> listVacinasVacinasUnicas = (ArrayList<CadernetaUsuario>) request.getAttribute("listVacinas");

 SimpleDateFormat formatardata = new SimpleDateFormat("dd/MM/yyyy");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="card z-depth-2">
                    <div class="card-content">

                        <div class="row">
                            <form action="ControleCardenetaUsuario" method="POST">
                                <div class="col s3 ">
                                    <label class="black-text" for="txtDataInicio">Data Inicio<i class="material-icons left">account_box</i></label>                                   

                                    <input type="date" name="txtDataInicio" id="txtDataInicio" class="hoverable validate input-field" required>
                                </div>
                                <div class="col s3 ">
                                    <label class="black-text" for="txtDataFinal">Data Final<i class="material-icons left">account_box</i></label>                                   

                                    <input type="date" name="txtDataFinal" onchange="validarcampo()" id="txtDataFinal" class="hoverable validate input-field" required>
                                </div>

                                <input type="text" value="true" name="idenf" hidden/> 
                                <input name="usuario_id" value="<%= usuarios.getUsuario_id()%>" hidden>
                                <input name="acao" value="ConsultarHistoricoUsuario" hidden>

                                <button class="btn"><i class="material-icons">search</i></button>

                            </form>
                            <span class="card-title titulo-tabela letra right center-align">Historico Usuario</span>
                        </div>

                                <script>
                                    function validarcampo(){
                                        let datainicio = document.getElementById("txtDataInicio").value;
                                        let datafinal = document.getElementById("txtDataFinal").value;
                                        
                                        if(datainicio > datafinal){
                                            alert("Data Inicio não pode ser maior que Data Final!");
                                            
                                            document.getElementById("txtDataInicio").value = "";
                                            document.getElementById("txtDataFinal").value = "";
                                        }
                                    }
                                </script>


                        <li class="divider black"></li> 
                        <div class="row">
                            <div class="col s12">
                                <ul class="tabs">
                                    <li class="tab col s3"><a href="#test1">Vacinas Obrigatórias</a></li>
                                    <li class="tab col s3"><a class="active" href="#test2">Vacinas de Campanhas</a></li>
                                    <li class="tab col s3"><a href="#test3">Vacinas Únicas</a></li>
                                </ul>
                            </div>


                            <div id="test1" class="col s12">
                                <center>
                                    <div style="overflow: scroll; width: auto; height: 350px; border:solid 1px">
                                        <table class="responsive-table">
                                            <tr>
                                                <td class="center-align letra">Nome Vacina</td>
                                                <td class="center-align letra">Tipo Vacina</td>
                                                <td class="center-align letra">Nome Funcionario</td>
                                                <td class="center-align letra">Confen Funcionario</td>
                                                <td class="center-align letra">Posto</td>
                                                <td class="center-align letra">Data</td>
                                                <td class="center-align letra">Hora</td>
                                            </tr>

                                            <% for (CadernetaUsuario listObr : listVacinasObr) {%>
                                            <tr>
                                                <td class="center-align"><%= listObr.getVacina().getNome()%></td>
                                                <td class="center-align"><%= listObr.getVacina().getTipo()%></td>
                                                <td class="center-align"><%= listObr.getUsuario().getNome()%></td>
                                                <td class="center-align"><%= listObr.getFuncionario().getCofen()%></td>
                                                <td class="center-align"><%= listObr.getPosto().getPosto_nome() %></td>
                                                <td class="center-align"><%= formatardata.format(listObr.getDateCadastro())%></td>
                                                <td class="center-align"><%= listObr.getHoraCadastro()%></td>
                                            </tr>
                                            <% } %>
                                        </table>
                                    </div>
                            </div>
                        </div>


                        <div id="test2" class="col s12">
                            <center>
                                <div style="overflow: scroll; width: auto; height: 350px; border:solid 1px">
                                    <table class="responsive-table">
                                        <tr>
                                            <td class="center-align letra">Nome Vacina</td>
                                            <td class="center-align letra">Tipo Vacina</td>
                                            <td class="center-align letra">Nome Funcionario</td>
                                            <td class="center-align letra">Confen Funcionario</td>
                                            <td class="center-align letra">Posto</td>
                                            <td class="center-align letra">Data</td>
                                            <td class="center-align letra">Hora</td>
                                        </tr>

                                        <% for (CadernetaUsuario listCamp : listVacinasCampanha) {%>
                                        <tr>
                                            <td class="center-align"><%= listCamp.getVacina().getNome()%></td>
                                            <td class="center-align"><%= listCamp.getVacina().getTipo()%></td>
                                            <td class="center-align"><%= listCamp.getUsuario().getNome()%></td>
                                            <td class="center-align"><%= listCamp.getFuncionario().getCofen()%></td>
                                            <td class="center-align"><%= listCamp.getPosto().getPosto_nome() %></td>
                                            <td class="center-align"><%= formatardata.format(listCamp.getDateCadastro())%></td>
                                            <td class="center-align"><%= listCamp.getHoraCadastro()%></td>
                                        </tr>
                                        <% } %>
                                    </table>

                                </div>
                            </center>
                        </div>
                        <div id="test3" class="col s12">
                            <center>
                                <div style="overflow: scroll; width: auto; height: 350px; border:solid 1px">
                                    <table class="responsive-table">
                                        <tr>
                                            <td class="center-align letra">Nome Vacina</td>
                                            <td class="center-align letra">Tipo Vacina</td>
                                            <td class="center-align letra">Nome Funcionario</td>
                                            <td class="center-align letra">Confen Funcionario</td>
                                            <td class="center-align letra">Posto</td>
                                            <td class="center-align letra">Data</td>
                                            <td class="center-align letra">Hora</td>
                                        </tr>

                                        <% for (CadernetaUsuario listVacin : listVacinasVacinasUnicas) {%>
                                        <tr>
                                            <td class="center-align"><%= listVacin.getVacina().getNome()%></td>
                                            <td class="center-align"><%= listVacin.getVacina().getTipo()%></td>
                                            <td class="center-align"><%= listVacin.getUsuario().getNome()%></td>
                                            <td class="center-align"><%= listVacin.getFuncionario().getCofen()%></td>
                                            <td class="center-align"><%= listVacin.getPosto().getPosto_nome() %></td>
                                            <td class="center-align"><%= formatardata.format(listVacin.getDateCadastro())%></td>
                                            <td class="center-align"><%= listVacin.getHoraCadastro()%></td>
                                        </tr>
                                        <% }%>
                                    </table>
                                </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="../paginas_utilitarias/rodape.jsp" />