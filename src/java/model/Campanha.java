/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author nelson_amaral
 */
public class Campanha {
   private int campanha_id;
   private String campanha_nome;
   private Date campanha_inicio;
   private Date campanha_final;
   private Date campanha_prevista;
   private String campanha_obs;
   private Vacina vacina;
   private Endereco endereco = new Endereco();
   private Cidade cidade = new Cidade();
   private boolean campanha_status;

    public Endereco getEndereco() {
        return endereco;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
    
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
   
    public String getCampanha_obs() {
        return campanha_obs;
    }

    public void setCampanha_obs(String campanha_obs) {
        this.campanha_obs = campanha_obs;
    }
   

    public int getCampanha_id() {
        return campanha_id;
    }

    public void setCampanha_id(int campanha_id) {
        this.campanha_id = campanha_id;
    }

    public String getCampanha_nome() {
        return campanha_nome;
    }

    public void setCampanha_nome(String campanha_nome) {
        this.campanha_nome = campanha_nome;
    }

    public Date getCampanha_inicio() {
        return campanha_inicio;
    }

    public void setCampanha_inicio(Date campanha_inicio) {
        this.campanha_inicio = campanha_inicio;
    }

    public Date getCampanha_final() {
        return campanha_final;
    }

    public void setCampanha_final(Date campanha_final) {
        this.campanha_final = campanha_final;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public boolean isCampanha_status() {
        return campanha_status;
    }

    public void setCampanha_status(boolean campanha_status) {
        this.campanha_status = campanha_status;
    }

    public Date getCampanha_prevista() {
        return campanha_prevista;
    }

    public void setCampanha_prevista(Date campanha_prevista) {
        this.campanha_prevista = campanha_prevista;
    }

    public Campanha(int campanha_id, String campanha_nome, Date campanha_inicio, Date campanha_final, Date campanha_prevista, Vacina vacina, boolean campanha_status) {
        this.campanha_id = campanha_id;
        this.campanha_nome = campanha_nome;
        this.campanha_inicio = campanha_inicio;
        this.campanha_final = campanha_final;
        this.campanha_prevista = campanha_prevista;
        this.vacina = vacina;
        this.campanha_status = campanha_status;
    }

    public Campanha() {
    }

    public Campanha(Vacina vacina) {
        this.vacina = vacina;
    }
   
    
}
