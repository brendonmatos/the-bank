package com.thebank.clientes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CriarClienteDTO {
    private String nome;

    private String email;
    private String cpf;
    private Integer salario;

    private String enderecoTipo;
    private String enderecoLogradouro;
    private String enderecoCep;
    private String enderecoComplemento;
    private String enderecoNumero;
    private String enderecoCidade;
    private String enderecoEstado;

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

    public String getEnderecoTipo() {
        return this.enderecoTipo;
    }

    public void setEnderecoTipo(String enderecoTipo) {
        this.enderecoTipo = enderecoTipo;
    }

    public String getEnderecoLogradouro() {
        return this.enderecoLogradouro;
    }

    public void setEnderecoLogradouro(String enderecoLogradouro) {
        this.enderecoLogradouro = enderecoLogradouro;
    }

    public String getEnderecoCep() {
        return this.enderecoCep;
    }

    public void setEnderecoCep(String enderecoCep) {
        this.enderecoCep = enderecoCep;
    }

    public String getEnderecoComplemento() {
        return this.enderecoComplemento;
    }

    public void setEnderecoComplemento(String enderecoComplemento) {
        this.enderecoComplemento = enderecoComplemento;
    }

    public String getEnderecoNumero() {
        return this.enderecoNumero;
    }

    public void setEnderecoNumero(String enderecoNumero) {
        this.enderecoNumero = enderecoNumero;
    }

    public String getEnderecoCidade() {
        return this.enderecoCidade;
    }

    public void setEnderecoCidade(String enderecoCidade) {
        this.enderecoCidade = enderecoCidade;
    }

    public String getEnderecoEstado() {
        return this.enderecoEstado;
    }

    public void setEnderecoEstado(String enderecoEstado) {
        this.enderecoEstado = enderecoEstado;
    }

}
