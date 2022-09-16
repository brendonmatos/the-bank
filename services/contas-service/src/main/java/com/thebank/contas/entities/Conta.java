package com.thebank.contas.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conta")
public class Conta implements Serializable {

    public enum StatusConta {
        PENDENTE_APROVACAO,
        ATIVA,
        REJEITADA,
        BLOQUEADA,
        CANCELADA;
    }

    @Id
    @NotNull
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "cpf", unique = true)
    @NotNull
    private String cpf;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private Date dataCriacao;

    @ManyToOne
    @JoinColumn(name = "gerente_cpf")
    @Nullable
    private Gerente gerente;

    @Column(name = "limite")
    @NotNull
    private Float limite;

    @Column(name = "saldo")
    @NotNull
    private Float saldo;

    @Column(name = "status")
    @NotNull
    private StatusConta status;

    @Column(name = "motivo_rejeicao")
    @Nullable
    private String motivoRejeicao;

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

    public boolean podeSacarValor(Float valor) {
        Float saldoFinal = (this.getSaldo() + this.getLimite()) - valor;
        if (saldoFinal > 0) {
            return true;
        }
        return false;
    }

    public Float getLimite() {
        return this.limite;
    }

    public void setLimite(Float limite) {
        this.limite = limite;
    }

    public Float getSaldo() {
        return this.saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    public String getMotivoRejeicao() {
        return this.motivoRejeicao;
    }

    public void setMotivoRejeicao(String motivoRejeicao) {
        this.motivoRejeicao = motivoRejeicao;
    }

    public StatusConta getStatus() {
        return this.status;
    }

    public Gerente getGerente() {
        return this.gerente;
    }

    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }

    public void setStatus(StatusConta status) {
        this.status = status;
    }

    public Date getDataCriacao() {
        return this.dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

}
