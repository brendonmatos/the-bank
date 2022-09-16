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
public class AtualizarContaDTO implements Serializable {
    private String id;
    private String cpf;
    private Float limite;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Float getLimite() {
        return this.limite;
    }

    public void setLimite(Float limite) {
        this.limite = limite;
    }

}
