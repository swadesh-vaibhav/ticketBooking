package com.bookmyshow.compete.ticketBooking.registration;

import com.bookmyshow.compete.ticketBooking.events.Event;
import com.bookmyshow.compete.ticketBooking.events.EventsClient;
import com.bookmyshow.compete.ticketBooking.events.Product;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(path = "/registrations")
public class RegistrationController {

    private final RegistrationRepository registrationRepository;
    private final EventsClient eventsClient;

    public RegistrationController(EventsClient eventsClient, RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
        this.eventsClient = eventsClient;
    }

    @PostMapping
    public Registration create(@RequestBody @Valid Registration registration) {
        String ticketCode = UUID.randomUUID().toString();

        Product product = eventsClient.getProductById(registration.productId());

        Event event = eventsClient.getEventById(product.eventId());

        Registration newRegistration = new Registration(
                null,
                registration.productId(),
                event.name(),
                product.price(),
                ticketCode,
                registration.attendeeName(),
                null,
                RegistrationStatus.PENDING
        );

        return registrationRepository.save(newRegistration);
    }

    @GetMapping(path = "/{ticketCode}")
    public Registration get(@PathVariable("ticketCode") String ticketCode) {
        return registrationRepository.findByTicketCode(ticketCode)
                .orElseThrow(() -> new NoSuchElementException("Registration with ticket code " + ticketCode + " not found"));
    }

    @GetMapping(path = "/ticketStatus")
    public Registration getById(@RequestParam("id") String ticketId) {
        return registrationRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Registration with id " + ticketId + " not found"));
    }

    @PutMapping
    public Registration update(@RequestBody Registration registration) {
        Registration existing = registrationRepository.findById(registration.id())
                .orElseThrow(() -> new NoSuchElementException("Registration with id " + registration.id() + " not found"));

        Registration updatedRegistration = new Registration(
            existing.id(),
            registration.productId(),
            registration.eventName(),
            registration.amount(),
            registration.attendeeName(),
            registration.ticketCode(),
            registration.transactionId(),
            registration.registrationStatus());

        return registrationRepository.save(updatedRegistration);
    }

    @DeleteMapping(path = "/{ticketCode}")
    public void delete(@PathVariable("ticketCode") String ticketCode) {
        registrationRepository.deleteByTicketCode(ticketCode);
    }
}
