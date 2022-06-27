package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface IFuncaoConsumidor {
	void consume(ConsumerRecord<String, String> registro);
}
