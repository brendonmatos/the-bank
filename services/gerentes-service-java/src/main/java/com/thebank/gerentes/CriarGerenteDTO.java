package com.thebank.gerentes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriarGerenteDTO {
    private String nome;
    private String email;
    private String cpf;

    String getNome() {
        return this.nome;
    }

    void setNome(String nome) {
        this.nome = nome;
    }

    String getEmail() {
        return this.email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getCpf() {
        return this.cpf;
    }

    void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
