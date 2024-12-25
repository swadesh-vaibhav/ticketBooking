package com.bookmyshow.compete.ticketBooking.registrations;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PutExchange;

public interface RegistrationsClient {

    @GetExchange("/ticketStatus")
    Registration getRegistrationById(@RequestParam("id") String registrationId);

    @PutExchange()
    void updateRegistration(@RequestBody Registration registration);
}
