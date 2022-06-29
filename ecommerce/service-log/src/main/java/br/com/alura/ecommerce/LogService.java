package br.com.alura.ecommerce;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

public class LogService {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws InterruptedException {
		try (KafkaService kaflaService = new KafkaService(
				LogService.class.getSimpleName(), 
				Pattern.compile("LOJA.*"), 
				new LogService()::parse,
				String.class,
				Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()))) {
			kaflaService.run();
		}
	}
	
	private void parse(ConsumerRecord<String, String> registro) {
		System.out.println("--------------------- NOVO REGISTRO ---------------------");
		System.out.println("Topic....: " + registro.topic());
		System.out.println("Key......: " + registro.key());
		System.out.println("Value....: " + registro.value());
		System.out.println("Partition: " + registro.partition());
		System.out.println("Offset...: " + registro.offset());
	}
}
