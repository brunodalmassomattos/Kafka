package br.com.alura.ecommerce;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NovaOrdem {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		try (KafkaDispatcher dispatcher = new KafkaDispatcher()) {
			
			String key;
			String value;
			String email = "Obrigado, seu pedido esta sendo processado";
			
			while (true) {
				key = UUID.randomUUID().toString();
				value = key + ",456,789";
				
				dispatcher.send("LOJA_NOVO_PEDIDO", key, value);
				dispatcher.send("LOJA_EMAIL_PEDIDO", key, email);
			}
		}
	}
}
