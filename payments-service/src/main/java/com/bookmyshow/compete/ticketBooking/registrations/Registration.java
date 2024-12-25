package com.bookmyshow.compete.ticketBooking.registrations;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public record Registration(@Id String id,
                           Integer productId,
                           String eventName,
                           BigDecimal amount,
                           String ticketCode,
                           String attendeeName,
                           Integer transactionId,
                           RegistrationStatus registrationStatus
) {
}
