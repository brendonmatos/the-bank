package com.thebank.contas.entities;

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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "gerente")
public class Gerente implements Serializable {

    @Id
    @Column(name = "cpf")
    @NotNull
    private String cpf;

    @OneToMany(mappedBy = "gerente")
    @JsonIgnore
    private List<Conta> contas;

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
