package com.thebank.contas.events;

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

    public void sendContaAprovada(final ContaAprovadaEvent cae) {
        rabbitTemplate.convertAndSend("conta_aprovada", cae);
    }

    public void sendContaRejeitada(final ContaRejeitadaEvent cae) {
        rabbitTemplate.convertAndSend("conta_rejeitada", cae);
    }

    public void sendMovimentacaoConta(final MovimentacaoContaEvent mce) {
        rabbitTemplate.convertAndSend("conta_movimentada", mce);
    }
}