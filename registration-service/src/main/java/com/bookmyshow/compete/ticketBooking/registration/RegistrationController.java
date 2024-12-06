package com.bookmyshow.compete.ticketBooking.registration;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping(path = "/registrations")
public class RegistrationController {

    private final RegistrationRepository registrationRepository;

    public RegistrationController(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @PostMapping
    public Registration create(@RequestBody @Valid Registration registration) {
        String ticketCode = UUID.randomUUID().toString();

        Registration newRegistration = new Registration(
                null, registration.productId(), ticketCode, registration.attendeeName()
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
                existing.id(), registration.productId(), registration.attendeeName(), registration.ticketCode());

        return registrationRepository.save(updatedRegistration);
    }

    @DeleteMapping(path = "/{ticketCode}")
    public void delete(@PathVariable("ticketCode") String ticketCode) {
        registrationRepository.deleteByTicketCode(ticketCode);
    }
}
