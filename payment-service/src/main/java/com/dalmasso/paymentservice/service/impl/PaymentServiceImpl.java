package com.dalmasso.paymentservice.service.impl;

import com.dalmasso.paymentservice.model.Payment;
import com.dalmasso.paymentservice.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public void sendPayment(Payment payment) {
        log.info("PAYMENT_SERVICE_IMPL ::: Recebi pagamento {}", payment);
    }
}
