<%-- 
    Document   : atualizaEndereco
    Created on : 29/07/2019, 20:42:56
    Author     : nelson_amaral
--%>

<%@page import="model.Endereco"%>
<%@page import="model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="../paginas_utilitarias/cabecalho_03_2.jsp" />
 
<%

    //recupera o usuario da sessao
    Usuario usuario = (Usuario) session.getAttribute("usuarioAutenticado");
    Endereco endereco = (Endereco) request.getAttribute("teste");
    if (usuario != null) {
        System.out.println("id " + usuario.getUsuario_id());


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
                        <span class="card-title titulo-tabela center-align">Endereço</span>
                        <form action="ControleEndereco" method="POST">
                            <input name="id_usuario" value="<%=usuario.getUsuario_id()%>" hidden>
                            <input name="id_endereco" value="<%=endereco.getId_endereco()%>" hidden>
                            <% }%>
                            

                            

                         <!--   <div class="row">
                                <h4 class="center-align"><i>Endereço</i></h4>
                            </div>
                         -->
                            <div class="row">
                                <div class="col s12">
                                    <div class="card z-depth-2">
                                        <div class="card-content">
                                            <div class="row">
                                                <div class="col s3 input-field hoverable">
                                                    <input type="text" name="txtCep" onclick="cep(this.value)" class="hoverable validate" placeholder="00000-000" size="12" maxlength=12 required>
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

                           

                            <div class="row center-align">
                                <input class="btn waves-effect center-align" type="submit" name="acao" value="Atualizar">
                            </div>
                        </form>   
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
</body>

<script>
    function cep(cep) {

        $.getJSON("https://viacep.com.br/ws/" + cep + "/json", function (result) {
            if (("erro" in result)) {
                document.getElementById("erro") = "CEP não encontrado";
            } else {
                //Atribuindo Valores Aos Campos 
                document.getElementById("txtLog").value = result.logradouro;
                document.getElementById("Bairro").value = result.bairro;
                document.getElementById("txtCidade").value = result.localidade;
                document.getElementById("uf").value = result.uf;

                //Disabilitando os campos após a atribuicao
                document.getElementById("txtLog").readonly = true;
                document.getElementById("Bairro").readonly = true;
                document.getElementById("txtCidade").readonly = true;
                document.getElementById("uf").readonly = true;
            }
            console.log(result);
        });
    }
    ;
</script>

<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="../paginas_utilitarias/rodape.jsp" />

</html>