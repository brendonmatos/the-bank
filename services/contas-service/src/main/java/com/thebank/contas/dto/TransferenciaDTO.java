package com.thebank.contas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TransferenciaDTO {
    private String contaDestinoId;
    private Float valor;
    private String contaOrigemId;

    public String getContaOrigemId() {
        return this.contaOrigemId;
    }

    public void setContaOrigemId(String contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }

    public String getContaDestinoId() {
        return this.contaDestinoId;
    }

    public void setContaDestinoId(String contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }

    public Float getValor() {
        return this.valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

}
