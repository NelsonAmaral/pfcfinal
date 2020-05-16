<%-- 
    Document   : exibir_usuario
    Created on : 25/11/2018, 05:52:21
    Author     : nelson_amaral
--%>
<%@page import="model.Funcionario"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="../paginas_utilitarias/cabecalho.jsp" />



  <!-- Modal Structure -->
  <div id="modal1" class="modal">
    <div class="modal-content">
        <center><h5 class="card-title titulo-tabela letra">Funcionarios Desativados</h5></center>
     <center><div style="overflow: scroll; width: auto; height: 350px; border:solid 1px">

                            <table class="responsive-table">
                                <tr>
                                    <td class="center-align letra">Nome</td>  
                                    <td class="center-align letra">Confen</td>
                                    <td class="center-align letra">Rg</td>
                                    <td class="center-align letra">Posto</td>
                                    <td class="center-align letra">Ativar</td>
                                </tr>
                                <%
                                    ArrayList<Funcionario> listaFuncionarioDesativados = (ArrayList<Funcionario>) request.getAttribute("listaFuncionarioDesativados");
                                    for (Funcionario funcdesativados : listaFuncionarioDesativados) {
                                %>

                                <tr>

                                    <td class="center-align" name="txtNome"/><%=funcdesativados.getNome()%></td>
                                    <td class="center-align"/><%=funcdesativados.getCofen()%></td>
                                    <td class="center-align"/><%=funcdesativados.getRg()%></td>
                                    <td class="center-align"/><%=funcdesativados.getPosto().getPosto_nome()%></td>
                                    
                                    <td class="center-align">

                                        <form action="ControleFuncionario" method="POST">
                                            <button class="btn waves-effect waves-teal blue" type="submit" name="acao" value="AtivarLoginFuncionario"><i class="material-icons">edit</i></button>
                                            <input type="text" name="id_funcionario" value="<%=funcdesativados.getId_funcionario()%>" hidden/>
                                        </form>
                                    </td>
                                    <%}%>

                                </tr>
                            </table>
                        </div></center>
    </div>
    <div class="modal-footer">
      <a href="#!" class="modal-close waves-effect waves-green btn-flat">Voltar</a>
    </div>
  </div>

  <script>
       $(document).ready(function(){
    $('.modal').modal();
  });
      
  </script>
  
<main>
    <div class="container">
        <div class="row">            
            <div class="card z-depth-2">
                <div class="card-content">
                    <span class="card-title titulo-tabela center-align">Tabela Funcionario</span>
                  
                    <% String msg = (String) request.getAttribute("msg"); if (msg != null) {%><center><a class="letra red-text"><%=msg%></a></center><%}%>
                   
                    <center><div style="overflow: scroll; width: auto; height: 350px; border:solid 1px">

                            <table class="responsive-table">
                                <tr>
                                    <td class="center-align letra">Nome</td>  
                                    <td class="center-align letra">Confen</td>
                                    <td class="center-align letra">Perfil</td>
                                    <td class="center-align letra">Posto</td>
                                    <td class="center-align letra">Operação</td>
                                </tr>
                                <%
                                    ArrayList<Funcionario> listaFuncionario = (ArrayList<Funcionario>) request.getAttribute("lista");
                                    for (Funcionario f : listaFuncionario) {
                                %>

                                <tr>

                                    <td class="center-align" name="txtNome"value="<%=f.getNome()%>"/><%=f.getNome()%></td>
                                    <td class="center-align"name="txtCofen"value="<%=f.getCofen()%>"><%=f.getCofen()%></td>
                                    <td class="center-align"name="txtPerfil" value="<%=f.getPerfil()%>"><%=f.getPerfil()%></td>
                                    <td class="center-align"/><%=f.getPosto().getPosto_nome()%></td>


                                    <td class="center-align">

                                        <form action="ControleFuncionario" method="POST">
                                            <button class="btn waves-effect waves-teal red" type="submit" name="acao" value="Deletar"><i class="material-icons">delete</i></button>
                                            <button class="btn waves-effect waves-teal blue" type="submit" name="acao" value="Editar"><i class="material-icons">edit</i></button>
                                                <input type="text" name="txtNome"value="<%=f.getNome()%>" hidden/>
                                                <input type="text" name="txtCofen"value="<%=f.getCofen()%>" hidden/>
                                                <input type="text" name="txtPerfil" value="<%=f.getPerfil()%>" hidden/>
                                            <input type="text" name="id_funcionario" value="<%=f.getId_funcionario()%>" hidden/>
                                        </form>
                                    </td>
                                    <%}%>

                                </tr>
                            </table>
                        </div></center>
                    <br>
                    <div class="container">                                            
                        <div class="row">
                            <form action="ControleAcesso" method="POST">
                                <input value="Voltar" name="acao" hidden>
                                <button class="btn right" type="submit">Voltar</button>
                            </form>

                            <center><a data-target="modal1" class="btn modal-trigger right red">Desativados</a></center>
                            
                            <a class="btn red left" href="ControleFuncionario?acao=Cadastrar"><li class="material-icons">add</li></a>
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
</body>

<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="../paginas_utilitarias/rodape.jsp" />
</html>
