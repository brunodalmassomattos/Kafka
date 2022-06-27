package br.com.alura.ecommerce;

import java.util.Random;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class DetectorFraudService {

	public static void main(String[] args) throws InterruptedException {
		try (KaflaService kaflaService = new KaflaService(DetectorFraudService.class.getSimpleName(), "LOJA_NOVO_PEDIDO", new DetectorFraudService()::parse)) {
			kaflaService.run();
		}
	}

	private void parse(ConsumerRecord<String, String> registro) {
		try {
			System.out.println("--------------------- NOVO REGISTRO ---------------------");
			System.out.println("CHECANDO POR FRAUDE");
			System.out.println(registro.key());
			System.out.println(registro.value());
			System.out.println(registro.partition());
			System.out.println(registro.offset());

			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (new Random().nextBoolean()) {
			System.out.println("Ordem processada - Sem Fraude \n");
		} else {
			System.out.println("Ordem processada - Fraude - Atenção \n");
		}
	}
}
