package com.bookmyshow.compete.ticketBooking.registration;

import com.bookmyshow.compete.ticketBooking.events.Event;
import com.bookmyshow.compete.ticketBooking.events.Product;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping(path = "/registrations")
public class RegistrationController {

    private final RegistrationRepository registrationRepository;
    private final WebClient webClient;

    public RegistrationController(WebClient webClient, RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
        this.webClient = webClient;
    }

    @PostMapping
    public Registration create(@RequestBody @Valid Registration registration) {
        String ticketCode = UUID.randomUUID().toString();

        Product product = webClient.get()
                .uri("products/{id}", registration.productId())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(Product.class)
                .block();

        Event event = webClient.get()
                .uri("events/{id}", product.eventId())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(Event.class)
                .block();

        Registration newRegistration = new Registration(
                null,
                registration.productId(),
                event.name(),
                product.price(),
                ticketCode,
                registration.attendeeName()
        );

        return registrationRepository.save(newRegistration);
    }

    @GetMapping(path = "/{ticketCode}")
    public Registration get(@PathVariable("ticketCode") String ticketCode) {
        return registrationRepository.findByTicketCode(ticketCode)
                .orElseThrow(() -> new NoSuchElementException("Registration with ticket code " + ticketCode + " not found"));
    }

    @PutMapping
    public Registration update(@RequestBody Registration registration) {
        Registration existing = registrationRepository.findByTicketCode(registration.ticketCode())
                .orElseThrow(() -> new NoSuchElementException("Registration with ticket code " + registration.ticketCode() + " not found"));

        Registration updatedRegistration = new Registration(
                existing.id(), registration.productId(), registration.eventName(), registration.amount(), registration.attendeeName(), registration.ticketCode());

        return registrationRepository.save(updatedRegistration);
    }

    @DeleteMapping(path = "/{ticketCode}")
    public void delete(@PathVariable("ticketCode") String ticketCode) {
        registrationRepository.deleteByTicketCode(ticketCode);
    }
}
