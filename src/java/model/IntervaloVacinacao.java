/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Victor_Aguiar
 */
public class IntervaloVacinacao {

    private int dose;
    private int dias;
    private boolean ativoOuNao;
    private CalendarioObrigatorio calendarioObr;

    private String anosdeVida;

    public IntervaloVacinacao() {
    }

    public String getanosdeVida() {
        return anosdeVida;
    }

    public void setanosdeVida(String anosdeVida) {
        this.anosdeVida = anosdeVida;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public boolean isAtivoOuNao() {
        return ativoOuNao;
    }

    public void setAtivoOuNao(boolean ativoOuNao) {
        this.ativoOuNao = ativoOuNao;
    }

    public CalendarioObrigatorio getCalendarioObr() {
        return calendarioObr;
    }

    public void setCalendarioObr(CalendarioObrigatorio calendarioObr) {
        this.calendarioObr = calendarioObr;
    }

}
