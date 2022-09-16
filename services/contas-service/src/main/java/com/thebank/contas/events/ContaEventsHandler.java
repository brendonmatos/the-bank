package com.thebank.contas.events;

import com.thebank.contas.ContasService;
import com.thebank.contas.dto.CriarContaDTO;
import com.thebank.contas.dto.StoreGerenteDTO;
import com.thebank.contas.dto.UnstoreGerenteDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * This class receives the events and triggers the associated
 * business logic.
 */
@Slf4j
@Component
class ContaEventsHandler {

    private ContasService contasService;

    ContaEventsHandler(final ContasService contasService) {
        this.contasService = contasService;
    }

    @RabbitListener(queues = "criar_conta")
    void handleCriarContaEvent(final CriarContaDTO contaRequest) {
        try {
            contasService.criarConta(contaRequest);
            log.info("conta criada com sucesso");
        } catch (Exception e) {
            log.error("erro ao criar conta", e);
        }
    }

    @RabbitListener(queues = "gerente_criado")
    void handleGerenteCriadoEvent(final StoreGerenteDTO storeGerenteDTO) {
        try {
            contasService.storeGerente(storeGerenteDTO);
            log.info("gerente criado com sucesso");
        } catch (Exception e) {
            log.error("erro ao criar gerente", e);
        }
    }

    @RabbitListener(queues = "conta_movimentada")
    void handleContaMovimentada(final MovimentacaoContaEvent movimentacaoContaEvent) {
        try {
            contasService.storeContaMovimentacao(movimentacaoContaEvent);
            log.info("conta movimentada com sucesso");
        } catch (Exception e) {
            log.error("erro ao movimentar conta", e);
        }
    }

    @RabbitListener(queues = "gerente_excluido")
    void handleGerenteRemovidoEvent(final UnstoreGerenteDTO unstoreGerenteDTO) {
        try {
            contasService.unstoreGerente(unstoreGerenteDTO);
            log.info("contas rejeitadas com sucesso");
        } catch (Exception e) {
            log.error("erro ao rejeitar contas", e);
        }
    }
}