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
public class Funcionario extends Usuario{
    
    private int id_funcionario;
    private String cofen;
    private String senha;
    private PerfilDeAcesso perfil;
    private Posto posto = new Posto();
    private boolean status;
    
    public Funcionario(){}

    public Posto getPosto() {
        return posto;
    }

    public void setPosto(Posto posto) {
        this.posto = posto;
    }
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PerfilDeAcesso getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilDeAcesso perfil) {
        this.perfil = perfil;
    }
    
    public int getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public String getCofen() {
        return cofen;
    }

    public void setCofen(String cofen) {
        this.cofen = cofen;
    }
    
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
     
}
