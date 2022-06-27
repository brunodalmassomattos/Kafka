package br.com.alura.ecommerce;

import java.io.Closeable;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KaflaService implements Closeable {
	private final KafkaConsumer<String, String> consumer;
	private final IFuncaoConsumidor parse;

	public KaflaService(String grupoID, String topico, IFuncaoConsumidor parse) {
		this.parse = parse;
		this.consumer = new KafkaConsumer<>(properties(grupoID));
		this.consumer.subscribe(Arrays.asList(topico));
	}

	public KaflaService(String grupoID, Pattern compile, IFuncaoConsumidor parse) {
		this.parse = parse;
		this.consumer = new KafkaConsumer<>(properties(grupoID));
		consumer.subscribe(Pattern.compile("LOJA.*"));
	}

	public void run() {
		while (true) {
			ConsumerRecords<String, String> recordes = consumer.poll(Duration.ofMillis(100));
			if (!recordes.isEmpty()) {
				System.out.println("Chegou " + recordes.count() + " registros");

				for (ConsumerRecord<String, String> item : recordes) {
					parse.consume(item);
				}
			}
		}
	}

	private static Properties properties(String grupoID) {
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
		properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "5");
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, grupoID);

		return properties;
	}

	@Override
	public void close() {
		consumer.close();
	}

}
