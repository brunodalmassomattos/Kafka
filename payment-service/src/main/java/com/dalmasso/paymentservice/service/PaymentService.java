package com.dalmasso.paymentservice.service;

import com.dalmasso.paymentservice.model.Payment;

public interface PaymentService {
    void sendPayment(Payment payment);
}
