package com.thebank.clientes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thebank.clientes.AtualizarClienteDTO;
import com.thebank.clientes.Cliente;
import java.util.List;
import java.util.Optional;

@Service
public class ClientesService {

	private final ClienteRepository clienteRepository;

	public ClientesService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public Cliente addCliente(CriarClienteDTO clienteRequest) {
		Cliente c = new Cliente();
		c.setCpf(clienteRequest.getCpf());
		c.setNome(clienteRequest.getNome());
		c.setEmail(clienteRequest.getEmail());
		c.setSalario(clienteRequest.getSalario());

		Endereco endereco = new Endereco();
		endereco.setCep(clienteRequest.getEnderecoCep());
		endereco.setCidade(clienteRequest.getEnderecoCidade());
		endereco.setComplemento(clienteRequest.getEnderecoComplemento());
		endereco.setEstado(clienteRequest.getEnderecoEstado());
		endereco.setLogradouro(clienteRequest.getEnderecoLogradouro());
		endereco.setNumero(clienteRequest.getEnderecoNumero());
		endereco.setTipo(clienteRequest.getEnderecoTipo());

		c.setEndereco(endereco);

		return clienteRepository.save(c);
	}

	public List<Cliente> getAllClientes() {
		return clienteRepository.findAll();
	}

	public Optional<Cliente> getClienteById(String cpf) {
		return clienteRepository.findById(cpf);
	}

	public Boolean removeCliente(String cpf) {
		clienteRepository.deleteById(cpf);
		return true;
	}

	public Cliente updateCliente(AtualizarClienteDTO clienteRequest) {
		Cliente c = new Cliente();
		c.setCpf(clienteRequest.getCpf());
		c.setNome(clienteRequest.getNome());
		c.setEmail(clienteRequest.getEmail());
		c.setSalario(clienteRequest.getSalario());

		Endereco endereco = new Endereco();
		endereco.setCep(clienteRequest.getEnderecoCep());
		endereco.setCidade(clienteRequest.getEnderecoCidade());
		endereco.setComplemento(clienteRequest.getEnderecoComplemento());
		endereco.setEstado(clienteRequest.getEnderecoEstado());
		endereco.setLogradouro(clienteRequest.getEnderecoLogradouro());
		endereco.setNumero(clienteRequest.getEnderecoNumero());
		endereco.setTipo(clienteRequest.getEnderecoTipo());

		c.setEndereco(endereco);

		return clienteRepository.save(c);
	}

}
