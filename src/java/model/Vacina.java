/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson_amaral
 */
public class Vacina {
    private int id_vacina;
    private String nome;
    private TipoVacina tipo;
    private String descricao;
    private boolean status;
    private boolean statusCalendario;

    private ArrayList<Restricao> restricoes;
    public Vacina(String nome, TipoVacina tipo) {
        this.nome = nome;

        this.tipo = tipo;
    }

    public Vacina() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public boolean isStatusCalendario() {
        return statusCalendario;
    }

    public void setStatusCalendario(boolean statusCalendario) {
        this.statusCalendario = statusCalendario;
    }
    
    
    
    public int getId_vacina() {
        return id_vacina;
    }

    public void setId_vacina(int id_vacina) {
        this.id_vacina = id_vacina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoVacina getTipo() {
        return tipo;
    }

    public void setTipo(TipoVacina tipo) {
        this.tipo = tipo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<Restricao> getRestricoes() {
        return restricoes;
    }

    public void setRestricoes(ArrayList<Restricao> restricoes) {
        this.restricoes = restricoes;
    }

    
    
    
}
