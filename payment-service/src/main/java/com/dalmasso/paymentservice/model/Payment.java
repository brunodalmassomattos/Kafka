package com.dalmasso.paymentservice.model;

import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
public class Payment implements Serializable {

    private UUID id;
    private UUID idUser;
    private UUID idProduct;
    private String cardNumber;

}
