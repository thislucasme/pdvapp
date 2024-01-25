package com.thislucasme.pdvapplication.enums;

public enum FormaPagamento {

    DINHEIRO(1),
    CARTAO_DEBITO(2),
    CARTAO_CREDITO(3),
    CHEQUE(4),
    PIX(5),
    FIADO(6);

    private Integer forma;

    FormaPagamento(Integer forma) {
        this.forma = forma;
    }

    public Integer getFormaPagamento() {
        return forma;
    }
}
