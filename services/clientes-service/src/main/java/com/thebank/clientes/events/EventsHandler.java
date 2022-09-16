package com.thebank.clientes.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.thebank.clientes.ClientesService;
import com.thebank.clientes.CriarClienteDTO;
import com.thebank.clientes.events.RemoverClienteEvent;
import com.thebank.clientes.events.CriarClienteEvent;

/**
 * This class receives the events and triggers the associated
 * business logic.
 */
@Slf4j
@Component
class EventsHandler {

    private ClientesService clientesService;

    EventsHandler(final ClientesService clientesService) {
        this.clientesService = clientesService;
    }

    @RabbitListener(queues = "criar_cliente")
    void handleCriarClienteEvent(final CriarClienteEvent clienteEvent) {
        CriarClienteDTO ccd = new CriarClienteDTO();
        ccd.setNome(clienteEvent.getNome());
        ccd.setCpf(clienteEvent.getCpf());
        ccd.setEmail(clienteEvent.getEmail());
        ccd.setSalario(clienteEvent.getSalario());
        ccd.setEnderecoCep(clienteEvent.getEnderecoCep());
        ccd.setEnderecoCidade(clienteEvent.getEnderecoCidade());
        ccd.setEnderecoComplemento(clienteEvent.getEnderecoComplemento());
        ccd.setEnderecoEstado(clienteEvent.getEnderecoEstado());
        ccd.setEnderecoLogradouro(clienteEvent.getEnderecoLogradouro());
        ccd.setEnderecoNumero(clienteEvent.getEnderecoNumero());
        ccd.setEnderecoTipo(clienteEvent.getEnderecoTipo());
        clientesService.addCliente(ccd);
        log.info("cliente criada com sucesso cpf:" + ccd.getCpf());
    }

    @RabbitListener(queues = "excluir_cliente")
    void handleCriarClienteEvent(final RemoverClienteEvent removeCliente) {
        clientesService.removeCliente(removeCliente.getCpf());
        log.info("cliente criada com sucesso cpf:" + removeCliente.getCpf());
    }

}