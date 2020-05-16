<%-- 
    Document   : Help
    Created on : 03/12/2019, 00:18:37
    Author     : marco
--%>

<%-- 
    Document   : PaginaAjuda
    Created on : 27/11/2019, 19:36:33
    Author     : marco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="paginas_utilitarias/cabecalho_03.jsp" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Help</title>
    </head>


    <div class="row">
        <div class="card z-depth-2">
            <div class="card-content">

                <div class="row">
                    <span class="card-title left titulo-tabela letra center-align"><i class="material-icons medium">help</i> FAQ de Ajuda</span>
                </div>

                <ul class="collapsible">
                    <li>
                        <div class="collapsible-header"><i class="material-icons">person</i>Alterar dados pessoais</div>

                        <div class="collapsible-body center-align">
                            <span>
                                <div class="row">
                                    <p> <FONT size="5"> Clique no seu nome de usuario </FONT></p>
                                    <img  src="framework/Imagens/Inicio.PNG"  width=500 height=250/>
                                </div>
                                <p><FONT size="5">E logo depois, aparecera uma aba com as opções, 
                                    <br>clique no botão perfil.  </FONT></p>

                                <div class="row">
                                    <img  src="framework/Imagens/Perfil_clique.PNG"   width=500 height=250/>
                                    <p><FONT size="5"> Esta pagina podera alterar o fisico e o contado do usuario.
                                        <br> *observação: informação pessoal so poderar ser alterado em um posto mais proximo</FONT></p>
                                </div>

                                <div class="row">
                                    <img  src="framework/Imagens/DadosPessoais.png"  width=500 height=250/>

                                </div>
                                <p><FONT size="5"> Agora e so atualizar os seus dados na tabela fisico e 
                                    <br> contato, terminado de digitar aperte o botão rosa 
                                    <br> que ira atualizar com sucesso.<br>
                                    Caso quiser alterar senha aperta no botão que tem a digital(esta no quadrado vermelho) </FONT></p><br>

                                <div class="row">
                                    <img  src="framework/Imagens/Senha.png"  width=500 height=250/>

                                </div>
                                <p><FONT size="5">Logo em seguida vai abrir uma janela para colocar a senha atual e a nova senha e confirmar</FONT></p>
                                <div class="row">
                                    <img  src="framework/Imagens/Salvar senha.PNG"  width=500 height=250/>

                                </div>
                                <p><FONT size="5">E assim finalizando as alterações dos dados<br>(Informações pessoais não podem ser alterado se quiser tera que ir no posto mais proximo)</FONT></p>
                            </span>   

                        </div> 

                    </li>


                    </li>
                    <li>
                        <div class="collapsible-header"><i class="material-icons">place</i>Alterar Endereço</div>
                        <div class="collapsible-body center-align"  >
                            <span><div class="row">
                                    <p> <FONT size="5"> Clique no seu nome de usuario</FONT></p>
                                    <img  src="framework/Imagens/Inicio.PNG"  width=500 height=250/>
                                </div>
                                <p><FONT size="5">E logo depois, aparecera uma aba com as opções, 
                                    <br>clique no botão perfil. </FONT></p>

                                <div class="row">
                                    <img  src="framework/Imagens/Perfil_clique.PNG"   width=500 height=250/>
                                    <p><FONT size="5"> Clica no botão perfil vai direcionar para outra pagina</FONT></p>
                                </div>

                                <div class="row">
                                    <img  src="framework/Imagens/SelecionarEndereco.png"  width=500 height=250/>

                                </div>
                                <p><FONT size="5"> Clica onde esta escrito endereço em vermelho </FONT></p><br>

                                <div class="row">
                                    <img  src="framework/Imagens/Endereco.PNG"  width=500 height=250/>

                                </div>
                                <p><FONT size="5">Agora so apagar o cep atual e depois digitar o cep para localizar o seu endereço e digitar o numero da sua casa e complemento(opcional).<br>
                                    So apertar no confirmar no botão rosa e o endereço estara atualizado</FONT></p>

                                <p><FONT size="5">Endereco atualizado com sucesso</FONT></p>
                            </span>   

                        </div> 
                    </li>



                    <li>
                        <div class="collapsible-header"><i class="material-icons">assignment_ind</i>Alterar restrição</div>
                        <div class="collapsible-body center-align"  >
                            <span><div class="row">
                                    <p> <FONT size="5">  Clique no seu nome de usuario</FONT></p>
                                    <img  src="framework/Imagens/Inicio.PNG"  width=500 height=250/>
                                </div>
                                <p><FONT size="5">E logo depois, aparecera uma aba com as opções, 
                                    <br>clique no botão perfil.</FONT></p>

                                <div class="row">
                                    <img  src="framework/Imagens/Perfil_clique.PNG"   width=500 height=250/>
                                    <p><FONT size="5"> Clica onde esta escrito restrição em vermelho</FONT></p>
                                </div>

                                <div class="row">
                                    <img  src="framework/Imagens/SelecionarRestricao.png"  width=500 height=250/>

                                </div>
                                <p><FONT size="5"> Aparecera as suas restrição, caso quiser excluir e so apertar o botão em vermelho que tem o simbolo de lixo<br>
                                    tambem pode adicionar e so aperta o botão rosa que esta escrito "cadastrar restrição" ira abrir uma nova janela</FONT></p><br>

                                <div class="row">
                                    <img  src="framework/Imagens/Restricao.PNG"  width=500 height=250/>

                                </div>
                                <p><FONT size="5">Agora e so selecionar as restrições que voce possui, <br>
                                    e finalizando aperta no botão vermelho escrito "cadastrar" assim ira cadastrar as suas restrições<br>
                                    OBS:O SISTEMA IMPEDIRA DE APARECER AS VACINAS COM SUAS RESTRIÇÕES</FONT></p>
                                <div class="row">
                                    <img  src="framework/Imagens/restricaoJanela.PNG"  width=500 height=250/>

                                </div>
                                <p><FONT size="5">Assim volta para pagina inicial concluindo as alterações das restrições</FONT></p>

                                <div class="row">
                                    <img  src="framework/Imagens/Restricao.PNG"  width=500 height=250/>

                                </div>


                            </span>   

                        </div> 
                    </li>



                    <li>
                        <div class="collapsible-header"><i class="material-icons">child_care</i>Visualizar seu dependentes.</div>
                        <div class="collapsible-body center-align"  >
                            <span><div class="row">
                                    <p> <FONT size="5">Clica na opção dependentes.</FONT></p>
                                    <img  src="framework/Imagens/Inicio1.png"  width=500 height=250/>
                                </div>
                                <p><FONT size="5">Ira aparecer uma outra opção escrito "Dependentes Usuario" clica nessa opção.</FONT></p>

                                <div class="row">
                                    <img  src="framework/Imagens/Dependentes_Clique.PNG"   width=500 height=250/>
                                    <p><FONT size="5">Ira ter uma lista com seus dependentes, para verificar o seu dependete <br>
                                        e so clicar na seta que podera visualizar o calendario do seu dependente.</FONT></p>
                                </div>

                                <div class="row">
                                    <img  src="framework/Imagens/EntrarDependentes.PNG"  width=500 height=250/>

                                </div>
                                <p><FONT size="5"> Essa e a tela do seu dependente, caso quiser voltar para sua pagina <br>
                                    e so apertar o botão vermelho que esta com um simbolo de uma pessoa, no canto superior direito.</FONT></p><br>

                                <div class="row">
                                    <img  src="framework/Imagens/Dependentes_visualizado.PNG"  width=500 height=250/>
                                </div>
                            </span>

                        </div> 
                    </li>


                    <li>
                        <div class="collapsible-header"><i class="material-icons">supervisor_account</i>Criação de acesso pro dependente</div>
                        <div class="collapsible-body center-align"><span><div class="row">

                                    <p> <FONT size="5">ATENÇÂO:SÓ PODERA CRIAR O ACESSO PRO DEPENDETE QUANDO ELE FIZER 18 ANOS DE VIDA <br> Clica na opção dependentes.</FONT></p>
                                    <img  src="framework/Imagens/Inicio1.png"  width=500 height=250/>
                                </div>
                                <p><FONT size="5">Ira aparecer uma outra opção escrito "Dependentes Usuario" clica nessa opção.</FONT></p>

                                <div class="row">
                                    <img  src="framework/Imagens/Dependentes_Clique.PNG"   width=500 height=250/>
                                    <p><FONT size="5">Ira ter uma lista com seus dependentes, para verificar o seu dependete <br>
                                        e so clicar na seta que podera visualizar o calendario do seu dependente.</FONT></p>
                                </div>

                                <div class="row">
                                    <img  src="framework/Imagens/EntrarDependentes.PNG"  width=500 height=250/>

                                </div>
                                <p><FONT size="5"> Essa e a tela do seu dependente, caso quiser voltar para sua pagina <br>
                                    e so apertar o botão vermelho que esta com um simbolo de uma pessoa, no canto superior direito.</FONT></p><br>

                                <div class="row">
                                    <img  src="framework/Imagens/Dependentes_visualizado.PNG"  width=500 height=250/>
                                </div>
                                <p><FONT size="5">Clica no nome do dependente.</FONT></p><br>

                                <div class="row">
                                    <img  src="framework/Imagens/1.PNG"  width=500 height=250/>
                                </div>
                                <p><FONT size="5">Aparecera uma aba com opções, clica em perfil</FONT></p><br>

                                <div class="row">
                                    <img  src="framework/Imagens/2.PNG"  width=500 height=250/>
                                </div>
                                <p><FONT size="5">Aparecera uma opção escrito "CRIAR O PRIMEIRO ACESSO" clica nessa opção, para abrir uma nova janela</FONT></p><br>

                                <div class="row">
                                    <img  src="framework/Imagens/3.PNG"  width=500 height=250/>
                                </div>
                                <p><FONT size="5">Nessa janela vai ter colocar a senha e o email, e ambos precisa confirmar</FONT></p><br>

                                <div class="row">
                                    <img  src="framework/Imagens/4.PNG"  width=500 height=250/>
                                </div>

                                <p><FONT size="5">O resposanvel ira receber um email falando sobre a desvinculação do dependente,<br>
                                    e assim o acesso do dependente esta liberado.</FONT></p><br>
                            </span>
                        </div> 
                    </li>
                </ul>
            </div>
        </div>
    </div>
</body>
</html>

<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="paginas_utilitarias/rodape.jsp" />