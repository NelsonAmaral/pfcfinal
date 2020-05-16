
var campoFiltro = document.querySelector("#filtrar-PesquisaTipoVacina");

campoFiltro.addEventListener("input", function() {
    var pacientes = document.querySelectorAll(".vacina_tipo");

    if(this.value.length > 0){

	    for (var i = 0; i < pacientes.length; i++) {
	        var paciente = pacientes[i];
	        var tdNome = paciente.querySelector(".info-TipoVacina");
	        var nome = tdNome.textContent;

	        //RegExp é um objeto especial que auxilia na busca de textos. O primeiro parâmetro, passamos o que queremos buscar.
	        //Passamos o "i" como segundo parâmetro para não levar em conta o Case-Sensitive.
	        var expressao = new RegExp(this.value,"i");

	        //Método .test() serve para testar se o que está sendo buscado existe e retorna um boolean.
	        if (expressao.test(nome)) {
	            paciente.classList.remove("hide");
	        } else {
	            paciente.classList.add("hide");
	        }
	    }
	} else {
		for(var i=0; i < pacientes.length;i++){
			var paciente = pacientes[i];
			paciente.classList.remove("hide");
		}
	}
});

