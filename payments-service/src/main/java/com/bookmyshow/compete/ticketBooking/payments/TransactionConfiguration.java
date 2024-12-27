package com.bookmyshow.compete.ticketBooking.payments;

import com.bookmyshow.compete.ticketBooking.registrations.RegistrationsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

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
    public Queue transactionSuccessQueue() {
        return new Queue("payment.success", true);
    }

    @Bean
    public Queue transactionFailureQueue() {
        return new Queue("payment.failure", true);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
