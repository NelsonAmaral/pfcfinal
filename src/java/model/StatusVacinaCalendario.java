/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author nelson_amaral
 */
public enum StatusVacinaCalendario {
    MEDICAMENTO_PENDENTE,    //PENDENTE: A espera do prazo para ser injetado
    MEDICAMENTO_RESTRITO,    //RSTRITO: medicamento com alguma reação a outro 
    MEDICAMENTO_NAO_INJETADO,//NAO_INJETADO: prazo para consumir medicamento expirado
    MEDICAMENTO_INJETADO,    //IJETADO: Consumido no tempo determinado 
    MEDICAMENTO_DOSE_INICIAL_NAO_CONSUMIDA, //primeira dose nao comsumida de uma sequencia de medicamentos 
    MEDICAMENTO_DELETADO,
    
}
