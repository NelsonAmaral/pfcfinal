<%-- 
    Document   : atualiza_posto
    Created on : 27/10/2019, 11:21:41
    Author     : nelson_amaral
--%>


<%@page import="model.Posto"%>
<%@page import="model.Funcionario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- IMPORTANDO CABECALHO DA PAGINA -->
<jsp:include page="/paginas_utilitarias/cabecalho.jsp" />

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
            <div class="col s2"></div>
            <div class="col s8">            
                <div class="card z-depth-2">
                    <div class="card-content">
                        <span class="card-title titulo-tabela letra center-align">Atualiza Posto</span>
                        <form action="ControlePosto" method="POST" ><br/>
                            <input name="id_funcionrio" value="<%=funcionario.getId_funcionario()%>" hidden>
                            <% }%>
                            <%
                                Posto posto =  (Posto) request.getAttribute("posto");
                            %>
                            <div class="row">
                                <div class="col s12 input-field hoverable">
                                    <input type="text" name="txtNome" id="txtNome" value="<%=posto.getPosto_nome()%>" class="hoverable validate" placeholder="Máximo 50 caracteres" size="50" maxlength=50 required>
                                    <label class="black-text" for="txtNome">Nome<i class="material-icons left">account_box</i></label>                                   
                                </div>

                            </div>


                            <div class="row">
                                <div class="col s12 input-field hoverable">
                                    <input type="text" name="txtTelefone" value="<%=posto.getPosto_telefone()%>" placeholder="(00) 0000-0000" class="hoverable validate telefone" required>
                                    <label class="black-text" for="txtTelefone">Telefone<i class="material-icons left">phone</i></label>                                   
                                </div>

                            <div class="row center-align">
                                <input type="text" name="id_posto" value="<%=posto.getPosto_id()%>" hidden/>  
                                <input class="btn waves-effect center-align" type="submit" value="Confirma" name="acao" >
                            </div>
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
                document.getElementById("txtLog").disabled = true;
                document.getElementById("Bairro").disabled = true;
                document.getElementById("txtCidade").disabled = true;
                document.getElementById("uf").disabled = true;
            }
            console.log(result);
        });
    }
    ;


    jQuery.fn.extend({maskWeight: function (t) {
            window._maskData = {selector: $(this), arr: [], insertCount: 0, numberPressed: !1, options: {}, defOptions: {integerDigits: 3, decimalDigits: 3, decimalMark: ".", initVal: "", roundingZeros: !0, digitsCount: 6, callBack: null, doFocus: !0}, initializeOptions: function (t) {
                    if (this.options = $.extend(!0, this.defOptions), this.arr = [], this.insertCount = 0, this.numberPressed = !1, t)
                        for (var i in t)
                            void 0 !== t[i] && null !== t[i] && (this.options[i] = t[i]);
                    0 == this.options.decimalDigits && (this.options.decimalMark = "");
                    var s = !1;
                    if ("" == this.options.initVal) {
                        if (this.options.roundingZeros)
                            this.options.initVal += "0";
                        else
                            for (var n = 0; n < this.options.integerDigits; n++)
                                this.options.initVal += "0";
                        this.options.initVal += this.options.decimalMark;
                        for (var n = 0; n < this.options.decimalDigits; n++)
                            this.options.initVal += "0"
                    } else
                        s = !0;
                    this.options.digitsCount = this.options.integerDigits + this.options.decimalDigits, this.arr = [];
                    for (var n = 0; n < this.options.digitsCount; n++)
                        this.arr.push("0");
                    s && parseInt(this.options.initVal) > 0 && this.createInitialValueArr()
                }, createInitialValueArr: function () {
                    this.options.initVal = 0 == this.options.decimalDigits ? parseInt(this.options.initVal) : parseFloat(this.options.initVal.toString().replace(",", ".")).toFixed(this.options.decimalDigits).replace(".", this.options.decimalMark);
                    for (var t = this.options.initVal.toString().replace(".", "").replace(",", "").split(""), i = 0; i < t.length; i++)
                        this.insert(t[i])
                }, insert: function (t) {
                    var i = this.mask(t);
                    this.selector.val(i), this.setCartetOnEnd()
                }, mask: function (t) {
                    "backspace" == t ? this.insertCount > 0 && (this.arr.pop(), this.arr.unshift("0"), this.insertCount--) : this.insertCount < this.options.digitsCount && (this.arr.shift(), this.arr.push(t.toString()), this.insertCount++);
                    for (var i = "", s = 0; s < this.arr.length; s++)
                        i += this.arr[s];
                    return i = this.reduce(i)
                }, reduce: function (t) {
                    return 0 == this.options.decimalDigits ? this.options.roundingZeros ? parseInt(t) : t : this.options.roundingZeros ? parseInt(t.substring(0, this.options.integerDigits)) + this.options.decimalMark + t.substring(this.options.integerDigits, this.options.digitsCount) : t.substring(0, this.options.integerDigits) + this.options.decimalMark + t.substring(this.options.integerDigits, this.options.digitsCount)
                }, getNumber: function (t) {
                    return String.fromCharCode(t.keyCode || t.which)
                }, setCartetOnEnd: function () {
                    var t = this;
                    setTimeout(function () {
                        var i = t.selector.val().length;
                        t.options.doFocus && t.selector[0].focus(), t.selector[0].setSelectionRange(i, i)
                    }, 1)
                }, isNumberOrBackspace: function (t) {
                    return"backspace" == t ? !0 : parseInt(t) || 0 == parseInt(t) ? !0 : !1
                }, init: function () {
                    var t = this;
                    this.selector.val(this.options.initVal), this.selector.on("click", function (i) {
                        t.setCartetOnEnd()
                    });
                    var i = navigator.userAgent.toLowerCase(), s = i.indexOf("android") > -1;
                    s ? (window._maskDataLastVal = this.selector.val(), this.selector[0].removeEventListener("input", window._maskDataAndroidMaskHandler, !0), setTimeout(function () {
                        window._maskDataAndroidMaskHandler = function (i) {
                            if (i.preventDefault(), i.stopPropagation(), t.selector.val().length < window._maskDataLastVal.length)
                                t.insert("backspace");
                            else {
                                var s = t.selector.val().charAt(t.selector.val().length - 1);
                                0 == parseFloat(t.selector.val().replace(",", ".")) && 0 == parseInt(s) ? t.insert("backspace") : t.insert(s)
                            }
                            return window._maskDataLastVal = t.selector.val(), t.options.callBack && t.options.callBack(), !1
                        }, t.selector[0].addEventListener("input", window._maskDataAndroidMaskHandler, !0)
                    }, 0)) : (this.selector.on("keydown", function (i) {
                        var s = i.keyCode || i.which;
                        (8 == s || 46 == s) && (i.preventDefault(), i.stopPropagation(), t.insert("backspace")), t.options.callBack && t.options.callBack()
                    }), this.selector.on("keypress", function (i) {
                        i.keyCode || i.which;
                        i.preventDefault(), i.stopPropagation();
                        var s = t.getNumber(i);
                        t.isNumberOrBackspace(s) && (0 == parseFloat(t.selector.val().replace(",", ".")) && 0 == parseInt(s) ? t.insert("backspace") : t.insert(s)), t.options.callBack && t.options.callBack()
                    }))
                }}, window._maskData.initializeOptions(t), window._maskData.init()
        }, removeMask: function () {
            window._maskData && ($(this).unbind(), window._maskData = null)
        }});
    $('.mascarapeso').maskWeight({
        integerDigits: 3,
        decimalDigits: 2,
        decimalMark: '.'
    });
</script>

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


    function calcularIdade() {

        var dataNascimento = new Date(document.getElementById("dataUsuario").value);
        var hoje = new Date();
        var intervalo = hoje - dataNascimento;

        var dias = parseInt((hoje - intervalo) / (24 * 3600 * 1000));

        var d1Y = dataNascimento.getFullYear();
        var d2Y = hoje.getFullYear();
        var d1M = dataNascimento.getMonth();
        var d2M = hoje.getMonth();

        var meses = (d2M + 12 * d2Y) - (d1M + 12 * d1Y);
        var anos = hoje.getFullYear() - dataNascimento.getFullYear();

        document.getElementById("txtIdade").value = anos;

        verificarIdade(anos);
    }

    function verificarIdade(anos) {

        validarcampoIdade(anos);

        if (anos < 18) {
            document.getElementById("id_responsavel").style.display = 'block';
        } else {
            document.getElementById("id_responsavel").style.display = 'none';
        }
    }

    function validarcampoIdade(idadeusuario) {

        if (idadeusuario <= 0 || idadeusuario > 122) {

            document.getElementById("txtIdade").value = '';

        }
    }
</script>


<!-- IMPORTANDO RODAPE DA PAGINA -->
<jsp:include page="/paginas_utilitarias/rodape.jsp" />

</html>
