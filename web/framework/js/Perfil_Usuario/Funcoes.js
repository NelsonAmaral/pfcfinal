//Função para verificar senhas digitas  iguais
function confirmaEmailPrimerioAcesso() {

    var email = document.getElementById("txtemailPrimerioAcesso").value;
    var emailnovamente = document.getElementById("txtemailconfirmaPrimerioAcesso").value;

    //verifica os campos senhas são iguais
    if (email != emailnovamente) {

        alert('Atenção e-mails digitados não são iguais!');

        //limpar inputs de senha para a nova digitação
        document.getElementById("txtemailPrimerioAcesso").value = '';
        document.getElementById("txtemailconfirmaPrimerioAcesso").value = '';
    } else {
        //comando para seguir com a ação atual
        event.preventDefault();
    }
    ;
}
;

//Função para verificar senhas digitas  iguais
function confirmaSenhaPrimerioAcesso() {

    var Senha = document.getElementById("txtSenhaPrimerioAcesso").value;
    var Senhanovamente = document.getElementById("txtSenhaNovamentePrimerioAcesso").value;

    //verifica os campos senhas são iguais
    if (Senha != Senhanovamente) {

        alert('Atenção senhas digitas não são iguais!');

        //limpar inputs de senha para a nova digitação
        document.getElementById("txtSenhaPrimerioAcesso").value = '';
        document.getElementById("txtSenhaNovamentePrimerioAcesso").value = '';
    } else {
        //comando para seguir com a ação atual
        event.preventDefault();
    }
    ;
};

function verificaPrimerioAcesso() {
    senha = document.getElementById("txtSenhaPrimerioAcesso").value;
    forca = 0;
    mostra = document.getElementById("mostraresultadosenhaPrimerioAcesso");
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
    return mostra_resPrimerioAcesso();
}
function mostra_resPrimerioAcesso() {
    if (forca < 30) {
        mostra.innerHTML = '<tr><td class="letra123"><i class="material-icons red-text">lock_open</i> Fraca</td></tr>';
        //limpar inputs de senha para a nova digitação
        document.getElementById("txtSenhaPrimerioAcesso").value = '';
    } else if ((forca >= 30) && (forca < 45)) {
        mostra.innerHTML = '<tr><td><i class="material-icons yellow-text">lock_outline</i> Justa</td></td></tr>';
        ;
    } else if ((forca >= 45) && (forca < 85)) {
        mostra.innerHTML = '<tr><td><i class="material-icons blue-text">lock</i>Forte</td></tr>';
    } else {
        mostra.innerHTML = '<tr><td>Excelente </td></tr>';
    }
}


