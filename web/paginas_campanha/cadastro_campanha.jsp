<%-- 
    Document   : cadastro_campanha
    Created on : 27/10/2019, 19:23:21
    Author     : nelson_amaral
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page import="model.Vacina"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Funcionario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="/paginas_utilitarias/cabecalho.jsp" />


<script src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
<link href='https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css' rel='stylesheet' type='text/css'>


<%
    ArrayList<Vacina> listaVacina = (ArrayList<Vacina>) request.getAttribute("listaVacinas");

    //recupera o usuario da sessao
    Funcionario funcionario = (Funcionario) session.getAttribute("usuarioAutenticado");
    if (funcionario != null) {
        System.out.println("id " + funcionario.getId_funcionario());


%>

<!DOCTYPE html>
<main>
    <div class="container">
        <div class="row">

            <div class="card z-depth-0">
                <div class="card-content">
                    <span class="card-title titulo-tabela letra center-align">Cadastro Campanha</span>
                    <form action="ControleCampanha" method="POST" ><br/>
                        <input name="id_funcionrio" value="<%=funcionario.getId_funcionario()%>" hidden>
                        <% }%>
                        <div class="row">
                            <div class="col s5">
                                <div class="col s12 input-field hoverable">
                                    <input type="text" name="txtNome" id="txtNome" class="hoverable validate" placeholder="Máximo 50 caracteres" size="50" maxlength=50 required>
                                    <label class="black-text" for="txtNome">Nome<i class="material-icons left">account_box</i></label>                                   
                                </div>

                                <div class="col s12">
                                    <label class="black-text left" for="txtDateInicio">Data Inicio<i class="material-icons left">perm_contact_calendar</i></label>                                                   
                                    <br>
                                    <input type="Date" name="txtDateInicio" id="dataInicio" class="hoverable validate" required>
                                </div>
                                <div class="col s12">
                                    <label class="black-text left" for="txtDateFinal">Data Final<i class="material-icons left">perm_contact_calendar</i></label>                                                   
                                    <br>
                                    <input type="Date" name="txtDateFinal" onchange="validandocampos()" id="dataFinal" class="hoverable validate" required>
                                </div>
                                <div class="col s12">
                                    <label class="black-text left-align" for="txtVacina">Vacinas<i class="material-icons left">dehaze</i></label>
                                    <select id="vacinas" class="browser-default" name="txtVacinasFK" required>
                                        <option value="" disabled selected>Selecione...</option>
                                        <c:forEach var="vacina" items="${listaVacinas}">
                                            <option value="${vacina.id_vacina}">${vacina.nome} / ${vacina.tipo}</option>
                                        </c:forEach>
                                    </select>           
                                </div>
                            </div>

                            <div class="col s4 right">
                                <label class="black-text left-align" for="txtTiposague">Pesquisa por estado<i class="material-icons left">assignment</i></label>
                                <select class="browser-default" id="filtrar-estado" name="txtpesqestado">
                                    <option value="" disabled selected>Selecione...</option>
                                    <c:forEach var="Estado" items="${listaEstados}">
                                        <option value="${Estado.id}" selected><c:out value="${Estado.nome}"/></option> 
                                    </c:forEach>
                                </select>


                                <div class="col s12 right">
                                    <center><div class="right" style="overflow: scroll; width: 300px; height: 350px; border:solid 1px">
                                            <table id="example" class="display" cellspacing="0" width="100%">
                                                <TR>
                                                    <td class="center-align letra black-text">Checkbox<input type='checkbox' id='ckTodos'/></td>
                                                    <td class="center-align letra black-text">Cidades</td>
                                                </TR>
                                                <c:forEach var="Cidade" items="${listaCidade}">
                                                    <TR class="nome_cidade">
                                                        <td class="center-align"><input type="checkbox" name="chcidade" class="list-group marcar" value="${Cidade.id_cidade}"></td>
                                                        <td class="center-align"><c:out value="${Cidade.nome_cidade}"/></td>
                                                        <td class="center-align info-cidade" hidden><c:out value="${Cidade.id_estado_cidade}"/></td>
                                                    </TR>
                                                </c:forEach>
                                            </table>
                                        </div></center>
                                </div>
                            </div>
                        </div>

                        <script>
                            $(document).ready(function () {
                                $('#example').DataTable();

                                //JQUERY PARA MARCAR O CHECKBOX
                                $('#ckTodos').click(function () {
                                    var check = $(this).is(':checked');
                                    $('.marcar').each(function () {
                                        $(this).prop("checked", check);
                                    });
                                });
                            });
                        </script>

                        <!--Import jQuery before materialize.js-->                       
                        <div id="carregamento"></div>

                        <div class="row center-align">
                            <button type="submit" class="btn waves-effect center-align" onclick="carregamento()" name="acao" value="Criar">Criar Campanha</button>
                        </div>
                    </form>   
                </div>
            </div>
        </div>
    </div>
</main>
</body>

<script>
    function validandocampos(){
        let dataInicio = document.getElementById("dataInicio").value;
        let dataFinal = document.getElementById("dataFinal").value;
          if (dataInicio > dataFinal) {        
            alert("Data final da campanha é menor que a data de inicio!");
              document.getElementById("dataInicio").value = "";
              document.getElementById("dataFinal").value = "";
           }
    }
    
    function carregamento() {

        let nome = document.getElementById("txtNome").value;
        let dataInicio = document.getElementById("dataInicio").value;
        let dataFinal = document.getElementById("dataFinal").value;

      

            if (nome !== '' && dataInicio !== '' && dataFinal !== '') {

                document.getElementById("txtNome").setAttribute('readonly', true);
                document.getElementById("dataInicio").setAttribute('readonly', true);
                document.getElementById("dataFinal").setAttribute('readonly', true);
                document.getElementById("vacinas").setAttribute('readonly', true);

                document.getElementById("carregamento").innerHTML = '<div class="progress" hidden><div class="indeterminate"></div></div>';

            }
    }
</script>

<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="/paginas_utilitarias/rodape.jsp" />
<script src="framework/js/Filtro_Paginas/Filtro_cadastrarCampanha/Filtro_nomeestado.js" type="text/javascript"></script>
</html>