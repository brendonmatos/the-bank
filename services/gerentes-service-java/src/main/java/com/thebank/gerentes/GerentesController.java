package com.thebank.gerentes;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class GerentesController {

	@Autowired
	private GerentesService gerentesService;

	@PostMapping("/")
	public Gerente addGerente(@Valid @RequestBody CriarGerenteDTO gerenteRequest) {
		return gerentesService.addGerente(gerenteRequest);
	}

	@GetMapping("/")
	public List<Gerente> getAllGerents() {
		return gerentesService.getAllGerentes();
	}

	@DeleteMapping("/{id}")
	public Map<String, String> deleteGerent(@PathVariable String id) {
		HashMap<String, String> response = new HashMap<>();
		if (gerentesService.removeGerente(id)) {
			response.put("message", "Gerente removido com sucesso");
			return response;
		}
		response.put("message", "Gerente não encontrado");
		return response;
	}

	@GetMapping("/{id}")
	public Gerente getGerente(@PathVariable String id) {
		return gerentesService.getGerenteById(id).orElse(null);
	}

	@PutMapping("/{id}")
	public Gerente updateGerente(@PathVariable String id, @Valid @RequestBody AtualizarGerenteDTO gerenteRequest) {
		HashMap<String, String> response = new HashMap<>();

		if (!gerentesService.getGerenteById(id).isPresent()) {
			response.put("message", "Gerente não encontrado");
			return null;
		}

		return gerentesService.updateGerente(gerenteRequest);
	}
}
