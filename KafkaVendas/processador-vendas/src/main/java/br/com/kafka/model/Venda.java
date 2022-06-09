package br.com.kafka.model;

import java.math.BigDecimal;

public class Venda {
	private Long operacao;
	private Long idCliente;
	private Integer qtdIngresso;
	private BigDecimal valorTotal;
	private String status;

	public Venda() {
	}

	public Venda(Long operacao, Long idCliente, Integer qtdIngresso, BigDecimal valorIngresso) {
		super();

		this.operacao = operacao;
		this.idCliente = idCliente;
		this.qtdIngresso = qtdIngresso;
		this.valorTotal = valorIngresso.multiply(BigDecimal.valueOf(qtdIngresso));
	}

	public Long getOperacao() {
		return operacao;
	}

	public void setOperacao(Long operacao) {
		this.operacao = operacao;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getQtdIngresso() {
		return qtdIngresso;
	}

	public void setQtdIngresso(Integer qtdIngresso) {
		this.qtdIngresso = qtdIngresso;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Venda [operacao=" + operacao + ", idCliente=" + idCliente + ", qtdIngresso=" + qtdIngresso
				+ ", valorTotal=" + valorTotal + ", status=" + status + "]";
	}

}
