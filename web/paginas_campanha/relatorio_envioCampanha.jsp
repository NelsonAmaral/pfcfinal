<%-- 
    Document   : crelatorio_envioCampanha
    Created on : 13/11/2019, 23:14:04
    Author     : victor aguiar
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.Cidade"%>
<%@page import="model.Campanha"%>
<%@page import="model.Usuario"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Funcionario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="/paginas_utilitarias/cabecalho.jsp" />


<%
    ArrayList<Usuario> listaUsuarios = (ArrayList<Usuario>) request.getAttribute("listUsuarios");

    ArrayList<Cidade> listaCidades = (ArrayList<Cidade>) request.getAttribute("cidades");
    
    Campanha campanha = (Campanha) request.getAttribute("campanha");

    //recupera o usuario da sessao
    Funcionario funcionario = (Funcionario) session.getAttribute("usuarioAutenticado");
    if (funcionario != null) {
        System.out.println("id " + funcionario.getId_funcionario());

    }
    
SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy");
%>


<!DOCTYPE html>
<main>
    <div class="container">
        <div class="row">
            <div class="card z-depth-0">
                <div class="card-content">
                    <span class="card-title titulo-tabela letra center-align">Relatório da Campanha <a><%= campanha.getCampanha_nome() %></a></span>

                    <!--card stats start-->
                    <div id="card-stats">
                        <div class="row">
                            <div class="col s12 m6 l3">
                                <div class="card">
                                    <div class="card-content cyan white-text">
                                        <p class="card-stats-title">
                                            <i class="material-icons">people</i>Nu. Usuario Atingidos</p>
                                        <h4 class="card-stats-number"></h4>
                                    </div>
                                    <div class="card-action cyan darken-1">
                                        <div id="clients-bar" class="center-align"><%= listaUsuarios.size() %> Usuarios</div>
                                    </div>
                                </div>
                            </div>
                            <div class="col s12 m6 l3">
                                <div class="card">
                                    <div class="card-content red accent-2 white-text">
                                        <p class="card-stats-title">
                                            <i class="material-icons">location_on</i>Total de cidades atingidas</p>
                                        <h4 class="card-stats-number"></h4>
                                    </div>
                                    <div class="card-action red darken-1">
                                        <div id="sales-compositebar" class="center-align"><%= listaCidades.size() %> Cidades</div>
                                    </div>
                                </div>
                            </div>
                            <div class="col s12 m6 l3">
                                <div class="card">
                                    <div class="card-content teal accent-4 white-text">
                                        <p class="card-stats-title">
                                            <i class="material-icons">colorize</i>Vacina da Campanha</p>
                                        <h4 class="card-stats-number"></h4>
                                    </div>
                                    <div class="card-action teal darken-1">
                                        <div id="profit-tristate" class="center-align"><%= campanha.getVacina().getNome() %></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col s12 m6 l3">
                                <div class="card">
                                    <div class="card-content deep-orange accent-2 white-text">
                                        <p class="card-stats-title">
                                            <i class="material-icons">Período</i>
                                        </p>
                                        <h4 class="card-stats-number"></h4>
                                    </div>
                                    <div class="card-action  deep-orange darken-1">
                                        <div id="invoice-line" class="center-align"><%= formatarDate.format(campanha.getCampanha_inicio()) %> / <%= formatarDate.format(campanha.getCampanha_prevista()) %></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    
                    <div class="row">
                        <div class="col s6">
                            <span class="card-title titulo-tabela letra center-align">Lista dos Usuarios</span>
                            
                            <center>
                                <div style="overflow: scroll; width: auto; height: 350px; border:solid 1px">
                            <table class="responsive-table">
                                <TR>
                                    <td class="center-align letra">Nome</td>
                                    <td class="center-align letra">Email</td>
                                    <td class="center-align letra">Cidade</td>
                                    <td class="center-align letra">Cep</td>
                                </TR>
                                
                                <% for (Usuario usuario : listaUsuarios){ %>
                                <TR>
                                    <td class="center-align"><%= usuario.getNome() %></td>
                                    <td class="center-align"><%= usuario.getEmail() %></td>
                                    <td class="center-align"><%= usuario.getEndereco().getCidade() %></td>
                                    <td class="center-align"><%= usuario.getEndereco().getCep() %></td>
                                </TR>
                                <%}%>
                            </table>
                                </div>
                            </center>
                        </div>
                            
                        <div class="col s6">
                            <span class="card-title titulo-tabela letra center-align">Lista das Cidades</span>
                            
                            <center>
                                <div style="overflow: scroll; width: auto; height: 350px; border:solid 1px">
                            <table class="responsive-table">
                                <TR>
                                    <td class="center-align letra">Cidade</td>
                                    <td class="center-align letra">Estado</td>
                                </TR>
                                
                                <% for (Cidade cidade : listaCidades){ %>
                                <TR>
                                    <td class="center-align"><%= cidade.getNome_cidade() %></td>
                                    <td class="center-align"><%= cidade.getEstado().getNome() %></td>
                                </TR>
                                <%}%>
                            </table>
                                </div>
                            </center>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
</body>

<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="/paginas_utilitarias/rodape.jsp" />

</html>