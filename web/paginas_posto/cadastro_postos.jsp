<%-- 
    Document   : cadastro_postos_hospitais
    Created on : 26/10/2019, 12:31:20
    Author     : nelson_amaral
--%>

<%@page import="model.Funcionario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="/paginas_utilitarias/cabecalho_02.jsp" />

<%

    //recupera o usuario da sessao
    Funcionario funcionario = (Funcionario) session.getAttribute("usuarioAutenticado");
    if (funcionario != null) {
        System.out.println("id " + funcionario.getId_funcionario());


%>
<%    String msg = (String) request.getAttribute("msg");
    if (msg != null) {
%><font color="red"><%=msg%></font>
<%}%>
<!DOCTYPE html>
<main>

    <div class="row">
        <div class="col s2">
        </div>
        <div class="col s8">            
            <div class="card z-depth-2">
                <div class="card-content">
                    <span class="card-title titulo-tabela letra center-align">Cadastro Postos</span>
                    <form action="../ControlePosto" method="POST" ><br/>
                        <input name="id_funcionrio" value="<%=funcionario.getId_funcionario()%>" hidden>
                        <% }%>
                        <div class="row">
                            <div class="col s6 input-field hoverable">
                                <input type="text" name="txtNome" id="txtNome" class="hoverable validate" placeholder="Máximo 50 caracteres" size="50" maxlength=50 required>
                                <label class="black-text" for="txtNome">Nome<i class="material-icons left">account_box</i></label>                                   
                            </div>

                            <div class="col s4 input-field hoverable">
                                <input type="text" name="txtTelefone" placeholder="(00) 0000-0000" class="hoverable validate telefone" required>
                                <label class="black-text" for="txtTelefone">Telefone<i class="material-icons left">phone</i></label>                                   
                            </div>
                        </div>

                        <div class="row">
                            <div class="col s12">
                                <div class="card z-depth-2">
                                    <div class="card-content">
                                        <h5 class="center-align"><i>Endereço</i></h5>

                                        <a id="erro"></a>

                                        <div class="row">
                                            <div class="col s3 input-field">
                                                <input type="text" name="txtCep" id="txtCep" onkeyup="somenteNumeros(this);" onchange="cep()" class="validate" placeholder="00000-000" size="12" maxlength=12 required>
                                                <label class="black-text" for="txtCep">Cep<i class="material-icons left">assignment</i></label>                                   
                                            </div>

                                            <div class="col s9 input-field">
                                                <input type="text" name="txtLog" id="txtLog" class="validate" placeholder="Máximo 50 caracteres" size="50" maxlength=50 readonly required>
                                                <label class="black-text" for="txtLog">Logradouro<i class="material-icons left">hotel</i></label>                                    
                                            </div>

                                        </div>
                                        <div class="row">                                                
                                            <div class='col s3'>
                                                <label class="black-text left-align" for="txtUf">UF<i class="material-icons left">assignment</i></label>
                                                <input type="text" name="txtUf" id="uf" class="hoverable validate" placeholder="UF" size="50" maxlength=50 readonly required>                                                   
                                            </div>

                                            <div class="col s4 input-field">
                                                <input type="text" name="txtCidade" id="txtCidade" class="validate" placeholder="Máximo 30 caracteres" size="30" maxlength=30 readonly required>
                                                <label class="black-text" for="txtCidade">Cidade<i class="material-icons left">assignment</i></label>                                   
                                            </div>


                                            <div class="col s5 input-field">
                                                <input type="text" name="txtBairro" id="Bairro" class="validate" placeholder="Máximo 30 caracteres" size="30" maxlength=30 readonly required>
                                                <label class="black-text" for="txtBairro">Bairro<i class="material-icons left">assignment</i></label>                                   
                                            </div>

                                        </div>


                                        <div class="row">
                                            <div class="col s2 input-field">
                                                <input type="text" name="txtNumero" onkeyup="somenteNumeros(this)" class="validate" placeholder="000" size="8" maxlength=8 required>
                                                <label class="black-text" for="txtNumero">Numero<i class="material-icons left"></i></label>                                   
                                            </div>                                     
                                            <div class="col s9 input-field">
                                                <input type="text" name="txtComplemento" id="txtComplemento" class="validate" placeholder="Máximo 54 caracteres" size="54" maxlength=54>
                                                <label class="black-text" for="txtComplemento">Complemento<i class="material-icons left">assignment</i></label>                                   
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row center-align">
                            <input class="btn waves-effect center-align" type="submit" value="Cadastrar" name="acao" >
                        </div>
                    </form>                     
                </div>
            </div>
        </div>
    </div>
</main>
</body>

<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="/paginas_utilitarias/rodape_02.jsp" />

<!-- Funcoes -->
<script src="../framework/js/Cadastro_Usuario/Funcoes.js" type="text/javascript"></script>
</html>