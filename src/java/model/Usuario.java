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
public class Usuario {
    private int usuario_id;    
    private String nome;
    private int idade;
    private String cpf;
    private String rg;
    private String senha;
    private long telefone;
    private long celular;
    private String tiposague;
    private float peso;
    private float altura;
    private Date nascimento;
    private Date datacadastro;
    private int responsavel;
    private boolean Status;
    private Endereco endereco = new Endereco();;
    private Restricao restricao = new Restricao();
    private String email;
    private String rg_responsavel;

    public Usuario() {
    }
    
    public Usuario( String nome, int idade, String cpf, String rg, long telefone, long celular, String tiposague, float peso, float altura, Date nascimento, Endereco endereco, String senha, boolean Status) {
        
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.rg = rg;
        this.telefone = telefone;
        this.celular = celular;
        this.tiposague = tiposague;
        this.peso = peso;
        this.altura = altura;
        this.nascimento = nascimento;
        this.endereco = endereco;
        this.senha = senha;
        this.Status = Status;
    }

    public Usuario(String nome, int idade, String cpf, String rg, long telefone, long celular, String tiposague, float peso, float altura, Date nascimento, String senha, boolean Status) {
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.rg = rg;
        this.telefone = telefone;
        this.celular = celular;
        this.tiposague = tiposague;
        this.peso = peso;
        this.altura = altura;
        this.nascimento = nascimento;
        this.senha = senha;
        this.Status = Status;
    }

    public Date getDatacadastro() {
        return datacadastro;
    }

    public void setDatacadastro(Date datacadastro) {
        this.datacadastro = datacadastro;
    }
    
    public String getRg_responsavel() {
        return rg_responsavel;
    }

    public void setRg_responsavel(String rg_responsavel) {
        this.rg_responsavel = rg_responsavel;
    }

    public int getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(int responsavel) {
        this.responsavel = responsavel;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }   

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Restricao getRestricao() {
        return restricao;
    }

    public void setRestricao(Restricao restricao) {
        this.restricao = restricao;
    }
    
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public long getTelefone() {
        return telefone;
    }

    public void setTelefone(long telefone) {
        this.telefone = telefone;
    }

    public long getCelular() {
        return celular;
    }

    public void setCelular(long celular) {
        this.celular = celular;
    }

    public String getTiposague() {
        return tiposague;
    }

    public void setTiposague(String tiposague) {
        this.tiposague = tiposague;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

}