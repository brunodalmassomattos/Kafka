package br.com.alura.ecommerce;

import java.math.BigDecimal;

public class Order {
	private String orderId;
	private BigDecimal amount;
	private String email;

	public Order(String orderId, BigDecimal amount, String email) {
		this.orderId = orderId;
		this.amount = amount;
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", amount=" + amount + ", email=" + email + "]";
	}

}
