package br.com.alura.ecommerce;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaDispatcher<T> implements Closeable {
	private final KafkaProducer<String, T> producer;

	public KafkaDispatcher() {
		this.producer = new KafkaProducer<>(properties());
	}

	private static Properties properties() {
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());

		return properties;
	}

	public void send(String topico, String key, T value) throws InterruptedException, ExecutionException {
		ProducerRecord<String, T> recordePedido = new ProducerRecord<>(topico, key, value);

		Callback callback = (data, ex) -> {
			if (ex != null) {
				ex.printStackTrace();
			}

			System.out.println("Enviado com sucesso " + data.topic() + ": Partição: " + data.partition() + "; offset: "
					+ data.offset() + "; timestamp: " + data.timestamp());
		};

		this.producer.send(recordePedido, callback).get();
	}

	@Override
	public void close() {
		this.producer.close();
	}
}
