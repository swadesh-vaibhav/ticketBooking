package com.bookmyshow.compete.ticketBooking.records;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrganiserRepository {
    private final List<Organiser> organizers = List.of(
            new Organiser(101, "Globomantics", "Globomantics Technology Corporation"),
            new Organiser(102, "Carved Rock", "Carved Rock Sports Equipment"));

    public List<Organiser> findAll() {
        return organizers;
    }
}