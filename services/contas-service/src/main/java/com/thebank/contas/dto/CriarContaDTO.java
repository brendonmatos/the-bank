package com.thebank.contas.dto;

import java.io.Serializable;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class CriarContaDTO implements Serializable{
    private String cpf;
    private Float salarioCliente;

    public String getCpf() {
        return this.cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public Float getSalarioCliente() {
        return this.salarioCliente;
    }
    public void setSalarioCliente(Float salarioCliente) {
        this.salarioCliente = salarioCliente;
    }
}

