package com.thebank.contas;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.thebank.contas.entities.MovimentoConta;

import java.util.Date;
import java.util.List;

@Repository
public interface MovimentoContaRepository extends JpaRepository<MovimentoConta, String> {

    @Query(value = "SELECT * FROM movimentos_contas m WHERE m.conta_origem_id = :contaId OR m.conta_destino_id = :contaId ORDER BY m.data_movimento DESC;", nativeQuery = true)
    List<MovimentoConta> listAllByConta(@Param("contaId") String contaId);

    @Query(value = "SELECT * FROM movimentos_contas m WHERE (m.conta_origem_id = :contaId OR m.conta_destino_id = :contaId) AND m.data_movimento >= :dataInicial AND m.data_movimento <= :dataFinal ORDER BY m.data_movimento DESC;", nativeQuery = true)
    List<MovimentoConta> listAllByContaInRange(@Param("contaId") String contaId, @Param("dataInicial") Date dataInicial,
            @Param("dataFinal") Date dataFinal);
}
