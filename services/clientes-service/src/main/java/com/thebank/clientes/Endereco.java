package com.thebank.clientes;

import java.io.Serializable;

import javax.annotation.processing.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
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
@Table(name = "Endereco")
public class Endereco implements Serializable {
    @Id
    @GeneratedValue
    @NotNull
    private Integer id;

    @Column(name = "tipo")
    @NotNull
    private String tipo;

    @Column(name = "logradouro")
    @NotNull
    private String logradouro;

    @Column(name = "cep")
    @NotNull
    private String cep;

    @Column(name = "complemento")
    @NotNull
    private String complemento;

    @Column(name = "numero")
    @NotNull
    private String numero;

    @Column(name = "cidade")
    @NotNull
    private String cidade;

    @Column(name = "estado")
    @NotNull
    private String estado;

    @OneToOne
    private Cliente cliente;
}
