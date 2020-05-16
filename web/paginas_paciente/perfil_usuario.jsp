<%-- 
    Document   : perfil_usuario
    Created on : 10/11/2019, 13:56:42
    Author     : Victor_Aguiar
--%>

<%@page import="model.Restricao"%>
<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="../paginas_utilitarias/cabecalho_03.jsp" />


<%
    Usuario usuario = (Usuario) request.getAttribute("usuario");
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

                        <span class="card-title titulo-tabela letra center-align">Perfil do Usuario</span>

                        <li class="divider black"></li> 

                        <div class="row">
                            <div class="col s12">
                                <ul class="tabs">
                                    <li class="tab col s4 letra"><a class="active" href="#test1">Dados Pessoais / Senha</a></li>
                                    <li class="tab col s4 letra"><a href="#test2">Endereço</a></li>
                                    <li class="tab col s4 letra"><a href="#test3">Restrições Pessoais</a></li>
                                </ul>
                            </div>

                            <div id="test1" class="col s12">

                                <!-- Modal Para Alterar Senha do Usuario -->
                                <div id="modal1" class="modal">
                                    <div class="modal-content">
                                        <div class="col s2"></div>
                                        <div class="col s8">

                                            <div class="card z-depth-2">
                                                <div class="card-content">

                                                    <span class="card-title titulo-tabela letra left">Alterar Senha</span>

                                                    <form action="ControleUsuario" method="POST">

                                                        <div class="row">
                                                            <div class="col s12 input-field">
                                                                <input type="password" name="txtsenhaatual" placeholder="Digite a senha atual" id="txtSenhaAtual" class="hoverable validate black-text" size="50" maxlength=50 required>
                                                                <label class="black-text" for="txtSenhaatual">Senha Atual<i class="material-icons left">account_box</i></label>                                   
                                                            </div>

                                                            <div class="col s12 input-field">
                                                                <input type="password" name="txtSenha" placeholder="Digite a nova senha" id="txtSenha" onchange="verifica()" class="hoverable validate black-text" size="50" maxlength=50 required>
                                                                <label class="black-text" for="txtSenha">Senha<i class="material-icons left">account_box</i></label>                                   
                                                            </div>

                                                            <div class="col s12 input-field">
                                                                <input type="password" name="txtnovasenha" placeholder="Digite novamente a nova senha" id="txtSenhaNovamente" onchange="confirmaSenha()" class="hoverable validate black-text" size="50" maxlength=50 required>
                                                                <label class="black-text" for="txtSenhaNovamente">Senha novamente<i class="material-icons left">account_box</i></label>                                   
                                                            </div>

                                                            <div class='col s6 left'>
                                                                <a id="mostraresultadosenha"></a>
                                                            </div>

                                                        </div>
                                                        <input name="txtId" value="<%= usuario.getUsuario_id()%>" hidden>
                                                        <input name="txtRg" value="<%= usuario.getRg()%>" hidden>
                                                        <input name="acao" value="AtualizarSenha" hidden>

                                                        <div class="row">
                                                            <div class="modal-footer">
                                                                <button type="submit" class="btn left"><li class="material-icons btn-small">sync</li></button>

                                                                <a href="#!" class="modal-close btn waves-effect waves-green btn-flat">Voltar</a>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                                <!-- Modal Para Criar Primeiro Acesso Usuario -->
                                <div id="modal2" class="modal">
                                    <div class="modal-content">
                                        <div class="col s2"></div>
                                        <div class="col s8">

                                            <div class="card z-depth-2">
                                                <div class="card-content">

                                                    <span class="card-title titulo-tabela letra left">Óla, vamos criar seu primeiro acesso!</span>

                                                    <form action="ControleUsuario" method="POST">

                                                        <div class="row">
                                                            <div class="col s12 input-field">
                                                                <input type="text" name="txtCPF" id="txtCPF" class="validate cpfMaskara" size="50" maxlength=50 required>
                                                                <label class="black-text" for="txtCPF">CPF<i class="material-icons left">account_box</i></label>                                   
                                                            </div>
                                                        </div>

                                                        <div class="row">
                                                            <div class="col s12 input-field" id="inputemail1">
                                                                <input type="email" name="txtEmail1" id="txtemailPrimerioAcesso" class="validate" placeholder="Máximo 50 caracteres" size="50" maxlength=50 required>
                                                                <label class="black-text" for="txtNome">Email<i class="material-icons left">account_box</i></label>                                   
                                                            </div>
                                                            <div class="col s12 input-field" id="inputemail2">
                                                                <input type="email" name="txtEmailnovamente" id="txtemailconfirmaPrimerioAcesso" onchange="confirmaEmailPrimerioAcesso()" class="validate" placeholder="Máximo 50 caracteres" size="50" maxlength=50 required>
                                                                <label class="black-text" for="txtNome">Confirmar Email<i class="material-icons left">account_box</i></label>                                   
                                                            </div>

                                                            <div class="col s12 input-field">
                                                                <input type="password" name="txtSenha1" placeholder="Digite a nova senha" id="txtSenhaPrimerioAcesso" onchange="verificaPrimerioAcesso()" class="hoverable validate black-text" size="50" maxlength=50 required>
                                                                <label class="black-text" for="txtSenha">Senha<i class="material-icons left">account_box</i></label>                                   

                                                                <div class="center-align" id="mostraresultadosenhaPrimerioAcesso"></div>
                                                            </div>

                                                            <div class="col s12 input-field">
                                                                <input type="password" name="txtnovasenha" placeholder="Digite novamente a nova senha" id="txtSenhaNovamentePrimerioAcesso" onchange="confirmaSenhaPrimerioAcesso()" class="hoverable validate black-text" size="50" maxlength=50 required>
                                                                <label class="black-text" for="txtSenhaNovamente">Senha novamente<i class="material-icons left">account_box</i></label>                                   
                                                            </div>





                                                        </div>
                                                        <input name="txtId" value="<%= usuario.getUsuario_id()%>" hidden>
                                                        <input name="txtNomeUsuario" value="<%= usuario.getNome()%>" hidden>
                                                        <input name="acao" value="CriarPrimeiroAcessoDependente" hidden>

                                                        <div class="row">
                                                            <div class="modal-footer">
                                                                <button type="submit" class="btn left"><li class="material-icons btn-small">sync</li></button>

                                                                <a href="#!" class="modal-close btn waves-effect waves-green btn-flat">Voltar</a>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <script>
                                    $(document).ready(function () {
                                        $('.modal').modal();
                                    });
                                </script>


                                <% String msg = (String) request.getAttribute("msg");
                                    if (msg != null) {%><center><a class="letra red-text"><%=msg%></a></center><%}%>


                                <div class="card z-depth-2">
                                    <div class="card-content">
                                        <div class="row">
                                            <div class="col s4">
                                                <div class="card z-depth-2">
                                                    <div class="card-content">

                                                        <span class="card-title titulo-tabela letra left">Inf. Pessoal</span>

                                                        <div class="row">
                                                            <div class="col s12 input-field">
                                                                <input type="text" name="txtNome" id="txtNome" class="hoverable validate black-text" value="<%= usuario.getNome()%>" size="50" maxlength=50 disabled>
                                                                <label class="black-text" for="txtNome">Nome<i class="material-icons left">account_box</i></label>                                   
                                                            </div>

                                                            <div class="col s12">
                                                                <label class="black-text left" for="txtDate">Data Nascimento<i class="material-icons left">perm_contact_calendar</i></label>                                                   

                                                                <input type="Date" onchange="calcularIdade()" name="txtDate" id="dataUsuario" value="<%= usuario.getNascimento()%>" class="validate black-text" disabled>
                                                            </div>

                                                            <div class="col s12 input-field">
                                                                <input type="text" name="txtCpf" id="txtCpf" class="validate black-text" value="<%= usuario.getCpf()%>" size="14" maxlength=14 disabled>
                                                                <label class="black-text" for="txtCpf">Cpf<i class="material-icons left">account_box</i></label>                                   
                                                            </div>

                                                            <div class="col s12 input-field">
                                                                <input type="text" name="txtRg" id="txtRg" class="validate black-text" value="<%= usuario.getRg()%>" size="9" maxlength=9 disabled>
                                                                <label class="black-text" for="txtRg">RG<i class="material-icons left">account_box</i></label>                                   
                                                            </div>

                                                            <div class="col s12 input-field">
                                                                <input type="text" name="txtsangue" id="txtSanguineo" class="validate black-text" value="<%= usuario.getTiposague()%>" size="9" maxlength=9 disabled>
                                                                <label class="black-text" for="txtsangue">Tipo Sanguíneo<i class="material-icons left">account_box</i></label>                                   
                                                            </div>

                                                            <% if (usuario.getResponsavel() == 0) {%>   
                                                            <div class="col s12">
                                                                <a class="letra">Alterar Senha:</a>
                                                                <button data-target="modal1" class="btn modal-trigger"><li class="material-icons">fingerprint</li></button>
                                                            </div>
                                                            <% } %>

                                                            <% if (usuario.getResponsavel() != 0 && usuario.getIdade() >= 18) {%>   
                                                            <div class="col s12">
                                                                <button data-target="modal2" class="btn modal-trigger">Criar Primeiro Acesso<li class="material-icons">fingerprint</li></button>
                                                            </div>
                                                            <% }%>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col s4">
                                                <div class="card z-depth-2">
                                                    <div class="card-content">
                                                        <div class="row">
                                                            <span class="card-title titulo-tabela letra left">Fisico</span>
                                                        </div>
                                                        <form action="ControleUsuario" method="POST">

                                                            <div class="row">

                                                                <div class="col s6 input-field">
                                                                    <input type="text" name="txtPeso" id="txtPeso" value="<%= usuario.getPeso()%>" onkeyup="validarAltuPeso()" class="validate" placeholder="0000" size="5" maxlength=5>
                                                                    <label class="black-text" for="txtPeso">Peso<i class="material-icons left">account_box</i></label>                                   
                                                                </div>

                                                                <div class="col s6 input-field">
                                                                    <input type="text" name="txtAltura" value="<%= usuario.getAltura()%>" class="validate" placeholder="0000" size="5" maxlength=5>
                                                                    <label class="black-text" for="txtPeso">Altura Usuario</label>                                   
                                                                </div> 

                                                                <script>
                                                                    function validarAltuPeso() {
                                                                        let peso = document.getElementById("txtPeso").value;
                                                                        let altura = document.getElementById("txtAtura").value;

                                                                        if (peso > 300.00 && peso != 0) {
                                                                            alert("Peso exorbitante!");
                                                                            document.getElementById("txtPeso").value = "";
                                                                        }

                                                                        if (altura > 2.51 && peso != 0) {
                                                                            alert("Altura exorbitante!");
                                                                            document.getElementById("txtAtura").value = "";
                                                                        }
                                                                    }
                                                                </script>
                                                            </div>

                                                            <input name="id_usuario" value="<%= usuario.getUsuario_id()%>" hidden>
                                                            <input name="acao" value="AtualizarDadosUsuario" hidden>
                                                            <input name="update" value="fisico" hidden>  

                                                            <div class="row">
                                                                <div class="center-align">
                                                                    <button type="submit" class="btn-floating"><li class="material-icons">sync</li></button>
                                                                </div>
                                                            </div>

                                                        </form>
                                                    </div>
                                                </div>
                                            </div>

                                            <% if (usuario.getResponsavel() == 0) {%>                

                                            <div class="col s4">
                                                <div class="row">
                                                    <div class="card z-depth-2">
                                                        <div class="card-content">
                                                            <span class="card-title titulo-tabela letra left">Contato</span>

                                                            <form action="" method="POST">
                                                                <div class="col s12 input-field">
                                                                    <input type="text" name="txtTelefone" value="<%= usuario.getTelefone()%>" class="validate telefone" required>
                                                                    <label class="black-text" for="txtTelefone">Telefone<i class="material-icons left">phone</i></label>                                   
                                                                </div>
                                                                <div class="col s12 input-field">
                                                                    <input type="text" name="txtCelular" value="<%= usuario.getCelular()%>" class="celular validate" required>
                                                                    <label class="black-text" for="txtCelular">Celular<i class="material-icons left">phone</i></label>                                   
                                                                </div>

                                                                <div class="row">

                                                                    <div class="col s12 input-field">
                                                                        <input type="email" name="txtEmail" id="txtemail" class="validate" value="<%= usuario.getEmail()%>" placeholder="Máximo 50 caracteres" size="50" maxlength=50 required>
                                                                        <label class="black-text" for="txtNome">Email<i class="material-icons left">account_box</i></label>                                   
                                                                    </div>
                                                                </div>
                                                                <input name="id_usuario" value="<%= usuario.getUsuario_id()%>" hidden>
                                                                <input name="acao" value="AtualizarDadosUsuario" hidden>
                                                                <input name="update" value="contato" hidden>  

                                                                <div class="row">
                                                                    <div class="center-align">
                                                                        <button type="submit" class="btn-floating"><li class="material-icons">sync</li></button>
                                                                    </div>
                                                                </div>
                                                            </form>     
                                                        </div>
                                                    </div>

                                                </div>  

                                                <% }%>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div id="test2" class="col s12">
                                <br>
                                <div class="col s3"></div>
                                <div class="col s6">
                                    <div class="card z-depth-2">
                                        <div class="card-content">

                                            <div class="row">
                                                <span class="card-title titulo-tabela center-align left letra">Endereço</span>
                                            </div>

                                            <a id="erro"></a>

                                            <form action="ControleEndereco" method="POST">

                                                <div class="row">
                                                    <div class="col s3 input-field">
                                                        <input type="text" name="txtCep" id="txtCep" onkeyup="somenteNumeros(this);" onchange="cep()" class="validate" value="<%= usuario.getEndereco().getCep()%>" placeholder="00000-000" size="12" maxlength=12 required>
                                                        <label class="black-text" for="txtCep">Cep<i class="material-icons left">assignment</i></label>                                   
                                                    </div>

                                                    <div class="col s9 input-field">
                                                        <input type="text" name="txtLog" id="txtLog" class="validate" value="<%= usuario.getEndereco().getLogradouro()%>" placeholder="Máximo 50 caracteres" size="50" maxlength=50 readonly required>
                                                        <label class="black-text" for="txtLog">Logradouro<i class="material-icons left">hotel</i></label>                                    
                                                    </div>

                                                </div>
                                                <div class="row">                                                
                                                    <div class='col s3'>
                                                        <label class="black-text left-align" for="txtUf">UF<i class="material-icons left">assignment</i></label>
                                                        <input type="text" name="txtUf" id="uf" class="hoverable validate" value="<%= usuario.getEndereco().getUf()%>" placeholder="UF" size="50" maxlength=50 readonly required>                                                   
                                                    </div>

                                                    <div class="col s4 input-field">
                                                        <input type="text" name="txtCidade" id="txtCidade" value="<%= usuario.getEndereco().getCidade()%>" class="validate" placeholder="Máximo 30 caracteres" size="30" maxlength=30 readonly required>
                                                        <label class="black-text" for="txtCidade">Cidade<i class="material-icons left">assignment</i></label>                                   
                                                    </div>


                                                    <div class="col s5 input-field">
                                                        <input type="text" name="txtBairro" id="Bairro" value="<%= usuario.getEndereco().getBairro()%>" class="validate" placeholder="Máximo 30 caracteres" size="30" maxlength=30 readonly required>
                                                        <label class="black-text" for="txtBairro">Bairro<i class="material-icons left">assignment</i></label>                                   
                                                    </div>

                                                </div>

                                                <div class="row">
                                                    <div class="col s2 input-field">
                                                        <input type="text" name="txtNumero" id="txtNumero" onkeyup="somenteNumeros(this)" value="<%= usuario.getEndereco().getNumero()%>" class="validate" placeholder="000" size="8" maxlength=8 required>
                                                        <label class="black-text" for="txtNumero">Numero<i class="material-icons left"></i></label>                                   
                                                    </div>                                     
                                                    <div class="col s9 input-field">
                                                        <input type="text" name="txtComplemento" value="<%= usuario.getEndereco().getComplemento()%>" id="txtComplemento" class="validate" placeholder="Máximo 54 caracteres" size="54" maxlength=54 required>
                                                        <label class="black-text" for="txtComplemento">Complemento<i class="material-icons left">assignment</i></label>                                   
                                                    </div>
                                                </div>

                                                <!-- Enviando dados ocultos para controler -->
                                                <input name="acao" value="Atualizar" hidden>
                                                <input name="id_usuario" value="<%= usuario.getUsuario_id()%>" hidden>

                                                <div class="row">
                                                    <div class="center-align">
                                                        <button type="submit" class="btn-floating"><li class="material-icons">sync</li></button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div id="test3" class="col s12">
                                <br>
                                <%
                                    ArrayList<Restricao> listaRestricaoUsuario = (ArrayList<Restricao>) request.getAttribute("restricaousuario");
                                    ArrayList<Restricao> listaRestricao = (ArrayList<Restricao>) request.getAttribute("restricoes");
                                %>

                                <!-- Modal Structure -->
                                <div id="modalAddRestricoes" class="modal">
                                    <div class="modal-content">
                                        <form action="ControleUsuario" method="POST">

                                            <div class="row">
                                                <span class="card-title titulo-tabela center-align left letra">Adicionar nova Restrições</span>
                                            </div>
                                            <center>
                                                <div style="overflow: scroll; width: auto; height: 300px; border:solid 1px">
                                                    <table class="responsive-table">
                                                        <TR>
                                                            <td class="center-align letra">Checkbox</td>
                                                            <td class="center-align letra">Restricão</td>
                                                            <td class="center-align letra">Descrição</td>
                                                        </TR>

                                                        <% for (Restricao lr : listaRestricao) {

                                                                boolean igual = false;
                                                                for (Restricao lv : listaRestricaoUsuario) {
                                                                    if (lr.getRestricao_id() == lv.getRestricao_id()) {
                                                                        igual = true;
                                                                        break;
                                                                    }
                                                                }

                                                                if (igual == false) {
                                                        %>


                                                        <TR>
                                                            <td class="center-align"><input type='checkbox' name="txtRestricaoFK" value="<%=lr.getRestricao_id()%>"></td>
                                                            <td class="center-align"><%=lr.getRestricao_nome()%></td>
                                                            <td name="tipo" class="center-align"><a href="#" onclick="tiporestricao('<%=lr.getRestricao_tipo()%>')"><i class="material-icons">chat</i></a></td>

                                                        </TR>
                                                        <% }
                                                            }%>

                                                    </table>
                                                </div>
                                            </center>

                                            <div class="modal-footer">
                                                <input name="usuario_id" value="<%= usuario.getUsuario_id()%>" hidden>
                                                <input name="acao" value="CadastraRestricaoUsuario" hidden>
                                                <button type="submit" class="btn red center-align left modal-close">Adicionar</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                                <div class="col s3"></div>
                                <div class="col s6">                 
                                    <div class="container">
                                        <div class="row">            
                                            <div class="card z-depth-2">
                                                <div class="card-content">

                                                    <span class="card-title titulo-tabela center-align left letra">Retrições do Usuario</span>

                                                    <table class="responsive-table">
                                                        <tr>
                                                            <td class="center-align letra">Nome</td>  
                                                            <td class="center-align letra">Descrição</td> 
                                                            <td class="center-align letra">Deletar</td> 

                                                        </tr>

                                                        <%for (Restricao r : listaRestricaoUsuario) {%>

                                                        <tr>
                                                            <td class="center-align"><%=r.getRestricao_nome()%></td>
                                                            <td name="tipo" class="center-align"><a href="#" onclick="tiporestricao('<%=r.getRestricao_tipo()%>')"><i class="material-icons">chat</i></a></td>

                                                            <td class="center-align">
                                                                <form action="ControleUsuario" method="POST">
                                                                    <input name="id_restricao" value="<%=r.getRestricao_id()%>" hidden/>
                                                                    <input name="usuario_id" value="<%= usuario.getUsuario_id()%>" hidden>
                                                                    <button type="submit" name="acao" value="DeletarRestricaoUsuario"><i class="material-icons red-text">delete</i></button>
                                                                </form>
                                                            </td>
                                                        </tr>

                                                        <%}%> 

                                                    </table>

                                                    <div class="container">
                                                        <div class="row">
                                                            <div class="center-align">
                                                                <!-- Modal Trigger -->
                                                                <a href="#modalAddRestricoes" class="waves-effect waves-light btn modal-trigger" >Cadastrar Restricão</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
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

<script>
    function tiporestricao(descricao) {
        alert(descricao);
    }
</script>

<!-- Funções Javascript -->
<script src="framework/js/Cadastro_Usuario/Funcoes.js" type="text/javascript"></script>
<script src="framework/js/Perfil_Usuario/Funcoes.js" type="text/javascript"></script>

<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="../paginas_utilitarias/rodape.jsp" />