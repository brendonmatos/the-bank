package com.thebank.contas.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
@Table(name = "movimentos_contas")
public class MovimentoConta implements Serializable {

    public enum TipoMovimento {
        DEPOSITO,
        SAQUE,
        TRANSFERENCIA
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "tipo_movimento")
    @NotNull
    private TipoMovimento tipoMovimento;

    @Column(name = "valor")
    @NotNull
    private Float valor;

    @CreationTimestamp
    @Column(name = "data_movimento")
    private Date dataMovimento;

    @ManyToOne
    @JoinColumn(name = "conta_origem_id")
    @Nullable
    private Conta contaOrigem;

    @ManyToOne
    @JoinColumn(name = "conta_destino_id")
    @Nullable
    private Conta contaDestino;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoMovimento getTipoMovimento() {
        return this.tipoMovimento;
    }

    public void setTipoMovimento(TipoMovimento tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    public Float getValor() {
        return this.valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Date getDataMovimento() {
        return this.dataMovimento;
    }

    public Conta getContaOrigem() {
        return this.contaOrigem;
    }

    public void setContaOrigem(Conta contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Conta getContaDestino() {
        return this.contaDestino;
    }

    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
    }

}
