package com.bookmyshow.compete.ticketBooking.registration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Registration(Integer id,
                           @NotNull(message = "Please provide product ID") Integer productId,
                           String ticketCode,
                           @NotBlank(message = "Attendee Name cannot be blank") String attendeeName) {
}
