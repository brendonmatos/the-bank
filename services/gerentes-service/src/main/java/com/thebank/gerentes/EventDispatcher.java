package com.thebank.gerentes;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventDispatcher {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    EventDispatcher(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendGerenteCriado(final StoreGerenteDTO storeGerente) {
        rabbitTemplate.convertAndSend("gerente_criado", storeGerente);
    }

    public void sendGerenteExcluido(UnstoreGerenteDTO unstoreGerenteDTO) {
        rabbitTemplate.convertAndSend("gerente_excluido", unstoreGerenteDTO);
    }
}