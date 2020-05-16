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
public class Cidade {
    private String nome_cidade;
    private int id_cidade;
    private int id_estado_cidade;
    private String[] cidades;
    private Estado Estado = new Estado();

    public Estado getEstado() {
        return Estado;
    }

    public void setEstado(Estado Estado) {
        this.Estado = Estado;
    }
    
    public String getNome_cidade() {
        return nome_cidade;
    }

    public void setNome_cidade(String nome_cidade) {
        this.nome_cidade = nome_cidade;
    }

    public int getId_cidade() {
        return id_cidade;
    }

    public void setId_cidade(int id_cidade) {
        this.id_cidade = id_cidade;
    }

    public int getId_estado_cidade() {
        return id_estado_cidade;
    }

    public void setId_estado_cidade(int id_estado_cidade) {
        this.id_estado_cidade = id_estado_cidade;
    }

    public String[] getCidades() {
        return cidades;
    }

    public void setCidades(String[] cidades) {
        this.cidades = cidades;
    }

}
