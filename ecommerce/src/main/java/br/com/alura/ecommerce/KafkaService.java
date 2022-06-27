package br.com.alura.ecommerce;

import java.io.Closeable;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

@SuppressWarnings("rawtypes")
public class KafkaService<T> implements Closeable {
	private final KafkaConsumer<String, T> consumer;
	private final ConsumerFunction parse;

	public KafkaService(String groupId, String topico, ConsumerFunction parse, Class<T> type, Map<String, String> properties) {
		this.parse = parse;
		this.consumer = new KafkaConsumer<>(getProperties(type, groupId, properties));
		this.consumer.subscribe(Arrays.asList(topico));
	}

	public KafkaService(String grupoID, Pattern compile, ConsumerFunction parse, Class<T> type, Map<String, String> properties) {
		this.parse = parse;
		this.consumer = new KafkaConsumer<>(getProperties(type, grupoID, properties));
		consumer.subscribe(Pattern.compile("LOJA.*"));
	}

	@SuppressWarnings("unchecked")
	public void run() {
		while (true) {
			ConsumerRecords<String, T> recordes = consumer.poll(Duration.ofMillis(100));
			if (!recordes.isEmpty()) {
				System.out.println("Chegou " + recordes.count() + " registros");

				for (ConsumerRecord<String, T> item : recordes) {
					parse.consume(item);
				}
			}
		}
	}

	private Properties getProperties(Class<T> type, String grupoID, Map<String, String> overrideProperties) {
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
		properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "5");
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, grupoID);
		properties.setProperty(GsonDeserializer.TYPE_CONFIG, type.getName());
		properties.putAll(overrideProperties);

		return properties;
	}

	@Override
	public void close() {
		consumer.close();
	}

}
