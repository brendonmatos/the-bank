package com.thebank.contas;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thebank.contas.dto.CriarContaDTO;
import com.thebank.contas.dto.DepositarEmContaDTO;
import com.thebank.contas.dto.StoreGerenteDTO;
import com.thebank.contas.dto.TransferenciaDTO;
import com.thebank.contas.dto.UnstoreGerenteDTO;
import com.thebank.contas.dto.ListarContasDTO;
import com.thebank.contas.dto.RejeitarContaDTO;
import com.thebank.contas.dto.SacarDeContaDTO;
import com.thebank.contas.entities.Conta;
import com.thebank.contas.entities.Conta.StatusConta;
import com.thebank.contas.entities.MovimentoConta.TipoMovimento;
import com.thebank.contas.events.ContaAprovadaEvent;
import com.thebank.contas.events.ContaRejeitadaEvent;
import com.thebank.contas.events.EventDispatcher;
import com.thebank.contas.events.MovimentacaoContaEvent;

import lombok.extern.slf4j.Slf4j;

import com.thebank.contas.entities.Gerente;
import com.thebank.contas.entities.MovimentoConta;

import org.springframework.data.domain.Example;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class ContasService {

	private final ContaRepository contaRepository;
	private final MovimentoContaRepository movimentoContaRepository;
	private final GerenteRepository gerenteRepository;
	private final EventDispatcher dispatcher;
	private RandomString randomString;

	public ContasService(
			ContaRepository contaRepository,
			GerenteRepository gerenteRepository,
			MovimentoContaRepository movimentoContaRepository,
			EventDispatcher dispatcher) {
		this.contaRepository = contaRepository;
		this.gerenteRepository = gerenteRepository;
		this.movimentoContaRepository = movimentoContaRepository;
		this.dispatcher = dispatcher;
		this.randomString = new RandomString(9);
	}

	public Conta criarConta(CriarContaDTO contaDto) {
		Conta c = new Conta();
		c.setId(randomString.nextString());

		Float salario = contaDto.getSalarioCliente();

		if (salario < 2000) {
			c.setLimite(0f);
		} else {
			c.setLimite(salario / 2);
		}

		c.setCpf(contaDto.getCpf());
		c.setStatus(StatusConta.PENDENTE_APROVACAO);
		c.setSaldo(0f);
		contaRepository.save(c);
		Gerente g = gerenteRepository.findLastLesserAccountsGerente();
		if (g != null) {
			c.setGerente(g);
		}
		return contaRepository.save(c);
	}

	public List<Conta> getAllContas(ListarContasDTO listarContasDTO) {
		StatusConta statusConta = listarContasDTO.getStatus();
		String gerenteCpf = listarContasDTO.getGerenteCpf();
		Conta cf = new Conta();
		if (statusConta != null) {
			cf.setStatus(statusConta);
		}

		if (gerenteCpf != null) {
			Gerente g = new Gerente();
			g.setCpf(gerenteCpf);
			cf.setGerente(g);
		}

		return contaRepository.findAll(Example.of(cf));
	}

	public List<Conta> getTop5(ListarContasDTO listarContasDTO) {
		String gerenteCpf = listarContasDTO.getGerenteCpf();
		Conta cf = new Conta();
		if (gerenteCpf != null) {
			Gerente g = new Gerente();
			g.setCpf(gerenteCpf);
			cf.setGerente(g);
		}

		return contaRepository.findTop5(gerenteCpf);
	}

	public Optional<Conta> getContaById(String contaId) {
		return contaRepository.findById(contaId);
	}

	public Optional<Conta> getContaByCpf(String cpf) {
		return contaRepository.findByCpf(cpf);
	}

	public Optional<Conta> getContaByCpfAndStatus(String cpf, StatusConta conta) {
		return contaRepository.findByCpf(cpf);
	}

	public Boolean removeConta(String contaId) {
		contaRepository.deleteById(contaId);
		return true;
	}

	// public Conta updateConta(AtualizarContaDTO contaDto) {
	// Conta conta = new Conta();
	// conta.setId(contaDto.getId());
	// conta.setCpf(contaDto.getCpf());
	// conta.setLimite(contaDto.getLimite());
	// return contaRepository.save(conta);
	// }

	public Conta aprovarConta(String contaId) {
		log.info("Conta aprovada: {}", contaId);
		Conta conta = contaRepository.findById(contaId).orElse(null);
		conta.setStatus(StatusConta.ATIVA);
		conta = contaRepository.save(conta);

		ContaAprovadaEvent cre = new ContaAprovadaEvent();
		cre.setCpf(conta.getCpf());
		cre.setId(conta.getId());
		dispatcher.sendContaAprovada(cre);

		return conta;
	}

	public Conta reprovarConta(RejeitarContaDTO rejectDto) {
		log.info("Conta reprovada: {}", rejectDto);
		Conta conta = contaRepository.findById(rejectDto.getId()).orElse(null);

		conta.setStatus(StatusConta.REJEITADA);
		conta.setMotivoRejeicao(rejectDto.getMotivoRejeicao());
		conta = contaRepository.save(conta);

		ContaRejeitadaEvent cre = new ContaRejeitadaEvent();
		cre.setCpf(conta.getCpf());
		cre.setId(conta.getId());
		cre.setMotivoRejeicao(conta.getMotivoRejeicao());
		dispatcher.sendContaRejeitada(cre);
		return conta;
	}

	public void storeGerente(StoreGerenteDTO gerenteDto) {
		Gerente gerente = new Gerente();
		gerente.setCpf(gerenteDto.getCpf());
		gerenteRepository.save(gerente);
	}

	public void unstoreGerente(UnstoreGerenteDTO gerenteDTO) {
		Gerente gerenteHerdeiro = gerenteRepository.findOneGerenteExceptGerenteCpf(gerenteDTO.getCpf());

		// if (gerenteHerdeiro == null) {
		// throw new Error("Gerente não pode ser removido, pois não possui outro
		// gerente");
		// }

		Conta contaQuery = new Conta();
		Gerente gerente = new Gerente();
		gerente.setCpf(gerenteDTO.getCpf());
		contaQuery.setGerente(gerente);
		for (Conta contaOrfa : contaRepository.findAll(Example.of(contaQuery))) {
			contaOrfa.setGerente(gerenteHerdeiro);
			contaRepository.save(contaOrfa);
		}

		gerenteRepository.deleteById(gerenteDTO.getCpf());
	}

	public Conta transferirEntreContas(TransferenciaDTO transferenciaDTO) {
		Conta contaOrigem = contaRepository.findById(transferenciaDTO.getContaOrigemId()).orElse(null);
		Float value = transferenciaDTO.getValor();
		if (contaOrigem == null) {
			throw new Error("Conta de origem não encontrada");
		}
		if (!contaOrigem.podeSacarValor(value)) {
			throw new Error("Saldo + Limite da conta origem são insuficientes para a operação");
		}
		Conta contaDestino = contaRepository.findById(transferenciaDTO.getContaDestinoId()).orElse(null);

		if (contaDestino == null) {
			throw new Error("Conta de destino não encontrada");
		}
		contaOrigem.setSaldo(contaOrigem.getSaldo() - value);
		contaDestino.setSaldo(contaDestino.getSaldo() + value);
		contaRepository.save(contaOrigem);
		contaRepository.save(contaDestino);

		MovimentacaoContaEvent mce = new MovimentacaoContaEvent();
		mce.setContaDestinoId(contaDestino.getId());
		mce.setContaOrigemId(contaOrigem.getId());
		mce.setTipoMovimento(TipoMovimento.TRANSFERENCIA);
		mce.setValor(transferenciaDTO.getValor());
		dispatcher.sendMovimentacaoConta(mce);

		return contaOrigem;
	}

	public Conta depositarEmConta(DepositarEmContaDTO depositarEmContaDTO) {
		Conta conta = contaRepository.findById(depositarEmContaDTO.getContaDestinoId()).orElse(null);
		Float novoSaldo = conta.getSaldo() + depositarEmContaDTO.getValor();
		conta.setSaldo(novoSaldo);

		MovimentacaoContaEvent mce = new MovimentacaoContaEvent();
		mce.setContaDestinoId(conta.getId());
		mce.setTipoMovimento(TipoMovimento.DEPOSITO);
		mce.setValor(depositarEmContaDTO.getValor());
		dispatcher.sendMovimentacaoConta(mce);

		contaRepository.save(conta);

		return conta;
	}

	public Conta sacarDeConta(SacarDeContaDTO sacarDeContaDTO) {
		Conta conta = contaRepository.findById(sacarDeContaDTO.getContaOrigemId()).orElse(null);

		if (!conta.podeSacarValor(sacarDeContaDTO.getValor())) {
			throw new Error("Saldo + Limite são insuficientes para a operação");
		}

		Float novoSaldo = conta.getSaldo() - sacarDeContaDTO.getValor();
		conta.setSaldo(novoSaldo);

		MovimentacaoContaEvent mce = new MovimentacaoContaEvent();
		mce.setContaOrigemId(conta.getId());
		mce.setTipoMovimento(TipoMovimento.SAQUE);
		mce.setValor(sacarDeContaDTO.getValor());
		dispatcher.sendMovimentacaoConta(mce);

		contaRepository.save(conta);

		return conta;
	}

	public void storeContaMovimentacao(MovimentacaoContaEvent event) {

		Conta contaOrigem = null;
		Conta contaDestino = null;

		if (event.getContaOrigemId() != null) {
			contaOrigem = contaRepository.findById(event.getContaOrigemId()).orElse(null);
		}

		if (event.getContaDestinoId() != null) {
			contaDestino = contaRepository.findById(event.getContaDestinoId()).orElse(null);
		}

		MovimentoConta movimentoConta = new MovimentoConta();
		movimentoConta.setContaDestino(contaDestino);
		movimentoConta.setContaOrigem(contaOrigem);
		movimentoConta.setTipoMovimento(event.getTipoMovimento());
		movimentoConta.setValor(event.getValor());
		movimentoContaRepository.save(movimentoConta);
	}

	public List<MovimentoConta> listarMovimentacoesConta(String id, Date dataInicial, Date dataFinal) {
		return movimentoContaRepository.listAllByContaInRange(id, dataInicial, dataFinal);
	}
}
