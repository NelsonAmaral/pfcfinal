/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author nelson_amaral
 */
public class CadernetaUsuario {
    private final Usuario usuario = new Usuario();
    private CalendarioObrigatorio calendarioObrigatorio = new CalendarioObrigatorio();
    private Campanha campanha = new Campanha();
    private Funcionario funcionario = new Funcionario();
    private Vacina vacina = new Vacina();
    private Posto posto = new Posto();   
    private Date dateCadastro;
    private Date horaCadastro;
    private String descricao;
    private int caderneta_dose;
    private Date caderneta_datainicio;
    private Date cardeneta_datafinal;
    
    public CadernetaUsuario() {}

    public Usuario getUsuario() {
        return usuario;
    }

    public int getCaderneta_dose() {
        return caderneta_dose;
    }

    public void setCaderneta_dose(int caderneta_dose) {
        this.caderneta_dose = caderneta_dose;
    }
    
    public CalendarioObrigatorio getCalendarioObrigatorio() {
        return calendarioObrigatorio;
    }

    public void setCalendarioObrigatorio(CalendarioObrigatorio calendarioObrigatorio) {
        this.calendarioObrigatorio = calendarioObrigatorio;
    }

    public Campanha getCampanha() {
        return campanha;
    }

    public void setCampanha(Campanha campanha) {
        this.campanha = campanha;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public Posto getPosto() {
        return posto;
    }

    public void setPosto(Posto posto) {
        this.posto = posto;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getCaderneta_datainicio() {
        return caderneta_datainicio;
    }

    public void setCaderneta_datainicio(Date caderneta_datainicio) {
        this.caderneta_datainicio = caderneta_datainicio;
    }

    public Date getCardeneta_datafinal() {
        return cardeneta_datafinal;
    }

    public void setCardeneta_datafinal(Date cardeneta_datafinal) {
        this.cardeneta_datafinal = cardeneta_datafinal;
    }
    
}
