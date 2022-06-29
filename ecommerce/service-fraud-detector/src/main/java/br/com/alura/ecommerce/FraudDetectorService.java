package br.com.alura.ecommerce;

import java.util.Map;
import java.util.Random;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class FraudDetectorService {
	
	public static void main(String[] args) throws InterruptedException {
		FraudDetectorService fraudService = new FraudDetectorService();

		try (KafkaService<Order> kaflaService = new KafkaService<>(
				FraudDetectorService.class.getSimpleName(),
				"LOJA_NOVO_PEDIDO", 
				fraudService::parse, 
				Order.class, 
				Map.of())) {
			kaflaService.run();
		}
	}

	private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>();
	
	private void parse(ConsumerRecord<String, Order> registro) throws Exception {
		try {
			System.out.println("--------------------- NOVO REGISTRO ---------------------");
			System.out.println("CHECANDO POR FRAUDE");
			System.out.println(registro.key());
			System.out.println(registro.value());
			System.out.println(registro.partition());
			System.out.println(registro.offset());

			Thread.sleep(500);
			
			Order ordem = registro.value();
			if (new Random().nextBoolean()) {
				System.out.println("Ordem processada - Sem Fraude \n");
				orderDispatcher.send("LOJA_PEDIDO_APROVADO", ordem.getUserId(), ordem);
			} else {
				System.out.println("Ordem processada - Fraude - Atenção \n");
				orderDispatcher.send("LOJA_PEDIDO_REPROVADO", ordem.getUserId(), ordem);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
