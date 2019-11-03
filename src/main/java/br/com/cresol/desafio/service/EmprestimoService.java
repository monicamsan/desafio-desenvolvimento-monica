package br.com.cresol.desafio.service;

import static br.com.cresol.desafio.cliente.ClienteFactory.codigoEmprestimo;
import static br.com.cresol.desafio.util.EmprestimoUtils.getDataAtual;
import static br.com.cresol.desafio.util.EmprestimoUtils.getNumeroContrato;

import java.math.BigDecimal;
import java.rmi.ServerException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import br.com.cresol.desafio.cliente.Cliente;
import br.com.cresol.desafio.cliente.ClienteFactory;
import br.com.cresol.desafio.dto.SimulacaoEmprestimo;
import br.com.cresol.desafio.dto.SimularEmprestimoPayload;
import br.com.cresol.desafio.util.EmprestimoUtils;

/**
 * @author monica
 *
 */
public class EmprestimoService {

	static List<Cliente> clientes;
	static List<SimulacaoEmprestimo> simulacao;

	static {
		simulacao = new ArrayList<SimulacaoEmprestimo>();
		clientes = new ArrayList<Cliente>();

		Cliente c1 = ClienteFactory.getCliente1();
		clientes.add(c1);

		Cliente c2 = ClienteFactory.getCliente2();
		clientes.add(c2);

		Cliente c3 = ClienteFactory.getCliente3();
		clientes.add(c3);

	}

	public SimulacaoEmprestimo simular(SimularEmprestimoPayload payload) throws Exception {
		SimulacaoEmprestimo simulacaoEmprestimo = null;
		try {
			validarCPF(payload);
			validarEmail(payload);
			validarQtdParcelas(payload);
			simulacaoEmprestimo = new SimulacaoEmprestimo();
			if (isClienteCarregado(payload)) {
				carregaCliente(simulacaoEmprestimo, payload);
			} else {
				inserirUsuario(payload);
			}
			simulacaoEmprestimo.setCodigo(codigoEmprestimo++);
			Long numeroContrato = getNumeroContrato(simulacaoEmprestimo);
			BigDecimal valorParcela = getValorParcela(payload);
			String dataAtual = getDataAtual();
			String dataSimulacao = getDataSimulacao();
			BigDecimal taxaJurosEmpretimo = getTaxaJuros(payload);

			simulacaoEmprestimo.setNumeroContrato(numeroContrato);
			simulacaoEmprestimo.setValorParcela(valorParcela);
			simulacaoEmprestimo.setDataSimulacao(dataAtual);
			simulacaoEmprestimo.setDataValidadeSimulacao(dataSimulacao);
			simulacaoEmprestimo.setValorContrato(payload.getValorContrato());
			simulacaoEmprestimo.setQuantidadeParcelas(payload.getQuantidadeParcelas());
			simulacaoEmprestimo.setValorParcela(valorParcela);
			simulacaoEmprestimo.setTaxaJurosEmprestimo(taxaJurosEmpretimo);
			simulacao.add(simulacaoEmprestimo);
		} catch (Exception e) {
			throw new Exception("Erro ao simular emprestimo.", e);
		}
		return simulacaoEmprestimo;
	}

	public String getDataSimulacao() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DATE, 30);
		String dataSimulacao = dateFormat.format(cal.getTime());

		return dataSimulacao;
	}

	public BigDecimal getValorParcela(SimularEmprestimoPayload payload) throws Exception {
		BigDecimal qtParcelas = BigDecimal.valueOf(payload.getQuantidadeParcelas());

		BigDecimal valorParcelaPasso1 = qtParcelas.multiply(getTaxaJuros(payload));
		BigDecimal valorParcelaPasso2 = BigDecimal.ONE.add(valorParcelaPasso1);
		BigDecimal valorParcelaPasso3 = payload.getValorContrato().multiply(valorParcelaPasso2);
		BigDecimal valorParcela = valorParcelaPasso3.divide(qtParcelas);

		return valorParcela;
	}

	public BigDecimal getTaxaJuros(SimularEmprestimoPayload payload) throws Exception {
		BigDecimal valorContratado = payload.getValorContrato();

		BigDecimal taxaDeJuros = BigDecimal.ZERO;

		if (BigDecimal.valueOf(1000).compareTo(valorContratado) <= 0) {
			taxaDeJuros = BigDecimal.valueOf(0.018d);
		} else {
			taxaDeJuros = BigDecimal.valueOf(0.03d);
		}

		if (payload.getQuantidadeParcelas() > 12) {
			taxaDeJuros.add(BigDecimal.valueOf(0.005d));
		}
		return taxaDeJuros;
	}


	private void carregaCliente(SimulacaoEmprestimo simulacaoEmprestimo, SimularEmprestimoPayload payload)
			throws Exception {
		Cliente cliente = null;
		for (Iterator<Cliente> iterator = clientes.iterator(); iterator.hasNext();) {
			Cliente clienteAux = iterator.next();
			if (clienteAux.getCpf().equals(payload.getCpf())) {
				cliente = clienteAux;
				break;
			}
		}
		if (cliente == null) {
			throw new ServerException("Cliente não encontrado !!!");
		}
		simulacaoEmprestimo.setCliente(cliente);
	}

	private void validarQtdParcelas(SimularEmprestimoPayload payload) throws ServerException {
		if (validarQtdMaximaParcelas(payload)) {
			throw new ServerException("Quantidade máxima de parcelas: 24 !!!");
		}
	}

	private void validarEmail(SimularEmprestimoPayload payload) throws Exception, ServerException {
		if (EmprestimoUtils.validarEmail(payload.getEmail()) == false) {
			throw new ServerException("E-mail inválido");
		}
	}

	private void validarCPF(SimularEmprestimoPayload payload) throws ServerException {
		if (EmprestimoUtils.validarCPF(payload.getCpf().toString()) == false) {
			throw new ServerException("CPF inválido");
		}
	}

	public boolean validarQtdMaximaParcelas(SimularEmprestimoPayload payload) {
		return (payload.getQuantidadeParcelas() != null && payload.getQuantidadeParcelas() > 24);
	}

	private void inserirUsuario(SimularEmprestimoPayload payload) throws Exception {
		Cliente cliente = new Cliente();
		Long codigo = Long.valueOf(clientes.size() + 1);
		cliente.setCodigo(codigo);
		cliente.setCpf(payload.getCpf());
		cliente.setNome(payload.getNome());
		cliente.setEmail(payload.getEmail());

		clientes.add(cliente);
	}

	private boolean isClienteCarregado(SimularEmprestimoPayload payload) throws Exception {
		for (Iterator<Cliente> iterator = clientes.iterator(); iterator.hasNext();) {
			Cliente cliente = iterator.next();
			if (cliente.getCpf().equals(payload.getCpf())) {
				return true;
			}
		}
		return false;
	}

}
