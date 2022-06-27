package br.com.alura.ecommerce;

import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class LogService {

	public static void main(String[] args) throws InterruptedException {
		try (KaflaService kaflaService = new KaflaService(LogService.class.getSimpleName(), Pattern.compile("LOJA.*"), new LogService()::parse)) {
			kaflaService.run();
		}
	}
	
	private void parse(ConsumerRecord<String, String> registro) {
		System.out.println("--------------------- NOVO REGISTRO ---------------------");
		System.out.println("LOGANDO OS DADOS");
		
		System.out.println(registro.topic());
		System.out.println(registro.key());
		System.out.println(registro.value());
		System.out.println(registro.partition());
		System.out.println(registro.offset());
	}
}
