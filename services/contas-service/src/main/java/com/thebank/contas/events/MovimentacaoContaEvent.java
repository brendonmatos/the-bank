package com.thebank.contas.events;

import java.io.Serializable;
import java.sql.Date;

import com.thebank.contas.entities.MovimentoConta;
import com.thebank.contas.entities.MovimentoConta.TipoMovimento;

public class MovimentacaoContaEvent implements Serializable {

    private TipoMovimento tipoMovimento;
    private Float valor;
    private Date dataMovimento;
    private String contaOrigemId;
    private String contaDestinoId;

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

    public void setDataMovimento(Date dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    public String getContaOrigemId() {
        return this.contaOrigemId;
    }

    public void setContaOrigemId(String contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }

    public String getContaDestinoId() {
        return this.contaDestinoId;
    }

    public void setContaDestinoId(String contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }

}
