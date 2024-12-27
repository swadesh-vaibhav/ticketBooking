package com.bookmyshow.compete.ticketBooking.registration;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RegistrationRepository extends MongoRepository<Registration, String> {

    @Cacheable(value = "registrations", key = "#ticketCode")
    Optional<Registration> findByTicketCode(String ticketCode);

    Optional<Registration> findById(String id);

    void deleteByTicketCode(String ticketCode);
}
