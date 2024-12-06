package com.bookmyshow.compete.ticketBooking.records;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    public List<Event> findByOrganiserId(int organiserId);
}

