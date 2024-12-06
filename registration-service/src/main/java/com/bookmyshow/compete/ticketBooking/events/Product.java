package com.bookmyshow.compete.ticketBooking.events;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record Product(
        int id,
        @NotNull(message = "Please provide event ID while creating product") Integer eventId,
        String name,
        String description,
        BigDecimal price) {
}
