package br.com.alura.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		try (KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>()) {
			try (KafkaDispatcher<Email> emailDispatcher = new KafkaDispatcher<>()) {

				String key = UUID.randomUUID().toString();

				for (int i = 0; i < 10; i++) {
					String orderID = UUID.randomUUID().toString();
					BigDecimal amount = new BigDecimal((Math.random() * 5000) + 1);

					Order order = new Order(key, orderID, amount);
					orderDispatcher.send("LOJA_NOVO_PEDIDO", key, order);

					emailDispatcher.send("LOJA_EMAIL_PEDIDO", key, new Email());
				}
			}
		}
	}
}
