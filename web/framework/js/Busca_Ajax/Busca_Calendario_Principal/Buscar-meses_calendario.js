var req;
 
// FUNÇÃO PARA BUSCA NOTICIA
function ListaCalendario(dia,mes,ano,usuario_id) {
 
// Verificando Browser
if(window.XMLHttpRequest) {
   req = new XMLHttpRequest();
}
else if(window.ActiveXObject) {
   req = new ActiveXObject("Microsoft.XMLHTTP");
}
 var endereco = "listarmesescalendario";
// Arquivo PHP juntamente com o valor digitado no campo (método GET)
var url = "ControlerCalendarioObr?acao="+endereco+"&dia="+dia+"&mes="+mes+"&ano="+ano+"&usuario_id="+usuario_id;
 
// Chamada do método open para processar a requisição
req.open("Get", url, true);
 
// Quando o objeto recebe o retorno, chamamos a seguinte função;
req.onreadystatechange = function() {
 
	// Exibe a mensagem "Buscando Noticias..." enquanto carrega
	if(req.readyState == 1) {
		document.getElementById('CalendarioCarregado').innerHTML = 'Buscando Calendario...';
	}
 
	// Verifica se o Ajax realizou todas as operações corretamente
	if(req.readyState == 4 && req.status == 200) {
 
	// Resposta retornada pelo busca.php
	var resposta = req.responseText;
 
	// Abaixo colocamos a(s) resposta(s) na div resultado
	document.getElementById('CalendarioCarregado').innerHTML = resposta;
	}
}
req.send(null);
}

// FUNÇÃO PARA EXIBIR NOTICIA
function exibirConteudo(id) {
 
// Verificando Browser
if(window.XMLHttpRequest) {
   req = new XMLHttpRequest();
}
else if(window.ActiveXObject) {
   req = new ActiveXObject("Microsoft.XMLHTTP");
}
 
// Arquivo PHP juntamento com a id da noticia (método GET)
var url = "../../Paginas/PaginasComanda/Ajax2_Comanda.php?id="+id;
 
// Chamada do método open para processar a requisição
req.open("Get", url, true);
 
// Quando o objeto recebe o retorno, chamamos a seguinte função;
req.onreadystatechange = function() {
 
	// Exibe a mensagem "Aguarde..." enquanto carrega
	if(req.readyState == 1) {
		document.getElementById('conteudo').innerHTML = 'Aguarde...';
	}
 
	// Verifica se o Ajax realizou todas as operações corretamente
	if(req.readyState == 4 && req.status == 200) {
 
	// Resposta retornada pelo exibir.php
	var resposta = req.responseText;
 
	// Abaixo colocamos a resposta na div conteudo
	document.getElementById('conteudo').innerHTML = resposta;
	}
}
req.send(null);
}