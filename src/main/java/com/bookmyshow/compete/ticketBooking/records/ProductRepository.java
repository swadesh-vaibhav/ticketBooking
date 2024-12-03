package com.bookmyshow.compete.ticketBooking.records;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    public List<Product> findByEventId(int eventId);
}
