package com.bookmyshow.compete.ticketBooking.handlers;

import com.bookmyshow.compete.ticketBooking.payments.Transaction;
import com.bookmyshow.compete.ticketBooking.registrations.Registration;
import com.bookmyshow.compete.ticketBooking.registrations.RegistrationsClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionSuccessHandler {

    private final RegistrationsClient registrationsClient;

    public TransactionSuccessHandler(RegistrationsClient registrationsClient) {
        this.registrationsClient = registrationsClient;
    }

    @RabbitListener(queues = "payment.success")
    public void handleTransactionSuccess(Transaction transaction) {
        try {
            Thread.sleep(transaction.getProcessingTime() * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Registration existing = registrationsClient.getRegistrationById(transaction.getRegistrationId());
        Registration updated = new Registration(
                existing.id(),
                existing.productId(),
                existing.eventName(),
                existing.amount(),
                existing.ticketCode(),
                existing.attendeeName(),
                transaction.getId(),
                com.bookmyshow.compete.ticketBooking.registrations.RegistrationStatus.CONFIRMED
        );
        registrationsClient.updateRegistration(updated);
    }
}