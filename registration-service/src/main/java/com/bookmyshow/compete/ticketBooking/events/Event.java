package com.bookmyshow.compete.ticketBooking.events;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record Event(
        int id,
        @NotBlank(message = "Event name cannot be blank")String name,
        Organiser organiser,
        Venue venue,
        LocalDate startDate,
        LocalDate endDate) {
}
