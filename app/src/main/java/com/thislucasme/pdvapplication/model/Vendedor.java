package com.thislucasme.pdvapplication.model;

public class Vendedor {
    private String nome;

    public Vendedor() {
    }

    public Vendedor(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
