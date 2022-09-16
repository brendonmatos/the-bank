package com.thebank.clientes;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.thebank.clientes.AtualizarClienteDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ClientesController {

	@Autowired
	private ClientesService clientesService;

	@PostMapping("/")
	public Cliente criarCliente(@Valid @RequestBody CriarClienteDTO clienteRequest) {
		return clientesService.addCliente(clienteRequest);
	}

	@GetMapping("/")
	public List<Cliente> listClientes() {
		return clientesService.getAllClientes();
	}

	@DeleteMapping("/{id}")
	public Map<String, String> deleteClient(@PathVariable String id) {
		HashMap<String, String> response = new HashMap<>();
		if (clientesService.removeCliente(id)) {
			response.put("message", "Cliente removido com sucesso");
			return response;
		}
		response.put("message", "Cliente não encontrado");
		return response;
	}

	@PutMapping("/{id}")
	public Cliente updateCliente(@PathVariable String id,
			@Valid @RequestBody AtualizarClienteDTO clienteRequest) {
		clienteRequest.setCpf(id);
		return clientesService.updateCliente(clienteRequest);
	}

	@GetMapping("/{id}")
	public Cliente getCliente(@PathVariable String id) {
		HashMap<String, String> response = new HashMap<>();

		if (!clientesService.getClienteById(id).isPresent()) {
			response.put("message", "Cliente não encontrado");
			return null;
		}

		return clientesService.getClienteById(id).get();
	}

	@GetMapping("/cpf/{id}")
	public Cliente getClienteByCpf(@PathVariable String id) {
		HashMap<String, String> response = new HashMap<>();

		if (!clientesService.getClienteById(id).isPresent()) {
			response.put("message", "Cliente não encontrado");
			return null;
		}

		return clientesService.getClienteById(id).get();
	}
}
