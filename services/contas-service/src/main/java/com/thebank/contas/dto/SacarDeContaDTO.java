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
public class SacarDeContaDTO implements Serializable {
    private String contaOrigemId;
    private Float valor;

    public String getContaOrigemId() {
        return this.contaOrigemId;
    }

    public void setContaOrigemId(String contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }

    public Float getValor() {
        return this.valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

}
