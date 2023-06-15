package com.thislucasme.pdvapplication.model;

import java.io.Serializable;
import java.util.List;

public class ProdutoResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Produto> data;
    private int total;
    private int page;

    public ProdutoResponse() {
    }

    public ProdutoResponse(List<Produto> data, int total, int page) {
        this.data = data;
        this.total = total;
        this.page = page;
    }

    public List<Produto> getData() {
        return data;
    }

    public void setData(List<Produto> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
