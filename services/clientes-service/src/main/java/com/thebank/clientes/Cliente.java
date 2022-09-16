package com.thebank.clientes;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
@Table(name = "cliente")
public class Cliente implements Serializable {
    @Id
    @Column(name = "cpf")
    @NotNull
    private String cpf;

    @Column(name = "nome")
    @NotNull
    private String nome;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;

    @Column(name = "salario")
    @NotNull
    private Integer salario;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getSalario() {
        return this.salario;
    }

    public void setSalario(Integer salario) {
        this.salario = salario;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

}
