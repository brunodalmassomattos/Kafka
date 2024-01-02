package com.dalmasso.strconsumer.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ErrorCustomHandler implements KafkaListenerErrorHandler {

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException e) {
        log.error("EXCEPTION_HANDLER ::: Capturei um Erro ");
        log.error("Payload ::: {}", message.getPayload());
        log.error("Headers ::: {}", message.getHeaders());

        return null;
    }

}
