package br.com.cresol.desafio.dto;

import java.math.BigDecimal;

import br.com.cresol.desafio.cliente.Cliente;

/**
 * @author monica
 *
 */
public class SimulacaoEmprestimo {

	private Long codigo;
	private Long numeroContrato;
	private String dataSimulacao;
	private String dataValidadeSimulacao;
	private BigDecimal valorContrato;
	private Integer quantidadeParcelas;
	private BigDecimal valorParcela;
	private BigDecimal taxaJurosEmprestimo;
	private Cliente cliente;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public Long getNumeroContrato() {
		return numeroContrato;
	}
	public void setNumeroContrato(Long numeroContrato) {
		this.numeroContrato = numeroContrato;
	}
	public String getDataSimulacao() {
		return dataSimulacao;
	}
	public void setDataSimulacao(String dataSimulacao) {
		this.dataSimulacao = dataSimulacao;
	}
	public String getDataValidadeSimulacao() {
		return dataValidadeSimulacao;
	}
	public void setDataValidadeSimulacao(String dataValidadeSimulacao) {
		this.dataValidadeSimulacao = dataValidadeSimulacao;
	}
	public BigDecimal getValorContrato() {
		return valorContrato;
	}
	public void setValorContrato(BigDecimal valorContrato) {
		this.valorContrato = valorContrato;
	}
	public Integer getQuantidadeParcelas() {
		return quantidadeParcelas;
	}
	public void setQuantidadeParcelas(Integer quantidadeParcelas) {
		this.quantidadeParcelas = quantidadeParcelas;
	}
	public BigDecimal getValorParcela() {
		return valorParcela;
	}
	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}
	public BigDecimal getTaxaJurosEmprestimo() {
		return taxaJurosEmprestimo;
	}
	public void setTaxaJurosEmprestimo(BigDecimal taxaJurosEmprestimo) {
		this.taxaJurosEmprestimo = taxaJurosEmprestimo;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
