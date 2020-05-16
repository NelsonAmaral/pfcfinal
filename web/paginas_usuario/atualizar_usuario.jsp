<%-- 
    Document   : Atualizar_Usuario
    Created on : 25/11/2018, 12:19:33
    Author     : nelson_amaral
--%>
<%@page import="model.Funcionario"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Usuario"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="/paginas_utilitarias/cabecalho.jsp" />

<%
    Usuario usuario = (Usuario) request.getAttribute("user");

    Usuario usuarioResponsavel = (Usuario) request.getAttribute("usuarioResponsavel");
%>

<!DOCTYPE html>
<main>
    <div class="container">
        <div class="row">            
            <div class="card z-depth-2">
                <div class="card-content">
                    <span class="card-title titulo-tabela center-align">Atualizar Paciente</span>
                    <form action="ControleUsuario" method="POST" ><br/>
                        <input type="text" name="txtId" value="<%= usuario.getUsuario_id()%>" hidden>

                        <div class="row">
                            <div class="col s12">
                                <div class="card z-depth-2">
                                    <div class="card-content">
                                        <div class="row"><h5 class="left"><i>Informações</i></h5></div>
                                        <div class="row">
                                            <div class="col s6 input-field">
                                                <input type="text" name="txtNome" value="<%= usuario.getNome()%>" id="txtNome" class="validate" placeholder="Máximo 50 caracteres" size="50" maxlength=50 required>
                                                <label class="black-text" for="txtNome">Nome<i class="material-icons left">account_box</i></label>                                   
                                            </div>

                                            <div class="col s2">
                                                <label class="black-text left" for="txtDate">Data Nascimento<i class="material-icons left">perm_contact_calendar</i></label>                                                   
                                                <br>
                                                <input type="Date" onchange="calcularIdade()" name="txtDate" value="<%= usuario.getNascimento()%>" id="dataUsuario" class="validate" required>
                                            </div>

                                            <!-- Campo oculto sera aliementado pela funcao de calcular a idade -->
                                            <input type="text" name="txtIdade" id="txtIdade" class="validate" hidden>


                                            <div id="id_responsavel" class="col s3 input-field hoverable">
                                                <input type="text" name="txtRgResponsavel" id="id_responsavel" class="hoverable validate" value="<%= usuarioResponsavel.getRg()%>" placeholder="00.000.000-00" size="10" maxlength=10>
                                                <label class="black-text" for="txtRgResponsavel">Rg Responsavel<i class="material-icons left">account_box</i></label>                                   
                                            </div>

                                        </div>
                                        <div class="row">
                                            <div class="col s2 input-field">
                                                <input type="text" name="txtCpf" id="txtCpf" class="validate cpfMaskara" value="<%=usuario.getCpf()%>" placeholder="000.000.000-00" size="11" maxlength=11 required>
                                                <label class="black-text" for="txtCpf">Cpf<i class="material-icons left">account_box</i></label>                                   
                                            </div>

                                            <div class="col s2 input-field">
                                                <input type="text" name="txtRg" id="txtRg" class="validate" value="<%= usuario.getRg()%>" placeholder="00.000.000-00" size="13" maxlength=13 required>
                                                <label class="black-text" for="txtRg">RG<i class="material-icons left">account_box</i></label>                                   
                                            </div>

                                            <div class="col s2 input-field">
                                                <input type="text" name="txtPeso" value="<%= usuario.getPeso()%>" id="txtPeso" class="validate" placeholder="0000" size="5" maxlength=5>
                                                <label class="black-text" for="txtPeso">Peso<i class="material-icons left">account_box</i></label>                                   
                                            </div>
                                            <div class="col s2 input-field hoverable">
                                                <input type="text" name="txtAtura" id="txtAtura" pattern="[-+]?[0-9]*\.?[0-9]*" value="<%= usuario.getAltura()%>" class="hoverable validate maskara" placeholder="0.00" size="4" maxlength=4>
                                                <label class="black-text" for="txtAtura">Altura<i class="material-icons left">account_box</i></label>                                   
                                            </div>        
                                            <div class='col s3'>
                                                <label class="black-text left-align" for="txtTiposague">Tipo Sanguineo<i class="material-icons left">assignment</i></label>
                                                <select class="browser-default" name="txtTiposague">
                                                    <option value="<%= usuario.getTiposague()%>" selected><%= usuario.getTiposague()%> </option>
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
                                            <div class="col s3 input-field hoverable">
                                                <input type="text" name="txtTelefone" placeholder="(00) 0000-0000" value="<%= usuario.getTelefone()%>" class="hoverable validate telefone">
                                                <label class="black-text" for="txtTelefone">Telefone<i class="material-icons left">phone</i></label>                                   
                                            </div>
                                            <div class="col s3 input-field hoverable">
                                                <input type="text" name="txtCelular" placeholder="(00) 0000-0000" value="<%= usuario.getCelular()%>" class="celular hoverable validate" required>
                                                <label class="black-text" for="txtCelular">Celular<i class="material-icons left">phone</i></label>                                   
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
                                        <div class="row"><h5 class="left"><i>Endereço</i></h5></div>

                                        <a id="erro"></a>

                                        <div class="row">
                                            <div class="col s3 input-field">
                                                <input type="text" name="txtCep" onchange="cep(this.value)" class="validate" value="<%= usuario.getEndereco().getCep()%>" placeholder="00000-000" size="12" maxlength=12 required>
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
                                                <input type="text" name="txtNumero" onkeyup="somenteNumeros(this)" value="<%= usuario.getEndereco().getNumero()%>" class="validate" placeholder="000" size="8" maxlength=8 required>
                                                <label class="black-text" for="txtNumero">Numero<i class="material-icons left"></i></label>                                   
                                            </div>                                     
                                            <div class="col s9 input-field">
                                                <input type="text" name="txtComplemento" value="<%= usuario.getEndereco().getComplemento()%>" id="txtComplemento" class="validate" placeholder="Máximo 54 caracteres" size="54" maxlength=54 required>
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
                                         <div class="row"><h5 class="left"><i>Email</i></h5></div>
                                        <div class="col s6 input-field" id="inputemail1">
                                            <input type="email" name="txtEmail" value="<%= usuario.getEmail()%>" id="txtemail" class="validate" placeholder="Máximo 50 caracteres" size="50" maxlength=50>
                                            <label class="black-text" for="txtNome">Email<i class="material-icons left">account_box</i></label>                                   
                                        </div>
                                        <div class="col s6 input-field" id="inputemail2">
                                            <input type="email" id="txtemailconfirma" onchange="confirmaEmail()" value="<%= usuario.getEmail()%>" class="validate" placeholder="Máximo 50 caracteres" size="50" maxlength=50>
                                            <label class="black-text" for="txtNome">Confirmar Email<i class="material-icons left">account_box</i></label>                                   
                                        </div>

                                        <div class="row">
                                            <div class="col s12">
                                                <center><a class="red-text letra123 center-align" id="informativoEmail"></a></center>
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
                                         <div class="row"><h5 class="left"><i>Senha de Acesso</i></h5></div>
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
                            <button class="btn waves-effect" type="submit" value="Comfirma" name="acao">Atualizar</button>
                        </div>
                    </form> 
                </div>
            </div>
        </div>
    </div>
</main>
</body>   

<!-- IMPORTANDO JS -->
<script src="framework/js/Cadastro_Usuario/Funcoes.js" type="text/javascript"></script>
<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="/paginas_utilitarias/rodape.jsp" />

</html>
