<%-- 
    Document   : cadastro_usuario
    Created on : 01/11/2018, 20:33:51
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
    <div class="container">
        <div class="row">
            <div class="col s2">
            </div>
            <div class="col s8">            
                <div class="card z-depth-2">
                    <div class="card-content">
                        <span class="card-title titulo-tabela letra center-align">Cadastro Paciente</span>
                        <form action="../ControleUsuario" method="POST" ><br/>
                            <input name="id_funcionrio" value="<%=funcionario.getId_funcionario()%>" hidden>
                            <% }%>
                            <div class="row">
                                <div class="col s6 input-field">
                                    <input type="text" name="txtNome" id="txtNome" class="hoverable validate" placeholder="Máximo 50 caracteres" size="50" maxlength=50 required>
                                    <label class="black-text" for="txtNome">Nome<i class="material-icons left">account_box</i></label>                                   
                                </div>

                                <div class="col s3">
                                    <label class="black-text left" for="txtDate">Data Nascimento<i class="material-icons left">perm_contact_calendar</i></label>                                                   
                                    <br>
                                    <input type="Date" onchange="calcularIdade()" name="txtDate" id="dataUsuario" class="validate" required>
                                </div>

                                <!-- Campo oculto sera aliementado pela funcao de calcular a idade -->
                                <input type="text" name="txtIdade" id="txtIdade" class="validate" hidden>
                                    

                                <div id="id_responsavel" class="col s3 input-field hoverable">
                                    <input type="text" name="txtRgResponsavel" id="txtRgResponsavel" class="validate" placeholder="00.000.000-00" size="9" maxlength=9>
                                    <label class="black-text" for="txtRgResponsavel">Rg Responsavel<i class="material-icons left">account_box</i></label>                                   
                                </div>

                            </div>
                            <div class="row">
                                <div class="col s4 input-field" id="cpf1">
                                    <input type="text" name="txtCpf" id="txtCpf" class="validate cpfMaskara" placeholder="000.000.000-00" size="14" maxlength=14>
                                    <label class="black-text" for="txtCpf">Cpf<i class="material-icons left">account_box</i></label>                                   
                                
                                    <div id="informativoCpf"></div>
                                </div>

                                <div class="col s4 input-field">
                                    <input type="text" name="txtRg" id="txtRg" class="validate" placeholder="00.000.000-00" size="9" maxlength=9 required>
                                    <label class="black-text" for="txtRg">RG<i class="material-icons left">account_box</i></label>                                   
                                </div>

                                <div class="col s2 input-field">
                                    <input type="text" name="txtPeso"  id="txtPeso" class="validate mascarapeso" placeholder="0000" size="5" maxlength=5>
                                    <label class="black-text" for="txtPeso">Peso<i class="material-icons left">account_box</i></label>                                   
                                </div>    

                                <div class="col s2 input-field hoverable">
                                    <input type="text" name="txtAtura" id="txtAtura" pattern="[-+]?[0-9]*\.?[0-9]*" class="hoverable validate alturamaskara" placeholder="0.00" size="3" maxlength=3>
                                    <label class="black-text" for="txtAtura">Altura<i class="material-icons left">account_box</i></label>                                   
                                </div>                             
                            </div>

                            <div class="row">
                                <div class="col s3 input-field">
                                    <input type="text" name="txtTelefone" placeholder="(00) 0000-0000" class="validate telefone" required>
                                    <label class="black-text" for="txtTelefone">Telefone<i class="material-icons left">phone</i></label>                                   
                                </div>
                                <div class="col s3 input-field">
                                    <input type="text" name="txtCelular" placeholder="(00) 0000-0000" class="celular validate" required>
                                    <label class="black-text" for="txtCelular">Celular<i class="material-icons left">phone</i></label>                                   
                                </div>

                                <div class='col s3'>
                                    <label class="black-text left-align" for="txtTiposague">Tipo Sanguineo<i class="material-icons left">assignment</i></label>
                                    <select class="browser-default" name="txtTiposague">
                                        <option value="" disabled selected>Selecione...</option>
                                        <option value="AB+">AB+</option>
                                        <option value="AB-">AB-</option>
                                        <option value="A+">A+</option>
                                        <option value="A-">A-</option>
                                        <option value="O+">O+</option>
                                        <option value="O-">O-</option>
                                        <option value="B+">B+</option>
                                        <option value="B-">B-</option>
                                    </select>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col s6 input-field" id="inputemail1">
                                    <input type="email" name="txtEmail" id="txtemail" class="validate" placeholder="Máximo 50 caracteres" size="50" maxlength=50>
                                    <label class="black-text" for="txtNome">Email<i class="material-icons left">account_box</i></label>                                   
                                </div>
                                <div class="col s6 input-field" id="inputemail2">
                                    <input type="email" id="txtemailconfirma" onchange="confirmaEmail()" class="validate" placeholder="Máximo 50 caracteres" size="50" maxlength=50>
                                    <label class="black-text" for="txtNome">Confirmar Email<i class="material-icons left">account_box</i></label>                                   
                                </div>
                                
                                <div class="row">
                                    <div class="col s12">
                                        <center><a class="red-text letra123 center-align" id="informativoEmail"></a></center>
                                    </div>
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
                                                    
                                                    <input type="text" id="txtCep" onkeyup="somenteNumeros(this);" onchange="cep()" name="txtCep" class="validate" placeholder="00000-000" size="12" maxlength=12 required>
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

                            <div class="row">
                                <div class="col s12">
                                    <div class="card z-depth-2">
                                        <div class="card-content">
                                            <div class="col s6 input-field" id="inputsenha1">
                                                <input type="password" name="txtSenha" id="txtSenha" onchange="verifica()" class="validate inputsenha1" placeholder="Senha" size="30" maxlength=30>
                                                <label class="black-text" for="txtSenha">Digite a senha<i class="material-icons left">vpn_key</i></label>                                   
                                            </div>

                                            <div class="col s6 input-field" id="inputsenha2">
                                                <input type="password" name="txtSenha" id="txtSenhaNovamente" onchange="confirmaSenha()" class="validate inputsenha2" placeholder="Senha" size="30" maxlength=30>
                                                <label class="black-text" for="txtSenha">Digite novamente a senha<i class="material-icons left">vpn_key</i></label>                                   
                                            </div>
                                            <div class='col s6 left'>
                                                <a id="mostraresultadosenha"></a>
                                            </div>
                                            
                                            <div class="row">
                                                <div class="col s12">
                                                    <center><a class="red-text letra123" id="informativoSenha"></a></center>
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
    </div>
</main>
</body>

<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="/paginas_utilitarias/rodape_02.jsp" />

<!-- IMPORTANDO FUNCOES DA PAGINA -->
<script src="../framework/js/Cadastro_Usuario/Funcoes.js" type="text/javascript"></script>

</html>