package com.thislucasme.pdvapplication.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private String identificador;
    private String observacao;
    private Double acrescimo = 0.0;
    private Double desconto = 0.0;
    private List<Produto> produtoList = new ArrayList<>();
    private double totalGeral = 0.0;

    public Pedido() {
    }

    public Pedido(String identificador, String observacao, Double acrescimo, Double desconto, List<Produto> produtoList, double totalGeral) {
        this.identificador = identificador;
        this.observacao = observacao;
        this.acrescimo = acrescimo;
        this.desconto = desconto;
        this.produtoList = produtoList;
        this.totalGeral = totalGeral;
    }

    public double getTotalGeral() {
        return totalGeral;
    }

    public void setTotalGeral(double totalGeral) {
        this.totalGeral = totalGeral;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Double getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(Double acrescimo) {
        this.acrescimo = acrescimo;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void setProdutoList(List<Produto> produtoList) {
        this.produtoList = produtoList;
    }
}
