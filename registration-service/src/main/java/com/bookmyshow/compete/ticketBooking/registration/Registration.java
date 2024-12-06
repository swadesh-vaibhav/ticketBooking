package com.bookmyshow.compete.ticketBooking.registration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("registrations")
public record Registration(@Id String id,
                           @NotNull(message = "Please provide product ID") Integer productId,
                           String ticketCode,
                           @NotBlank(message = "Attendee Name cannot be blank") String attendeeName) {
}
