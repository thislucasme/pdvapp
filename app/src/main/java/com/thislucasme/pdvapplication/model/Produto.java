package com.thislucasme.pdvapplication.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id = 0;
    @SerializedName("categorias_id")
    private Integer categoriasId;
    @SerializedName("promocao_id")
    private Integer promocaoId;
    @SerializedName("estoque")
    private int estoque = 0;
    @SerializedName("codigo_barras")
    private String codigoBarras;
    @SerializedName("observacao")
    private String observacao;
    @SerializedName("controlar_estoque")
    private Integer controlarEstoque = 0;
    @SerializedName("venda_fracionada")
    private Integer vendaFracionada = 0;
    @SerializedName("valor_aberto")
    private Integer valorAberto;
    @SerializedName("fornecedores_id")
    private Integer fornecedoresId;
    @SerializedName("users_id")
    private int usersId;
    @SerializedName("descricao")
    private String descricao;
    @SerializedName("url_image")
    private String urlImagem;
    @SerializedName("preco_custo")
    private Double preco_custo = 0.0;
    @SerializedName("preco_venda")
    private Double preco_venda = 0.0;
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("user_uuid")
    private String userUuid;
    private Integer quantidade = 0;

    public Produto() {
    }

    // Construtor


    public Produto(int id, Integer categoriasId, Integer promocaoId, int estoque, String codigoBarras, String observacao, Integer controlarEstoque, Integer vendaFracionada, Integer valorAberto, Integer fornecedoresId, int usersId, String descricao, String urlImagem, Double preco_custo, Double preco_venda, String uuid, String userUuid, Integer quantidade) {
        this.id = id;
        this.categoriasId = categoriasId;
        this.promocaoId = promocaoId;
        this.estoque = estoque;
        this.codigoBarras = codigoBarras;
        this.observacao = observacao;
        this.controlarEstoque = controlarEstoque;
        this.vendaFracionada = vendaFracionada;
        this.valorAberto = valorAberto;
        this.fornecedoresId = fornecedoresId;
        this.usersId = usersId;
        this.descricao = descricao;
        this.urlImagem = urlImagem;
        this.preco_custo = preco_custo;
        this.preco_venda = preco_venda;
        this.uuid = uuid;
        this.userUuid = userUuid;
        this.quantidade = quantidade;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoriasId(Integer categoriasId) {
        this.categoriasId = categoriasId;
    }

    public void setPromocaoId(Integer promocaoId) {
        this.promocaoId = promocaoId;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public void setControlarEstoque(Integer controlarEstoque) {
        this.controlarEstoque = controlarEstoque;
    }

    public void setVendaFracionada(Integer vendaFracionada) {
        this.vendaFracionada = vendaFracionada;
    }

    public void setValorAberto(Integer valorAberto) {
        this.valorAberto = valorAberto;
    }

    public void setFornecedoresId(Integer fornecedoresId) {
        this.fornecedoresId = fornecedoresId;
    }

    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco_custo() {
        return preco_custo;
    }

    public void setPreco_custo(Double preco_custo) {
        this.preco_custo = preco_custo;
    }

    public Double getPreco_venda() {
        return preco_venda;
    }

    public void setPreco_venda(Double preco_venda) {
        this.preco_venda = preco_venda;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Integer getCategoriasId() {
        return categoriasId;
    }

    public Integer getPromocaoId() {
        return promocaoId;
    }

    public int getEstoque() {
        return estoque;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getObservacao() {
        return observacao;
    }

    public Integer getControlarEstoque() {
        return controlarEstoque;
    }

    public Integer getVendaFracionada() {
        return vendaFracionada;
    }

    public Integer getValorAberto() {
        return valorAberto;
    }

    public Integer getFornecedoresId() {
        return fornecedoresId;
    }

    public int getUsersId() {
        return usersId;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", categoriasId=" + categoriasId +
                ", promocaoId=" + promocaoId +
                ", estoque=" + estoque +
                ", codigoBarras='" + codigoBarras + '\'' +
                ", observacao='" + observacao + '\'' +
                ", controlarEstoque=" + controlarEstoque +
                ", vendaFracionada=" + vendaFracionada +
                ", valorAberto=" + valorAberto +
                ", fornecedoresId=" + fornecedoresId +
                ", usersId=" + usersId +
                ", descricao='" + descricao + '\'' +
                ", urlImagem='" + urlImagem + '\'' +
                ", preco_custo=" + preco_custo +
                ", preco_venda=" + preco_venda +
                ", quantidade=" + quantidade +
                ", uuid=" + uuid +
                ", user Uuid=" + userUuid +
                '}';
    }
}
