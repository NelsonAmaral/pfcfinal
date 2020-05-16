<%-- 
    Document   : exibir_usuario
    Created on : 25/11/2018, 05:52:21
    Author     : nelson_amaral
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="../paginas_utilitarias/cabecalho_02.jsp" />

<main>
    <div class="container">
        <div class="row">            
            <div class="card z-depth-2">
                <div class="card-content">
                    <span class="card-title titulo-tabela letra right-align">Buscar Usuarios</span>
                    <%String msg = (String) request.getAttribute("msg");if (msg != null) {%><center><a class="red-text letra"><%=msg%></a></center><%}%>
                    <div class="row">
                            <div class="col s2">
                                 <label>Pesquisa por RG</label>
                                 <input placeholder="00.000.000-0" id="TXTpesquisaRg" maxlength="10">
                          </div>
                        <div class="col s2">
                            <button onclick="BuscarUsuario()"><i class="material-icons">search</i></button>
                        </div>
                    </div>

                    <div class="row">
                        <div class="card z-depth-2">
                            <div class="card-content">
                                <div id="ExibirDadosUsuario"></div>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="container">
                        <div class="row right">
                            <form action="ControleAcesso" method="POST">
                                <input value="Voltar" name="acao" hidden>
                                <button class="btn" type="submit">Voltar</button>
                            </form>
                        </div>
                        <div class="row left">
                            <a class="btn red" href="../paginas_usuario/cadastro_usuario.jsp"><li class="material-icons">add</li></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
</body>

<script>
    function BuscarUsuario() {
        let usuario_rg = document.getElementById("TXTpesquisaRg").value;
        
        BuscarUsuarioPorRG(usuario_rg);
    }

</script>

<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="../paginas_utilitarias/rodape_02.jsp" />
<script src="../framework/js/Busca_Ajax/Busca_ConsultarUsuario/Buscar-usuarioPorRg.js" type="text/javascript"></script>
</html>
