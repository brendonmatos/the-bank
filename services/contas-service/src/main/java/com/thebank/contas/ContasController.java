package com.thebank.contas;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.thebank.contas.dto.CriarContaDTO;
import com.thebank.contas.dto.DepositarEmContaDTO;
import com.thebank.contas.dto.AtualizarContaDTO;
import com.thebank.contas.dto.ListarContasDTO;
import com.thebank.contas.dto.RejeitarContaDTO;
import com.thebank.contas.dto.SacarDeContaDTO;
import com.thebank.contas.dto.TransferenciaDTO;
import com.thebank.contas.entities.Conta;
import com.thebank.contas.entities.MovimentoConta;
import com.thebank.contas.entities.Conta.StatusConta;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ContasController {

	@Autowired
	private ContasService contasService;

	@PostMapping("/")
	public Conta criarConta(@Valid @RequestBody CriarContaDTO contaRequest) {
		return contasService.criarConta(contaRequest);
	}

	@GetMapping("/")
	public List<Conta> getAllContas(@RequestParam(name = "gerenteCpf", required = false) String gerenteCpf,
			@RequestParam(name = "status", required = false) StatusConta status) {
		ListarContasDTO dto = new ListarContasDTO();
		dto.setGerenteCpf(gerenteCpf);
		dto.setStatus(status);
		return contasService.getAllContas(dto);
	}

	@GetMapping("/top5")
	public List<Conta> getTop5(@RequestParam(name = "gerenteCpf", required = false) String gerenteCpf,
			@RequestParam(name = "status", required = false) StatusConta status) {
		ListarContasDTO dto = new ListarContasDTO();
		dto.setGerenteCpf(gerenteCpf);
		dto.setStatus(status);
		return contasService.getTop5(dto);
	}

	@DeleteMapping("/{id}")
	public Map<String, String> deleteConta(@PathVariable String id) {
		HashMap<String, String> response = new HashMap<>();
		if (contasService.removeConta(id)) {
			response.put("message", "Conta removido com sucesso");
			return response;
		}
		response.put("message", "Conta não encontrado");
		return response;
	}

	@GetMapping("/{id}")
	public Conta getConta(@PathVariable String id) {
		return contasService.getContaById(id).orElse(null);
	}

	@GetMapping("/cpf/{cpf}")
	public Conta getContaByCpf(@PathVariable String cpf) {
		return contasService.getContaByCpf(cpf).orElse(null);
	}

	@GetMapping("/cpf/{cpf}/status")
	public Conta getContaByCpfAndStatus(@PathVariable String cpf,
			@RequestParam(name = "status", required = false) StatusConta status) {
		return contasService.getContaByCpfAndStatus(cpf, status).orElse(null);
	}

	// @PutMapping("/{id}")
	// public Conta updateConta(@PathVariable String id, @Valid @RequestBody
	// AtualizarContaDTO contaRequest) {
	// HashMap<String, String> response = new HashMap<>();

	// if (!contasService.getContaById(id).isPresent()) {
	// response.put("message", "Conta não encontrado");
	// return null;
	// }

	// contaRequest.setId(id);
	// return contasService.updateConta(contaRequest);
	// }

	@PutMapping("/{id}/aprovar")
	public Conta aprovarConta(@PathVariable String id) {
		return contasService.aprovarConta(id);
	}

	@PutMapping("/{id}/reprovar")
	public Conta reprovarConta(@PathVariable String id, @Valid @RequestBody RejeitarContaDTO rejectDto) {
		rejectDto.setId(id);
		return contasService.reprovarConta(rejectDto);
	}

	@PutMapping("/{id}/sacar")
	public Conta sacar(@PathVariable String id, @Valid @RequestBody SacarDeContaDTO sacarDto) {
		sacarDto.setContaOrigemId(id);
		return contasService.sacarDeConta(sacarDto);
	}

	@PutMapping("/{id}/depositar")
	public Conta depositar(@PathVariable String id, @Valid @RequestBody DepositarEmContaDTO ded) {
		ded.setContaDestinoId(id);
		return contasService.depositarEmConta(ded);
	}

	@PutMapping("/{id}/transferir")
	public Conta transferir(@PathVariable String id, @Valid @RequestBody TransferenciaDTO td) {
		td.setContaOrigemId(id);
		log.info("TransferenciaDTO: {}", td);
		return contasService.transferirEntreContas(td);
	}

	@GetMapping("/{id}/movimentacoes")
	public List<MovimentoConta> listarMovimentacoes(@PathVariable String id,
			@RequestParam("dataInicial") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataInicial,
			@RequestParam("dataFinal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataFinal) {
		return contasService.listarMovimentacoesConta(id, dataInicial, dataFinal);
	}
}
