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
public class IntervaloDoseVacinacao {

    private int dose;
    private int tempo;
    private String ciclo;
    private Boolean status;

    private CalendarioObrigatorio caledarioObr;

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }
    
    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    

    public CalendarioObrigatorio getCaledarioObr() {
        return caledarioObr;
    }

    public void setCaledarioObr(CalendarioObrigatorio caledarioObr) {
        this.caledarioObr = caledarioObr;
    }

}
