package com.ap.dispatcherservice.function;

import com.ap.dispatcherservice.model.OrderAcceptedMessage;
import com.ap.dispatcherservice.model.OrderDispatchedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class DispatchingFunctions {

    private static final Logger log =
            LoggerFactory.getLogger(DispatchingFunctions.class);

    @Bean
    public Function<OrderAcceptedMessage, Long> pack() {
        return orderAcceptedMessage -> {
            log.info("The order with id {} is packed.",
                     orderAcceptedMessage.orderId());
          return orderAcceptedMessage.orderId();
        };
    }

    @Bean
    public Function<Flux<Long>, Flux<OrderDispatchedMessage>> label() {

        return fluxOrderId -> fluxOrderId.map(orderId -> {
            log.info("The order with id {} is labeled.", orderId);
           return new OrderDispatchedMessage(orderId);
        });
    }

}
