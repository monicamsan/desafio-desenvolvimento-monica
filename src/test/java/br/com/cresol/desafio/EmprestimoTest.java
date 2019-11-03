package br.com.cresol.desafio;

import static br.com.cresol.desafio.constantes.EmprestimoConstantes.CPF;
import static br.com.cresol.desafio.constantes.EmprestimoConstantes.EMAIL;
import static br.com.cresol.desafio.constantes.EmprestimoConstantes.NOME;
import static br.com.cresol.desafio.util.EmprestimoUtils.getDataAtual;
import static br.com.cresol.desafio.util.EmprestimoUtils.getNumeroContrato;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import br.com.cresol.desafio.cliente.Cliente;
import br.com.cresol.desafio.dto.SimulacaoEmprestimo;
import br.com.cresol.desafio.dto.SimularEmprestimoPayload;
import br.com.cresol.desafio.service.EmprestimoService;

/**
 * 
 * @author monica
 *
 */
public class EmprestimoTest {

	EmprestimoService emprestimoService;

	@Before
	public void getEmprestimoService() {
		emprestimoService = new EmprestimoService();
	}

	@Test
	public void validarQtdMaximaParcelas() throws Exception {
		SimularEmprestimoPayload payload = configurarPayload();
		payload.setQuantidadeParcelas(25);
		assertEquals(true, emprestimoService.validarQtdMaximaParcelas(payload));
	}

	@Test
	public void validarParcela() throws Exception {
		SimulacaoEmprestimo simulacaoEmprestimo = new SimulacaoEmprestimo();
		SimularEmprestimoPayload payload = configurarPayload();
		payload.setQuantidadeParcelas(12);
		configurarSimulacao(simulacaoEmprestimo, payload);
		assertEquals(BigDecimal.valueOf(1216.0).setScale(3, BigDecimal.ROUND_HALF_EVEN),
				simulacaoEmprestimo.getValorParcela());
	}

	private void configurarSimulacao(SimulacaoEmprestimo simulacaoEmprestimo, SimularEmprestimoPayload payload)
			throws Exception {
		simulacaoEmprestimo.setCodigo(1L);
		Long numeroContrato = getNumeroContrato(simulacaoEmprestimo);
		BigDecimal valorParcela = emprestimoService.getValorParcela(payload);
		String dataAtual = getDataAtual();
		String dataSimulacao = emprestimoService.getDataSimulacao();
		BigDecimal taxaJurosEmpretimo = emprestimoService.getTaxaJuros(payload);

		simulacaoEmprestimo.setNumeroContrato(numeroContrato);
		simulacaoEmprestimo.setValorParcela(valorParcela);
		simulacaoEmprestimo.setDataSimulacao(dataAtual);
		simulacaoEmprestimo.setDataValidadeSimulacao(dataSimulacao);
		simulacaoEmprestimo.setValorContrato(payload.getValorContrato());
		simulacaoEmprestimo.setQuantidadeParcelas(payload.getQuantidadeParcelas());
		simulacaoEmprestimo.setValorParcela(valorParcela);
		simulacaoEmprestimo.setTaxaJurosEmprestimo(taxaJurosEmpretimo);

		Cliente cliente = getCliente();
		simulacaoEmprestimo.setCliente(cliente);
	}

	private Cliente getCliente() {
		Cliente cliente = new Cliente();
		cliente.setCodigo(1L);
		cliente.setCpf(CPF);
		cliente.setNome(NOME);
		cliente.setEmail(EMAIL);
		return cliente;
	}

	@Test
	public void validarTaxaDeJuros() throws Exception {
		SimulacaoEmprestimo simulacaoEmprestimo = new SimulacaoEmprestimo();
		SimularEmprestimoPayload payload = configurarPayload();
		payload.setQuantidadeParcelas(12);
		configurarSimulacao(simulacaoEmprestimo, payload);
		assertEquals(BigDecimal.valueOf(0.018), simulacaoEmprestimo.getTaxaJurosEmprestimo());
	}

	private SimularEmprestimoPayload configurarPayload() {
		SimularEmprestimoPayload payload = new SimularEmprestimoPayload();
		payload.setNome(NOME);
		payload.setCpf(CPF);
		payload.setEmail(EMAIL);
		payload.setValorContrato(BigDecimal.valueOf(12000));
		return payload;
	}

}
