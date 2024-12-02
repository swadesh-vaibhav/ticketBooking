package com.bookmyshow.compete.ticketBooking.records;

import java.time.LocalDate;

public record Event(int id,
                    String name,
                    Organiser organiser,
                    Venue venue,
                    LocalDate startDate,
                    LocalDate endDate) {
}
