package com.thebank.contas;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.thebank.contas.entities.Gerente;
import java.util.List;

@Repository
public interface GerenteRepository extends JpaRepository<Gerente, String> {
    @Query(value = "SELECT g.cpf FROM gerente g LEFT OUTER JOIN conta c ON g.cpf = c.gerente_cpf GROUP BY g.cpf ORDER BY Count(c.gerente_cpf) ASC LIMIT 1", nativeQuery = true)
    Gerente findLastLesserAccountsGerente();

    @Query(value = "SELECT * FROM gerente g WHERE g.cpf != :gerenteCpf LIMIT 1", nativeQuery = true)
    Gerente findOneGerenteExceptGerenteCpf(String gerenteCpf);
}