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
    private Integer quantidadeProdutosPedido = 0;
    private Double ultimoValorProduto = 0.0;
    private String ultimoNomeProduto;
    private String userId;
    @TypeConverters(Converters.class)
    private List<Produto> produtoList = new ArrayList<>();
    private double totalGeral = 0.0;

    public Pedido() {
    }

    public Pedido(Integer identificador,
                  String observacao,
                  Double acrescimo,
                  Double desconto,
                  List<Produto> produtoList,
                  Double totalGeral,
                  Integer quantidadeProdutosPedido,
                  Double ultimoValorProduto,
                  String ultimoNomeProduto) {
        this.identificador = identificador;
        this.observacao = observacao;
        this.acrescimo = acrescimo;
        this.desconto = desconto;
        this.produtoList = produtoList;
        this.totalGeral = totalGeral;
        this.quantidadeProdutosPedido = quantidadeProdutosPedido;
        this.ultimoNomeProduto = ultimoNomeProduto;
        this.ultimoValorProduto = ultimoValorProduto;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getQuantidadeProdutosPedido() {
        return quantidadeProdutosPedido;
    }

    public void setQuantidadeProdutosPedido(Integer quantidadeProdutosPedido) {
        this.quantidadeProdutosPedido = quantidadeProdutosPedido;
    }

    public Double getUltimoValorProduto() {
        return ultimoValorProduto;
    }

    public void setUltimoValorProduto(Double ultimoValorProduto) {
        this.ultimoValorProduto = ultimoValorProduto;
    }

    public String getUltimoNomeProduto() {
        return ultimoNomeProduto;
    }

    public void setUltimoNomeProduto(String ultimoNomeProduto) {
        this.ultimoNomeProduto = ultimoNomeProduto;
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

    @Override
    public String toString() {
        return "Pedido{" +
                "identificador=" + identificador +
                ", observacao='" + observacao + '\'' +
                ", acrescimo=" + acrescimo +
                ", desconto=" + desconto +
                ", quantidadeProdutosPedido=" + quantidadeProdutosPedido +
                ", ultimoValorProduto=" + ultimoValorProduto +
                ", ultimoNomeProduto='" + ultimoNomeProduto + '\'' +
                ", produtoList=" + produtoList +
                ", totalGeral=" + totalGeral +
                ", userId=" + userId +
                '}';
    }
}
