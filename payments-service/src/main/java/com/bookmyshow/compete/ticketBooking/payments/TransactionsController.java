package com.bookmyshow.compete.ticketBooking.payments;
import com.bookmyshow.compete.ticketBooking.registrations.Registration;
import com.bookmyshow.compete.ticketBooking.registrations.RegistrationsClient;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@CrossOrigin
@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionRepository transactionRepository;
    private final RegistrationsClient registrationsClient;
    private final RabbitTemplate rabbitTemplate;

    public TransactionsController(TransactionRepository transactionRepository, RegistrationsClient registrationsClient, RabbitTemplate rabbitTemplate) {
        this.transactionRepository = transactionRepository;
        this.registrationsClient = registrationsClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/create")
    public Transaction CreateTransaction(
            @RequestParam("registrationId") String registrationId,
            @RequestParam("amount")BigDecimal amount) {

        Transaction transaction = new Transaction();

        transaction.setRegistrationId(registrationId);
        transaction.setAmount(amount);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setTimeStamp(LocalDateTime.now());
        transaction.setProcessingTime(0);

        transaction = transactionRepository.save(transaction);

        Registration registration = registrationsClient.getRegistrationById(registrationId);

        Registration updatedRegistration = new Registration(
                registration.id(),
                registration.productId(),
                registration.eventName(),
                registration.amount(),
                registration.ticketCode(),
                registration.attendeeName(),
                transaction.getId(),
                registration.registrationStatus());
        
        registrationsClient.updateRegistration(updatedRegistration);

        return transaction;
    }

    @GetMapping("/succeed/{transactionId}")
    public Transaction SucceedImmediately(@PathVariable("transactionId") int transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NoSuchElementException("Transaction with id " + transactionId + " not found"));

        if(transaction.getStatus() == TransactionStatus.SUCCEEDED)
        {
            return transaction;
        }

        transaction.setStatus(TransactionStatus.SUCCEEDED);
        rabbitTemplate.convertAndSend("payment.success", transaction);

        return transactionRepository.save(transaction);
    }

    @GetMapping("/succeedInTen/{transactionId}")
    public Transaction SucceedInTen(@PathVariable("transactionId") int transactionId) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NoSuchElementException("Transaction with id " + transactionId + " not found"));

        if(transaction.getStatus() == TransactionStatus.SUCCEEDED)
        {
            return transaction;
        }

        transaction.setStatus(TransactionStatus.SUCCEEDED);
        transaction.setProcessingTime(10);
        rabbitTemplate.convertAndSend("payment.success", transaction);

        return transactionRepository.save(transaction);
    }
}