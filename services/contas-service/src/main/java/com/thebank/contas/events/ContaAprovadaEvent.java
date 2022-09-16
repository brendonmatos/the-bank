package com.thebank.contas.events;

import java.io.Serializable;

import com.thebank.contas.entities.Gerente;
import com.thebank.contas.entities.Conta.StatusConta;

public class ContaAprovadaEvent implements Serializable {
    private String id;
    private String cpf;
    // private Gerente gerenteCpf;
    // private Float limite;
    // private Float saldo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // public Gerente getGerente_cpf() {
    // return gerenteCpf;
    // }

    // public void setGerente_cpf(Gerente gerente_cpf) {
    // this.gerenteCpf = gerente_cpf;
    // }

    // public Float getLimite() {
    // return limite;
    // }

    // public void setLimite(Float limite) {
    // this.limite = limite;
    // }

    // public Float getSaldo() {
    // return saldo;
    // }

    // public void setSaldo(Float saldo) {
    // this.saldo = saldo;
    // }

}