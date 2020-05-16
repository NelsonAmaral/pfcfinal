/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;


/**
 *
 * @author Nelson_Amaral
 */
public class CalendarioObrigatorio {
    
    private int id_calendarioObr;
    private String comentario;
    private Date dateCadastro;
    private Date horaCadastro;
    private boolean status;
    private Integer dose;
    private int diasvidainiciomes;
    private int diasvidafinalmes;
    private boolean status_intervalov;
    private IntervaloVacinacao intervalov = new IntervaloVacinacao();  
    private Vacina vacina = new Vacina();
    private Funcionario funcionario = new Funcionario();
    
    private int vacinacao_concluida;
    
    public CalendarioObrigatorio(){}
    
    public int getVacinacao_concluida() {
        return vacinacao_concluida;
    }

    public void setVacinacao_concluida(int vacinacao_concluida) {
        this.vacinacao_concluida = vacinacao_concluida;
    }
    
    public Date getDateCadastro() {
        return dateCadastro;
    }

    public void setDateCadastro(Date dateCadastro) {
        this.dateCadastro = dateCadastro;
    }

    public Date getHoraCadastro() {
        return horaCadastro;
    }

    public void setHoraCadastro(Date horaCadastro) {
        this.horaCadastro = horaCadastro;
    }
    
    public IntervaloVacinacao getIntervalov() {
        return intervalov;
    }

    public void setIntervalov(IntervaloVacinacao intervalov) {
        this.intervalov = intervalov;
    }
    
    public boolean isStatus_intervalov() {
        return status_intervalov;
    }

    public void setStatus_intervalov(boolean status_intervalov) {
        this.status_intervalov = status_intervalov;
    }
    
    public int getDiasvidainiciomes() {
        return diasvidainiciomes;
    }

    public void setDiasvidainiciomes(int diasvidainiciomes) {
        this.diasvidainiciomes = diasvidainiciomes;
    }

    public int getDiasvidafinalmes() {
        return diasvidafinalmes;
    }

    public void setDiasvidafinalmes(int diasvidafinalmes) {
        this.diasvidafinalmes = diasvidafinalmes;
    }
    
    public Integer getDose() {
        return dose;
    }

    public void setDose(Integer dose) {
        this.dose = dose;
    }
    
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    
    
    public int getId_calendarioObr() {
        return id_calendarioObr;
    }

    public void setId_calendarioObr(int id_calendarioObr) {
        this.id_calendarioObr = id_calendarioObr;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }
    
    
    
}
