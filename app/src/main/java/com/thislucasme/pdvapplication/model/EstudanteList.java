package com.thislucasme.pdvapplication.model;

public class EstudanteList {
    int id;
    String name;

    public EstudanteList() {
    }

    public EstudanteList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
