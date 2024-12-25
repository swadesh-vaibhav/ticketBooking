package com.bookmyshow.compete.ticketBooking.payments;

import com.bookmyshow.compete.ticketBooking.registrations.RegistrationsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.amqp.core.Queue;

@Configuration
public class TransactionConfiguration {

    @Bean
    public WebClient webClient(@Value("${tickets.registration.url}") String baseUrl) {
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    public RegistrationsClient eventsClient(WebClient webClient) {
        return HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build()
                .createClient(RegistrationsClient.class);
    }

    @Bean
    public Queue transactionQueue() {
        return new Queue("transactionQueue", true);
    }
}
