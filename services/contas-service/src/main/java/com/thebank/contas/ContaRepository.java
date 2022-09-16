package com.thebank.contas;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.thebank.contas.entities.Conta;
import com.thebank.contas.entities.Conta.StatusConta;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, String> {

    // @Query(value = "Select * FROM conta c WHERE c.gerente_cpf =:gerenteCpf AND
    // c.status = :status ;", nativeQuery = true)
    // List<Conta> findByGerenteCpfAndStatus(@Param("gerenteCpf") String gerenteCpf,
    // @Param("status") int status);

    @Query(value = "Select * FROM conta c WHERE c.status = 1 AND c.gerente_cpf = :gerenteCpf ORDER BY c.saldo DESC LIMIT 5;", nativeQuery = true)
    List<Conta> findTop5(@Param("gerenteCpf") String gerenteCpf);

    Optional<Conta> findByCpf(String cpf);

    Optional<Conta> getContaByCpfAndStatus(String cpf, StatusConta conta);

}
