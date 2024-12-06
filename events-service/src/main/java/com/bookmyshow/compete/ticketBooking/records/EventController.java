package com.bookmyshow.compete.ticketBooking.records;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class EventController {

    private final OrganiserRepository organiserRepository;
    private final EventRepository eventRepository;
    private final ProductRepository productRepository;

    public EventController(OrganiserRepository organiserRepository,
                           EventRepository eventRepository,
                           ProductRepository productRepository) {
        this.organiserRepository = organiserRepository;
        this.eventRepository = eventRepository;
        this.productRepository = productRepository;
    }

    @GetMapping(path = "/organizers")
    public List<Organiser> getOrganizers() {
        return organiserRepository.findAll();
    }

    @GetMapping(path = "/events") // ?organiserId=12
    public List<Event> getEventsByOrganizer(@RequestParam("organizerId") int organizerId) {
        return eventRepository.findByOrganiserId(organizerId);
    }

    @GetMapping(path = "/events/{id}")
    public Event getEventById(@PathVariable("id") int eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("Event with id " + eventId + " not found"));
    }

    @GetMapping(path = "/products")
    public List<Product> getProductsByEvent(@RequestParam("eventId") int eventId) {
        return productRepository.findByEventId(eventId);
    }
}
