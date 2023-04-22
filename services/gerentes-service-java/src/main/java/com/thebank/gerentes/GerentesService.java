package com.thebank.gerentes;

import org.springframework.stereotype.Service;
import org.w3c.dom.events.Event;

import java.util.List;
import java.util.Optional;

@Service
public class GerentesService {

	private final GerenteRepository gerenteRepository;
	private final EventDispatcher dispatcher;

	public GerentesService(GerenteRepository gerenteRepository, EventDispatcher dispatcher) {
		this.gerenteRepository = gerenteRepository;
		this.dispatcher = dispatcher;
	}

	public Gerente addGerente(CriarGerenteDTO gerenteRequest) {
		Gerente c = new Gerente();
		c.setNome(gerenteRequest.getNome());
		c.setCpf(gerenteRequest.getCpf());
		c.setEmail(gerenteRequest.getEmail());
		Gerente savedGerente = gerenteRepository.save(c);

		StoreGerenteDTO storeGerente = new StoreGerenteDTO();
		storeGerente.setCpf(savedGerente.getCpf());
		dispatcher.sendGerenteCriado(storeGerente);

		return savedGerente;
	}

	public List<Gerente> getAllGerentes() {
		return gerenteRepository.findAll();
	}

	public Optional<Gerente> getGerenteById(String gerenteCpf) {
		return gerenteRepository.findById(gerenteCpf);
	}

	public Boolean removeGerente(String gerenteCpf) {
		gerenteRepository.deleteById(gerenteCpf);

		UnstoreGerenteDTO storeGerente = new UnstoreGerenteDTO();
		storeGerente.setCpf(gerenteCpf);
		dispatcher.sendGerenteExcluido(storeGerente);
		return true;
	}

	public Gerente updateGerente(AtualizarGerenteDTO gerenteRequest) {
		Gerente gerente = new Gerente();
		gerente.setNome(gerenteRequest.getNome());
		gerente.setCpf(gerenteRequest.getCpf());
		gerente.setEmail(gerenteRequest.getEmail());
		return gerenteRepository.save(gerente);
	}

}
