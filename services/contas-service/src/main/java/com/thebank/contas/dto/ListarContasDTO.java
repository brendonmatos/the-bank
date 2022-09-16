package com.thebank.contas.dto;

import java.io.Serializable;

import com.thebank.contas.entities.Conta;
import com.thebank.contas.entities.Conta.StatusConta;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class ListarContasDTO implements Serializable {
    private String gerenteCpf;
    private StatusConta status;

    public String getGerenteCpf() {
        return this.gerenteCpf;
    }

    public void setGerenteCpf(String gerenteCpf) {
        this.gerenteCpf = gerenteCpf;
    }

    public StatusConta getStatus() {
        return this.status;
    }

    public void setStatus(StatusConta status) {
        this.status = status;
    }

}
