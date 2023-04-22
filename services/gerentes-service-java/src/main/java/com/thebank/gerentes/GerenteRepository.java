package com.thebank.gerentes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GerenteRepository extends JpaRepository<Gerente, String> {
    @Query(value = "SELECT g.cpf FROM gerente g LEFT OUTER JOIN conta c ON g.cpf = c.gerente_cpf GROUP BY g.cpf ORDER BY Count(c.gerente_cpf) ASC LIMIT 1", nativeQuery = true)
    Gerente findFive();
}
