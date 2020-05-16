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
public class Posto {
    private int posto_id;
    private String posto_nome;
    private long posto_telefone;
    private Endereco posto_endereco = new Endereco();
    private boolean posto_ativo;

    public int getPosto_id() {
        return posto_id;
    }

    public void setPosto_id(int posto_id) {
        this.posto_id = posto_id;
    }

    public String getPosto_nome() {
        return posto_nome;
    }

    public void setPosto_nome(String posto_nome) {
        this.posto_nome = posto_nome;
    }

    public long getPosto_telefone() {
        return posto_telefone;
    }

    public void setPosto_telefone(long posto_telefone) {
        this.posto_telefone = posto_telefone;
    }

    public Endereco getPosto_endereco() {
        return posto_endereco;
    }

    public void setPosto_endereco(Endereco posto_endereco) {
        this.posto_endereco = posto_endereco;
    }

    public boolean isPosto_ativo() {
        return posto_ativo;
    }

    public void setPosto_ativo(boolean posto_ativo) {
        this.posto_ativo = posto_ativo;
    }
    
    
            
}
