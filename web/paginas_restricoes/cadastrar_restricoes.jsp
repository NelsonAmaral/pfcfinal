<%-- 
    Document   : cadastar_vacina
    Created on : 25/11/2018, 11:21:48
    Author     : nelson_amaral
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="../paginas_utilitarias/cabecalho_02.jsp" />

<br>
<!DOCTYPE html>
<html>
    <body>
        <div class="container">
            <div class="row">
                <div class="col s12">
                    <div class="card z-depth-2">
                        <div class="card-content">
                            <span class="card-title titulo-tabela center-align letra">Cadastrar Restricões</span>
                            <form action="../ControleRestricoes" method="POST">
                                <div class="row">
                                    <div class="col s6 input-field">
                                        <input type="text" name="txtNome" id="txtNome" class="hoverable validate" placeholder="Maximo 30 caracteres" size="30" maxlength=30 required>
                                        <label class="black-text" for="txtNome">Restrições<i class="material-icons left">account_box</i></label>                                   
                                    </div>
                                    <div class="col s6 input-field">
                                        <label class="black-text" for="txtTipo">Descrição<i class="material-icons left">account_box</i></label>                                             
                                        <br>
                                        <textarea style="height: 150px" type="text" name="txtTipo" id="txtTipo" class="validate" placeholder="Maximo 30 caracteres" required></textarea>
                                     </div>
                                </div>
                                <input class="btn left" type="submit" value="Cadastrar" name="acao" >
                            </form>

                            <div class="right">
                            <a href="../ControleRestricoes?acao=Listar" class="btn blue">Voltar</a>
                            </div>
                            <br>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>

    <!-- IMPORTANDO RODAPE DA PAGINA -->
    <jsp:include page="../paginas_utilitarias/rodape_02.jsp" />

</html>
