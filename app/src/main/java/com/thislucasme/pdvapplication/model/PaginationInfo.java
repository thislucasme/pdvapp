package com.thislucasme.pdvapplication.model;

public class PaginationInfo {
    private int page;
    private int total;

    public PaginationInfo() {
    }

    public PaginationInfo(int page, int total) {
        this.page = page;
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
