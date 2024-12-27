package com.bookmyshow.compete.ticketBooking.handlers;

import com.bookmyshow.compete.ticketBooking.payments.Transaction;
import com.bookmyshow.compete.ticketBooking.registrations.Registration;
import com.bookmyshow.compete.ticketBooking.registrations.RegistrationStatus;
import com.bookmyshow.compete.ticketBooking.registrations.RegistrationsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(TransactionFailureHandler.class);

    private final RegistrationsClient registrationsClient;

    public TransactionFailureHandler(RegistrationsClient registrationsClient) {
        this.registrationsClient = registrationsClient;
    }

    @RabbitListener(queues = "payment.failure")
    public void handleTransactionFailure(Transaction transaction) {

        logger.info("Transaction failure handler started for transaction ID: {}", transaction.getId());

        try {
            Thread.sleep(transaction.getProcessingTime() * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Registration existing = registrationsClient.getRegistrationById(transaction.getRegistrationId());
        logger.info("Fetched existing registration for transaction ID: {}", transaction.getId());
        Registration updated = new Registration(
                existing.id(),
                existing.productId(),
                existing.eventName(),
                existing.amount(),
                existing.ticketCode(),
                existing.attendeeName(),
                transaction.getId(),
                RegistrationStatus.FAILED
        );
        registrationsClient.updateRegistration(updated);

        logger.info("Updated registration for transaction ID: {}", transaction.getId());

        logger.info("Transaction failure handler completed for transaction ID: {}", transaction.getId());
    }
}