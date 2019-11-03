package br.com.cresol.desafio.dto;

import java.math.BigDecimal;

/**
 * @author monica
 *
 */
public class SimularEmprestimoPayload {

	private String nome;
	private Long cpf;
	private String email;
	private BigDecimal valorContrato;
	private Integer quantidadeParcelas;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getCpf() {
		return cpf;
	}
	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	
}
