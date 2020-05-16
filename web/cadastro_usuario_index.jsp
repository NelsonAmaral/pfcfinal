<%-- 
    Document   : cadastro_usuario
    Created on : 01/11/2018, 20:33:51
    Author     : nelson_amaral
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->

<html>
    <!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!-- CORE CSS-->
        <link href="framework/css/themes/fixed-menu/materialize.css" rel="stylesheet" type="text/css"/>
        <link href="framework/css/themes/fixed-menu/style.css" rel="stylesheet" type="text/css"/>
        <!-- Custome CSS-->
        <link href="framework/css/custom/custom.css" rel="stylesheet" type="text/css"/>
        <!-- INCLUDED PLUGIN CSS ON THIS PAGE -->
        <link href="framework/vendors/perfect-scrollbar/perfect-scrollbar.css" rel="stylesheet" type="text/css"/>
        <link href="framework/vendors/jvectormap/jquery-jvectormap.css" rel="stylesheet" type="text/css"/>

        <!-- IMPORTANDO BIBLIOTECA DE ICONES -->
        <link href="framework/css/Css_Icones_Materialize/ImportICons.css" rel="stylesheet" type="text/css"/>
        <!-- Biblioteca da mascara dos inputs-->
        <script src="framework/js/jQuery.js" type="text/javascript"></script>

        <script src="framework/jquery.maskedinput-master/src/jquery.maskedinput.js" type="text/javascript"></script>
        <!-- Css de letras-->
        <link href="framework/css/css_letras.css" rel="stylesheet" type="text/css"/>
        <title>ICE</title>

        <script type="text/javascript">
            $(function () {
                $("#data").mask("9999-99-99");
                $(".celular").mask("(99) 99999-9999");
                $(".telefone").mask("(99) 9999-9999");
                $(".cpfMaskara").mask("999.999.999.99");
                $(".rgMaskara").mask("99.999.999.9", {maxlength: false});
                $(".alturamaskara").mask("9.99");
                $(".cepmaskara").mask("99999-999");
            });
        </script>


        <!-- Start Page Loading -->
    <div id="loader-wrapper">
        <div id="loader"></div>
        <div class="loader-section section-left"></div>
        <div class="loader-section section-right"></div>
    </div>

</head>
<br>


<!DOCTYPE html>
<body>
    <main>
        <div class="container">
            <div class="row">
                <div class="col s2">
                </div>
                <div class="col s8">            
                    <div class="card z-depth-2">
                        <div class="card-content">
                            <span class="card-title titulo-tabela letra center-align">Cadastro Paciente</span>
                            <form action="ControleUsuarioNaoLogado" method="POST" ><br/>
                                <div class="row">
                                    <div class="col s6 input-field hoverable">
                                        <input type="text" name="txtNome" id="txtNome" class="hoverable validate" placeholder="Máximo 50 caracteres" size="50" maxlength=50 required>
                                        <label class="black-text" for="txtNome">Nome<i class="material-icons left">account_box</i></label>                                   
                                    </div>

                                    <div class="col s3">
                                        <label class="black-text left" for="txtDate">Data Nascimento<i class="material-icons left">perm_contact_calendar</i></label>                                                   
                                        <br>
                                        <input type="Date" onchange="calcularIdade()" name="txtDate" id="dataUsuario" class="hoverable validate" required>
                                    </div>

                                    <!-- Campo oculto sera aliementado pela funcao de calcular a idade -->
                                    <input type="text" name="txtIdade" id="txtIdade" class="hoverable validate" hidden>


                                    <div id="id_responsavel" class="col s3 input-field hoverable">
                                        <input type="text" name="txtRgResponsavel" id="txtRgResponsavel" class="hoverable validate" placeholder="00.000.000-00" size="9" maxlength=9>
                                        <label class="black-text" for="txtRgResponsavel">Rg Responsavel<i class="material-icons left">account_box</i></label>                                   
                                    </div>

                                </div>
                                <div class="row">
                                    <div class="col s4 input-field hoverable">
                                        <input type="text" name="txtCpf" id="txtCpf" class="hoverable validate cpfMaskara" placeholder="000.000.000-00" size="14" maxlength=14 required>
                                        <label id="informativoCpf" class="black-text" for="txtCpf">Cpf<i class="material-icons left">account_box</i></label>                                   
                                    </div>

                                    <div class="col s4 input-field hoverable">
                                        <input type="text" name="txtRg" id="txtRg" class="hoverable validate" placeholder="00.000.000-00" size="9" maxlength=9 required>
                                        <label class="black-text" for="txtRg">RG<i class="material-icons left">account_box</i></label>                                   
                                    </div>

                                    <div class="col s2 input-field hoverable">
                                        <input type="text" name="txtPeso"  id="txtPeso" class="hoverable validate mascarapeso" placeholder="0000" size="5" maxlength=5>
                                        <label class="black-text" for="txtPeso">Peso<i class="material-icons left">account_box</i></label>                                   
                                    </div>    

                                    <div class="col s2 input-field hoverable">
                                        <input type="text" name="txtAtura" id="txtAtura" pattern="[-+]?[0-9]*\.?[0-9]*" class="hoverable validate alturamaskara" placeholder="0.00" size="3" maxlength=3>
                                        <label class="black-text" for="txtAtura">Altura<i class="material-icons left">account_box</i></label>                                   
                                    </div>                             
                                </div>

                                <div class="row">
                                    <div class="col s3 input-field hoverable">
                                        <input type="text" name="txtTelefone" placeholder="(00) 0000-0000" class="hoverable validate telefone" required>
                                        <label class="black-text" for="txtTelefone">Telefone<i class="material-icons left">phone</i></label>                                   
                                    </div>
                                    <div class="col s3 input-field hoverable">
                                        <input type="text" name="txtCelular" placeholder="(00) 0000-0000" class="celular hoverable validate" required>
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
                                                    
                                                    <div class="col s3 input-field hoverable">
                                                        <input type="text" name="txtCep" onkeyup="somenteNumeros(this);" onclick="cep()" class="hoverable validate" id="txtCep" placeholder="00000-000" size="12" maxlength=12 required>
                                                        <label class="black-text" for="txtCep">Cep<i class="material-icons left">assignment</i></label>                                   
                                                    </div>

                                                    <div class="col s9 input-field hoverable">
                                                        <input type="text" name="txtLog" id="txtLog" class="hoverable validate" placeholder="Máximo 50 caracteres" size="50" maxlength=50 readonly required>
                                                        <label class="black-text" for="txtLog">Logradouro<i class="material-icons left">hotel</i></label>                                    
                                                    </div>

                                                </div>
                                                <div class="row">                                                
                                                    <div class='col s3'>
                                                        <label class="black-text left-align" for="txtUf">UF<i class="material-icons left">assignment</i></label>
                                                        <input type="text" name="txtUf" id="uf" class="hoverable validate" placeholder="UF" size="50" maxlength=50 readonly required>                                                   
                                                    </div>

                                                    <div class="col s4 input-field hoverable">
                                                        <input type="text" name="txtCidade" id="txtCidade" class="hoverable validate" placeholder="Máximo 30 caracteres" size="30" maxlength=30 readonly required>
                                                        <label class="black-text" for="txtCidade">Cidade<i class="material-icons left">assignment</i></label>                                   
                                                    </div>


                                                    <div class="col s5 input-field hoverable">
                                                        <input type="text" name="txtBairro" id="Bairro" class="hoverable validate" placeholder="Máximo 30 caracteres" size="30" maxlength=30 readonly required>
                                                        <label class="black-text" for="txtBairro">Bairro<i class="material-icons left">assignment</i></label>                                   
                                                    </div>

                                                </div>


                                                <div class="row">
                                                    <div class="col s2 input-field hoverable">
                                                        <input type="text" name="txtNumero" onkeyup="somenteNumeros(this)" class="hoverable validate" placeholder="000" size="8" maxlength=8 required>
                                                        <label class="black-text" for="txtNumero">Numero<i class="material-icons left"></i></label>                                   
                                                    </div>                                     
                                                    <div class="col s9 input-field hoverable">
                                                        <input type="text" name="txtComplemento" id="txtComplemento" class="hoverable validate" placeholder="Máximo 54 caracteres" size="54" maxlength=54 required>
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


</html>
<!-- END FOOTER -->
<!-- ================================================
Scripts
================================================ -->

<!-- FUNCOES JS PARA O CADASTRO USUARIO -->
<script src="framework/js/Cadastro_Usuario/Funcoes.js" type="text/javascript"></script>
<!--materialize js-->
<script src="framework/js/materialize.min.js" type="text/javascript"></script>
<!--scrollbar-->
<script src="framework/vendors/perfect-scrollbar/perfect-scrollbar.min.js" type="text/javascript"></script>
<!-- chartjs -->
<script src="framework/vendors/chartjs/chart.min.js" type="text/javascript"></script>
<!-- sparkline -->
<script src="framework/vendors/sparkline/jquery.sparkline.min.js" type="text/javascript"></script>
<!--plugins.js - Some Specific JS codes for Plugin Settings-->
<script src="framework/js/plugins.js" type="text/javascript"></script>
<!--custom-script.js - Add your own theme custom JS-->
<script src="framework/js/custom-script.js" type="text/javascript"></script>

<script src="framework/numerocaractere.js" type="text/javascript"></script>
</body>

</html>