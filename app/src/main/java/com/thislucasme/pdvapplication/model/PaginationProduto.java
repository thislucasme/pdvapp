package com.thislucasme.pdvapplication.model;

public class PaginationProduto {
    private int page;
    private int limit;
    private String queryText;
    private String codigoBarras;

    public PaginationProduto() {
    }

    public PaginationProduto(int page, int limit, String queryText, String codigoBarras) {
        this.page = page;
        this.limit = limit;
        this.queryText = queryText;
        this.codigoBarras = codigoBarras;
    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
