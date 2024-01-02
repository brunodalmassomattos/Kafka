package com.dalmasso.paymentservice.service.impl;

import com.dalmasso.paymentservice.model.Payment;
import com.dalmasso.paymentservice.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Log4j2
@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final KafkaTemplate<String, Serializable> kafkaTemplate;

    @Override
    @SneakyThrows
    public void sendPayment(Payment payment) {
        log.info("PAYMENT_SERVICE_IMPL ::: Recebi pagamento {}", payment);

        // PARA SIMULAR UM PROCESSAMENT
        Thread.sleep(1000);

        log.info("Enviando o pagamento");
        kafkaTemplate.send("payment-topic", payment);
    }
}
