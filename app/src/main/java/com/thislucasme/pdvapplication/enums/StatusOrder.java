package com.thislucasme.pdvapplication.enums;

public enum StatusOrder {

    PENDENTE(1),
    CONCLUÍDO(2);

    private Integer status;

    StatusOrder(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
