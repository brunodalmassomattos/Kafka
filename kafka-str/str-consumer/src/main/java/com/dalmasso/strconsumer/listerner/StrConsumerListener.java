package com.dalmasso.strconsumer.listerner;

import com.dalmasso.strconsumer.custom.StrConsumerListenerCustom;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class StrConsumerListener {

    @SneakyThrows
    @StrConsumerListenerCustom(groupId = "group-1")
    public void create(String message) {
        log.info("CREATE ::: Receive message {}", message);

        throw new IllegalArgumentException("EXCEPTION...");
    }

    @StrConsumerListenerCustom(groupId = "group-1")
    public void log(String message) {
        log.info("LOG ::: Receive message {}", message);
    }

    @KafkaListener(groupId = "group-2", topics = "str-topic", containerFactory = "validMessageContainerFactory")
    public void history(String message) {
        log.info("HISTORY ::: Receive message {}", message);
    }

}
