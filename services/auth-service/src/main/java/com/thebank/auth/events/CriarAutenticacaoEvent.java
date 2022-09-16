package com.thebank.auth.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CriarAutenticacaoEvent {
    private String email;
    private String senha;
    private String cpf;
    private Integer nivel;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getNivel() {
        return this.nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

}