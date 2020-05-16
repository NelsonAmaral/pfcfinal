function dataanterior() {

    limparcampodescricaovacina();
    var data = document.getElementById("datd").value;

    var dataformatada = '01-' + data;

    let date = new Date(dataformatada);

    var mescalculado = date.getDate() - 1;
    var anocalculado = date.getFullYear();

    if (mescalculado == 0) {
        mescalculado = 12;
        anocalculado = date.getFullYear() - 1;
    }


    var dateatual = new Date();

    if (date > dateatual) {
        
    date.setDate(mescalculado);
    date.setFullYear(anocalculado);

        let usuario_id = document.getElementById("usuario_id").value;

        ListaCalendario(19, mescalculado, anocalculado, usuario_id);

        document.getElementById("datd").value = mescalculado + "-" + anocalculado;

        document.getElementById("LongDate").innerHTML = date;

    } else {

        alert("Não é possivel buscar meses anteriores!");

    }

}



function dataposterior() {
    limparcampodescricaovacina();
    var data = document.getElementById("datd").value;

    var dataformatada = '01-' + data;

    let date = new Date(dataformatada);

    var mescalculado = date.getDate() + 1;
    var anocalculado = date.getFullYear();

    if (mescalculado > 12) {
        mescalculado = 01;
        anocalculado = date.getFullYear() + 1;
    }

    date.setDate(mescalculado);
    date.setFullYear(anocalculado);

    let usuario_id = document.getElementById("usuario_id").value;

    document.getElementById("LongDate").innerHTML = date;

    ListaCalendario(19, mescalculado, anocalculado, usuario_id);

    document.getElementById("datd").value = mescalculado + "-" + anocalculado;

}

window.onload = dataatual();
function dataatual() {
    limparcampodescricaovacina();
    var data = document.getElementById("datd").value;

    var dataformatada = '01-' + data;

    let date = new Date(dataformatada);

    var mescalculado = date.getDate();
    var anocalculado = date.getFullYear();

    if (mescalculado > 12) {
        mescalculado = 01;
        anocalculado = date.getFullYear() + 1;
    }

    date.setDate(mescalculado);
    date.setFullYear(anocalculado);

    let usuario_id = document.getElementById("usuario_id").value;

    ListaCalendario(19, mescalculado, anocalculado, usuario_id);

    //alert(mescalculado + "-" + anocalculado);
    document.getElementById("datd").value = mescalculado + "-" + anocalculado;

    document.getElementById("LongDate").innerHTML = date;
}

window.onload = calcularIdade;
function calcularIdade() {
    var dataNascimento = new Date(document.getElementById("nascimento").value);
    var hoje = new Date();
    var intervalo = hoje - dataNascimento;

    var dias = parseInt((hoje - intervalo) / (24 * 3600 * 1000));

    var d1Y = dataNascimento.getFullYear();
    var d2Y = hoje.getFullYear();
    var d1M = dataNascimento.getMonth();
    var d2M = hoje.getMonth();

    var meses = (d2M + 12 * d2Y) - (d1M + 12 * d1Y);
    var anos = hoje.getFullYear() - dataNascimento.getFullYear();

    if (anos === 0) {
        if (isNaN(meses)) {
            if (isNaN(dias)) {
                document.getElementById("nascimentoA").innerHTML = 0;
            } else {
                document.getElementById("nascimentoA").innerHTML = dias + " Dias de vida";
            }
        } else {
            document.getElementById("nascimentoA").innerHTML = meses + " Mesês de vida";
        }
    } else {
        document.getElementById("nascimentoA").innerHTML = anos + " Anos de vida";
    }
}

function limparcampodescricaovacina() {
    document.getElementById("descricao").innerHTML = "";
    document.getElementById("nomevacina").innerHTML = "";
}