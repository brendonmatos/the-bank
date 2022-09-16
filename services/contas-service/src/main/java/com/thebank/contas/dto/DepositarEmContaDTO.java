package com.thebank.contas.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositarEmContaDTO implements Serializable {
    private String contaDestinoId;
    private Float valor;

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
