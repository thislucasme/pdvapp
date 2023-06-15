package com.thislucasme.pdvapplication.model;

public class Cliente {
    private String nome;
    private String apelidoNomeFantasia;
    private String CpfCnpj;
    private String email;
    private String telefone;

    public Cliente() {
    }

    public Cliente(String nome, String apelidoNomeFantasia, String cpfCnpj, String email, String telefone) {
        this.nome = nome;
        this.apelidoNomeFantasia = apelidoNomeFantasia;
        CpfCnpj = cpfCnpj;
        this.email = email;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelidoNomeFantasia() {
        return apelidoNomeFantasia;
    }

    public void setApelidoNomeFantasia(String apelidoNomeFantasia) {
        this.apelidoNomeFantasia = apelidoNomeFantasia;
    }

    public String getCpfCnpj() {
        return CpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        CpfCnpj = cpfCnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
