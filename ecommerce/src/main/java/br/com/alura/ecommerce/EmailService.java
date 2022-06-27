package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService {

	public static void main(String[] args) throws InterruptedException {
		try (KaflaService kafkaService = new KaflaService(EmailService.class.getSimpleName(), "LOJA_EMAIL_PEDIDO", new EmailService()::parse)) {
			kafkaService.run();
		}
	}

	private void parse(ConsumerRecord<String, String> registro) {
		try {
			System.out.println("--------------------- NOVO REGISTRO ---------------------");
			System.out.println("ENVIANDO EMAIL");
			System.out.println(registro.key());
			System.out.println(registro.value());
			System.out.println(registro.partition());
			System.out.println(registro.offset());

			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Email enviado");
	}
}
