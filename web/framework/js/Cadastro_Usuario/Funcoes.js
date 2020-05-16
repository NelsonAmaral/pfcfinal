function somenteNumeros(num) {
        var er = /[^0-9.]/;
        er.lastIndex = 0;
        var campo = num;
        if (er.test(campo.value)) {
          campo.value = "";
        }
    }


function cep() {
let cep = document.getElementById("txtCep").value;

    $.getJSON("https://viacep.com.br/ws/" + cep + "/json", function (result) {
        
        if (("erro" in result)) {

            document.getElementById("erro").value = "CEP não encontrado";
            
            
            //Limpando os campos para novos dados
            document.getElementById("txtLog").value = "";
            document.getElementById("Bairro").value = "";
            document.getElementById("txtCidade").value = "";
            document.getElementById("uf").value = "";
            document.getElementById("txtComplemento").value = "";
            document.getElementById("txtNumero").value = "";

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

            //Limpando os campos para novos dados
            document.getElementById("txtComplemento").value = "";
            document.getElementById("txtNumero").value = "";
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

$('.mascarapeso').maskWeight({integerDigits: 3,decimalDigits: 2,decimalMark: '.'});



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
};

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

window.onload = calcularIdade;
function calcularIdade() {

    let dataNascimento = new Date(document.getElementById("dataUsuario").value);
    let hoje = new Date();
    let intervalo = hoje - dataNascimento;

    if (dataNascimento > hoje) {
        
        alert("Data de nascimento não pode ser maior que a data de hoje");
        document.getElementById("dataUsuario").value = '';

    } else {

        let dias = parseInt((hoje - intervalo) / (24 * 3600 * 1000));
        let d1Y = dataNascimento.getFullYear();
        let d2Y = hoje.getFullYear();
        let d1M = dataNascimento.getMonth();
        let d2M = hoje.getMonth();

        let meses = (d2M + 12 * d2Y) - (d1M + 12 * d1Y);
        let anos = hoje.getFullYear() - dataNascimento.getFullYear();

        document.getElementById("txtIdade").value = anos;

        verificarIdade(anos);
    }
}

function verificarIdade(anos) {

    validarcampoIdade(anos);

    if (anos < 18) {
        
        document.getElementById("id_responsavel").style.display = 'block';                
        
        //Desabilitando a visualização dos inputs de senha para menor de 18
        document.getElementById("inputsenha1").style.display = 'none';      
        document.getElementById("inputsenha2").style.display = 'none';
        
        //Desabilitando a visualização dos inputs de email para menor de 18
        document.getElementById("inputemail1").style.display = 'none';      
        document.getElementById("inputemail2").style.display = 'none';
        
        //Desabilitando a visualização do input CPF
        document.getElementById("txtCpf").setAttribute('readonly',true);

        document.getElementById("informativoSenha").innerHTML = "Usuario menor de 18 anos não é necessario a inclusão da senha de acesso";
        document.getElementById("informativoEmail").innerHTML = "Usuario menor de 18 anos não é necessario a inclusão de email para acesso";
        document.getElementById("informativoCpf").innerHTML = "Campo desativado!";
        
        document.getElementById("inputemail2").attr('required', false);
        document.getElementById("inputemail1").attr('required', false);
        document.getElementById("inputsenha1").attr('required', false);
        document.getElementById("inputsenha2").attr('required', false);
    } else {
        
        //Desabilitando a visualização do input CPF
        document.getElementById("informativoCpf").innerHTML = "<label id='informativoCpf' class='black-text' for='txtCpf'>Cpf<i class='material-icons left'>account_box</i></label>";
        document.getElementById("txtCpf").removeAttribute('readonly');
        
        document.getElementById("id_responsavel").style.display = 'none';
        
        document.getElementById("inputsenha1").style.display = 'block';      
        document.getElementById("inputsenha2").style.display = 'block';
        
            //Desabilitando a visualização dos inputs de email para menor de 18
        document.getElementById("inputemail1").style.display = 'block';      
        document.getElementById("inputemail2").style.display = 'block';
        
        document.getElementById("informativoSenha").innerHTML = "";
        document.getElementById("informativoEmail").innerHTML = "";
        
        document.getElementById("inputemail2").attr('required', true);
        document.getElementById("inputemail1").attr('required', true);
        document.getElementById("inputsenha1").attr('required', true);
        document.getElementById("inputsenha2").attr('required', true);
    }
}

function validarcampoIdade(idadeusuario) {

    if (idadeusuario <= 0 || idadeusuario > 122) {

        document.getElementById("txtIdade").value = '';

    }
}


