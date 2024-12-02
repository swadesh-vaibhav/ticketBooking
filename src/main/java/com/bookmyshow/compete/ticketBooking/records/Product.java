package com.bookmyshow.compete.ticketBooking.records;

import java.math.BigDecimal;

public record Product(int id,
                      int eventId,
                      String name,
                      String description,
                      BigDecimal price) {
}
