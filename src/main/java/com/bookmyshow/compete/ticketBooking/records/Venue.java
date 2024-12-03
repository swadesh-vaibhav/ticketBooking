package com.bookmyshow.compete.ticketBooking.records;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Venue name must not be blank")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Street must not be blank")
    @Column(nullable = false)
    private String street;

    @NotBlank(message = "City must not be blank")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "Country must not be blank")
    @Column(nullable = false)
    private String country;
}
