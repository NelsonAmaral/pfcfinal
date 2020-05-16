$(document).ready(function () {
    $('.modal').modal();
});

function validarPreenchimento() {

    let tipo = document.getElementById("txtTipo").value;
    let idadeoumes = document.getElementById("txtAnos").value;

    if (tipo == 0) {

        if (idadeoumes > 120) {
            alert("Tempo inicial da vacina por anos de vida muito alta!");
            document.getElementById("txtAnos").value = '';
        }else if (idadeoumes < 0){
            alert("Tempo inicial da vacina por anos de vida não pode ser negativo!");
            document.getElementById("txtAnos").value = '';
        }

    } else if (tipo == 1) {

        if (idadeoumes > 12) {
            alert("Tempo inicial da vacina por meses de vida muito alta! (Dica: Selecione anos de vida)");
            document.getElementById("txtAnos").value = '';
        }else if (idadeoumes < 0){
            alert("Tempo inicial da vacina por meses de vida não pode ser negativo!");
            document.getElementById("txtAnos").value = '';
        }

    }
}


//Setando o campo DIV campospersonalizados bloqueado para visualizaçao
document.getElementById("campospersonalizados").style.display = 'none';

//Variavel para aux 
let contador = 1;
let cont = 2;

//Array para gravação de intervalos personalizados 
let intervalosPersonalizados = [];

//função para verificar se o usuario selecionou o campo "Personalizado"
function CamposPersoanlizados() {
    var intervalodoses = document.getElementById("txtIntervalo").value;

    if (intervalodoses == "0") {

        document.getElementById("campospersonalizados").style.display = 'block';

    } else {
        document.getElementById("campospersonalizados").style.display = 'none';
    }
}

//Caso o usuario selecina o campo Personalizado, o sistema utilizará está função para adicionar no Array criado logo a cima 
function AdicionarIntervaloPersonalizadoArray() {

    var intervalodosespersonalizados = document.getElementById("txtIntervalosPersonalizados").value;
    var doses = document.getElementById("txtDose").value;

    if (doses == "") {
        alert("Preencha a quantidade de doses");
    }

    if (contador < doses) {

        contador = contador + 1;

        document.getElementById("txtIntervalosPersonalizados").value = '';

        intervalosPersonalizados.push(intervalodosespersonalizados);


        cont = cont + 1;

        document.getElementById("NumberDose").innerHTML = "<a>DOSE " + cont + "</a>";



        if (contador == doses) {
            document.getElementById("campospersonalizados").style.display = 'none';
            document.getElementById("objintervaloarray").value = intervalosPersonalizados;
            //alert(intervalosPersonalizados);
            document.getElementById("mensagemUsuario").innerHTML = "<center><h6>Intervalos Adicionados Com Sucesso!</h6></center>";
        }
    }
}


function PassarParametrosModal(NomeVacina, TipoVacina, Idvacina) {

    document.getElementById("nomevacina").value = NomeVacina;
    document.getElementById("tipovacina").value = TipoVacina;
    document.getElementById("idVacina").value = Idvacina;
}


