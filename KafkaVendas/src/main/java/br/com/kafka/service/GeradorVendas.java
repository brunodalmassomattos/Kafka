package br.com.kafka.service;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import br.com.kafka.model.Venda;
import br.com.kafka.serializer.VendaSerializer;

public class GeradorVendas {

	private static Random random = new Random();
	private static long operacao = 0;
	private static BigDecimal valorDecimal = BigDecimal.valueOf(250);

	public static void main(String[] args) throws InterruptedException {
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, VendaSerializer.class.getName());

		try (KafkaProducer<String, Venda> producer = new KafkaProducer<>(properties)) {

			while (Boolean.TRUE) {
				ProducerRecord<String, Venda> record = new ProducerRecord<>("venda-ingresso", gerarVenda());
				producer.send(record);

				Thread.sleep(200);
				System.out.println(operacao);
			}
		}
	}

	private static Venda gerarVenda() {
		long cliente = random.nextLong();
		int qtdIngresso = random.nextInt(10);

		return new Venda(operacao++, cliente, qtdIngresso, valorDecimal);
	}

}
