    <%@page import="model.Posto"%>
<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%-- 
        Document   : cadastro_usuario
        Created on : 01/11/2018, 20:33:51
        Author     : nelson_amaral
    --%>

    <%@page import="model.Funcionario"%>
    <%@page contentType="text/html" pageEncoding="UTF-8"%>

    <!-- IMPORTANDO CABECALHO DA PAGINA -->
    <jsp:include page="/paginas_utilitarias/cabecalho.jsp" />


    <%

        //recupera o usuario da sessao
        Funcionario funcionario = (Funcionario) session.getAttribute("usuarioAutenticado");
        Funcionario funcionarioAtuliza = (Funcionario) request.getAttribute("funcionario");
        Usuario usuarioAtuliza = (Usuario) request.getAttribute("usuario");

        if (funcionario != null) {
            System.out.println("id " + funcionario.getId_funcionario());


    %>
    <%
        String msg = (String) request.getAttribute("msg");
        if (msg != null) {
    %>
    <font color="red"><%=msg%></font>
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
                            <span class="card-title titulo-tabela center-align">Atualizando Funcionario(a) <%= usuarioAtuliza.getNome()%></span>
                            <form action="ControleFuncionario" method="POST" ><br/>
                                 <input name="id_funcionrio" value="<%=funcionario.getId_funcionario()%>" hidden>
                                 <input name="txtUsuarioFK" value="<%=usuarioAtuliza.getUsuario_id()%>" hidden>
                                   <% }%>

                                <div class="row">
                                    <div class="col s6">
                                        <label class="black-text" for="txtCpf" >Confen<i class="material-icons left">account_box</i></label>                                               
                                        <input type="text" name="txtConfen" value="<%=funcionarioAtuliza.getCofen()%>" id="txtConfen" class="hoverable validate input-field center-align" size="14" maxlength=14 required>
                                        
                                        <label class="black-text left-align" for="txtPerfil">Perfil<i class="material-icons left">assignment</i></label>
                                        <select class="browser-default" name="txtPerfil">
                                            <option value="<%=funcionarioAtuliza.getPerfil()%>" selected><%=funcionarioAtuliza.getPerfil()%></option>
                                            <option value="ADMINISTRADOR">ADMINISTRADOR</option>
                                            <option value="ENFERMEIRO">ENFERMEIRO</option>
                                            <option value="MEDICO">MEDICO</option>

                                        </select>
                                           
                                            <br>
                             
                                     <label class="black-text left-align" for="txtPostoId">Postos<i class="material-icons left">assignment</i></label>
                                     <select class="browser-default" id="filtrar-estado" name="txtPostoId" required>
                                    <option value="" selected>Selecione...</option>
                                    <option class="blue-text" value="<%= funcionarioAtuliza.getPosto().getPosto_id() %>" selected><%= funcionarioAtuliza.getPosto().getPosto_nome() %></option> 
                                    <%
                                    ArrayList<Posto> listPostos = (ArrayList<Posto>) request.getAttribute("listaPostos");
                                    
                                    for (Posto postos : listPostos){%>   
                                    
                                        <option value="<%= postos.getPosto_id() %>"><%= postos.getPosto_nome() %></option> 
                                    
                                    <% } %>
                                </select>
                           
                                    </div>

                                    <div class="col s6">
                                        <div class="card z-depth-2">
                                            <div class="card-content">
                                                <div class="col s12 input-field">
                                                    <input type="password" id="txtSenha" onchange="verifica()" class="hoverable validate" placeholder="Senha" size="30" maxlength=30>
                                                    <label class="black-text" for="txtSenha">Digite a senha<i class="material-icons left">vpn_key</i></label>                                   
                                                </div>

                                                <div class="col s12 input-field">
                                                    <input type="password" name="txtSenha" id="txtSenhaNovamente" onchange="confirmaSenha()" class="hoverable validate" placeholder="Senha" size="30" maxlength=30>
                                                    <label class="black-text" for="txtSenha">Digite novamente a senha<i class="material-icons left">vpn_key</i></label>                                   
                                                </div>
                                                <div class='col s6 left'>
                                                    <a id="mostraresultadosenha"></a>
                                                </div>

                                            </div>
                                        </div>
                                                        </div>
                                </div>

                                <div class="row center-align">                        
                                    <input name="id_fun_atual" value="<%=funcionarioAtuliza.getId_funcionario()%>" hidden>
                                    <input class="btn waves-effect center-align" type="submit" value="Atualizar" name="acao" >
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

        function somenteNumeros(num) {
            var er = /[^0-9.]/;
            er.lastIndex = 0;
            var campo = num;
            if (er.test(campo.value)) {
                campo.value = "";
            }
        }

     //Função para verificar senhas digitas  iguais
        function confirmaEmail() {

            var email = document.getElementById("txtemail").value;
            var emailnovamente = document.getElementById("txtemailconfirma").value;

            //verifica os campos senhas são iguais
            if (email != emailnovamente) {

                alert('Atenção e-mails digitados não são iguais!');

                //limpar inputs de senha para a nova digitação
                document.getElementById("txtemail").value = '';
                document.getElementById("txtemailconfirma").value = '';
            } else {
                //comando para seguir com a ação atual
                event.preventDefault();
            }
            ;
        }
        ;

        //Função para verificar senhas digitas  iguais
        function confirmaSenha() {

            var Senha = document.getElementById("txtSenha").value;
            var Senhanovamente = document.getElementById("txtSenhaNovamente").value;

            //verifica os campos senhas são iguais
            if (Senha != Senhanovamente) {

                alert('Atenção senhas digitas não são iguais!');

                //limpar inputs de senha para a nova digitação
                document.getElementById("txtSenha").value = '';
                document.getElementById("txtSenhaNovamente").value = '';
            } else {
                //comando para seguir com a ação atual
                event.preventDefault();
            }
            ;
        }
        ;

        function verifica() {
            senha = document.getElementById("txtSenha").value;
            forca = 0;
            mostra = document.getElementById("mostraresultadosenha");
            if ((senha.length >= 4) && (senha.length <= 7)) {
                forca += 10;
            } else if (senha.length > 7) {
                forca += 25;
            }
            if (senha.match(/[a-z]+/)) {
                forca += 10;
            }
            if (senha.match(/[A-Z]+/)) {
                forca += 20;
            }
            if (senha.match(/d+/)) {
                forca += 20;
            }
            if (senha.match(/W+/)) {
                forca += 25;
            }
            return mostra_res();
        }
        function mostra_res() {
            if (forca < 30) {
                mostra.innerHTML = '<tr><td class="letra123"><i class="material-icons red-text">lock_open</i> Fraca</td></tr>';
                //limpar inputs de senha para a nova digitação
                document.getElementById("txtSenha").value = '';
            } else if ((forca >= 30) && (forca < 45)) {
                mostra.innerHTML = '<tr><td><i class="material-icons yellow-text">lock_outline</i> Justa</td></td></tr>';
                ;
            } else if ((forca >= 45) && (forca < 85)) {
                mostra.innerHTML = '<tr><td><i class="material-icons blue-text">lock</i>Forte</td></tr>';
            } else {
                mostra.innerHTML = '<tr><td>Excelente </td></tr>';
            }
        }
    </script>

    <script>

        function verificarIdade() {
            var display = document.getElementById("id_responsavel").style.display;
            var idadeusuario = document.getElementById("txtIdade").value;

            if (idadeusuario < 18) {
                document.getElementById("id_responsavel").style.display = 'block';
            } else {
                document.getElementById("id_responsavel").style.display = 'none';

            }
        }

    </script>




    <!-- IMPORTANDO RODAPE DA PAGINA -->
    <jsp:include page="/paginas_utilitarias/rodape.jsp" />

    </html>
