package com.thislucasme.pdvapplication.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.thislucasme.pdvapplication.converters.Converters;

import java.util.ArrayList;
import java.util.List;
@Entity(tableName = "pedidos")
public class Pedido {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer identificador;
    private String observacao;
    private Double acrescimo = 0.0;
    private Double desconto = 0.0;
    @TypeConverters(Converters.class)
    private List<Produto> produtoList = new ArrayList<>();
    private double totalGeral = 0.0;

    public Pedido() {
    }

    public Pedido(Integer identificador, String observacao, Double acrescimo, Double desconto, List<Produto> produtoList, double totalGeral) {
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

    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
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
