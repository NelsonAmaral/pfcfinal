<%-- 
    Document   : cadastrar_vacinas
    Created on : 23/10/2019, 23:26:02
    Author     : Victor_Aguiar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="/paginas_utilitarias/cabecalho_02.jsp" />

<!DOCTYPE html>
<html>
    <body>
        <div class="card z-depth-2">
            <div class="card-content">
                <div class="row">
                    <span class="card-title titulo-tabela center-align letra">Registrar Vacinação</span>
                </div>

                <div class="row">
                    <div class="col s3">
                        <label class="black-text" for="txtRg">RG USUARIO<i class="material-icons left">account_box</i></label>                                           
                        <input type="text" name="txtRg" id="txtRg" class="hoverable validate" placeholder="00.000.000-00" size="9" maxlength=9 required>
                    </div>

                    <div class="col s3">
                        <label class="black-text left-align" for="txtTiposague">Tipo<i class="material-icons left">assignment</i></label>
                        <select id="txttipo" class="browser-default" name="txtTipo">
                            <option value="" disabled selected>Selecione...</option>
                            <option value="VacinaOB">Obrigatoria</option>
                            <option value="Campanha">Campanha</option>
                            <option value="Vacinas">Vacinas</option>
                        </select>
                    </div>

                    <button onclick="buscarlista()" class="btn"><i class="material-icons">search</i></button>
                    <br><br><br><br>
                    <div class="row"> 
                        <div class="col s4 left input-field">
                            <input type="text" name="txtNome" id="filtrar-PesquisaVacina" class="hoverable validate" placeholder="Digite o nome da vacina" size="50" maxlength=50 required>
                            <label class="black-text" for="txtNome">Pesquisa <i class="material-icons left">search</i></label>                                   
                        </div>

                        <center><div class="col s12">
                                <div id="returnConsulta"></div>
                            </div></center>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

<script>
    function Confirmaacao(){
         var resultado = confirm('Deseja realmente registrar está vacinação?');
           if (resultado){
              return true;

           }else{
           event.preventDefault();
            };
    };
    
    function buscarlista() {

        let rg_usuario = document.getElementById("txtRg").value;
        let tipoconsulta = document.getElementById("txttipo").value;

        if (rg_usuario === "") {
            alert('Campo RG não preenchido');
        } else {

            if (tipoconsulta === 'VacinaOB') {
                buscarVacinacaoMes(rg_usuario);
            } else if (tipoconsulta === 'Campanha') {
                buscarVacinasCampanha(rg_usuario);
            } else if (tipoconsulta === 'Vacinas') {
                buscarVacinas(rg_usuario);
            } else {
                alert('Campo TIPO não preenchido');
            }

        }
    }
</script>

<!-- IMPORT FUNCAO DE CONSULTA VIA AJAX DE VACINAS OBRIGATORIAS-->
<script src="../framework/js/Busca_Ajax/Busca_registrarVacinasUsuario/Buscar_VacinacaoDoMêsOB.js" type="text/javascript"></script>

<!-- IMPORT FUNCAO DE CONSULTA VIA AJAX DE VACINAS -->
<script src="../framework/js/Busca_Ajax/Busca_registrarVacinasUsuario/Buscar_Vacinas.js" type="text/javascript"></script>

<!-- IMPORT FUNCAO DE CONSULTA VIA AJAX DE VACINAS DAS CAMPANHAS -->
<script src="../framework/js/Busca_Ajax/Busca_registrarVacinasUsuario/Buscar_VacinasCampanha.js" type="text/javascript"></script>

<!-- IMPORT ARQUIVO PARA FILTRO DE PESQUISA -->
<script src="../framework/js/Filtro_Paginas/Filtro_resgistrarVacinasUsuario/Filtro_Vacinas.js" type="text/javascript"></script>

<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="/paginas_utilitarias/rodape_02.jsp" />