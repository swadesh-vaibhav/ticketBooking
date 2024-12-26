package com.bookmyshow.compete.ticketBooking.registrations;

import java.math.BigDecimal;

public record Registration(String id,
                           Integer productId,
                           String eventName,
                           BigDecimal amount,
                           String ticketCode,
                           String attendeeName,
                           String transactionId,
                           RegistrationStatus registrationStatus
) {
}
