package com.thebank.gerentes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * This class receives the events and triggers the associated
 * business logic.
 */
@Slf4j
@Component
class GerentesEventsHandler {

    private GerentesService gerentesService;

    GerentesEventsHandler(final GerentesService gerentesService) {
        this.gerentesService = gerentesService;
    }

    @RabbitListener(queues = "criar_gerente")
    void handleCriarGerente(final CriarGerenteDTO gerenteRequest) {
        gerentesService.addGerente(gerenteRequest);
        log.info("gerente criado com sucesso");
    }
}