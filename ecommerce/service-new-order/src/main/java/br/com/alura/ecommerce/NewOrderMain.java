package br.com.alura.ecommerce;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		try (KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>()) {
			try (KafkaDispatcher<Email> emailDispatcher = new KafkaDispatcher<>()) {
				String email = getSaltString() + "@email.com";

				for (int i = 0; i < 10; i++) {
					String orderID = UUID.randomUUID().toString();
					BigDecimal amount = new BigDecimal((Math.random() * 5000) + 1);

					Order order = new Order(orderID, amount, email);
					orderDispatcher.send("LOJA_NOVO_PEDIDO", email, order);

					emailDispatcher.send("LOJA_EMAIL_PEDIDO", email, new Email());
				}
			}
		}
	}

	protected static String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 10) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}

		return salt.toString();
	}

}
