package com.thebank.auth;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;

    @Column(name = "cpf", unique = true)
    @NotNull
    private String cpf;

    @Column(name = "salted_senha")
    @NotNull
    private String saltedSenha;

    @Column(name = "nivel")
    @NotNull
    private Integer nivel;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSaltedSenha() {
        return this.saltedSenha;
    }

    public void setSaltedSenha(String saltedSenha) {
        this.saltedSenha = saltedSenha;
    }

    public Integer getNivel() {
        return this.nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

}
