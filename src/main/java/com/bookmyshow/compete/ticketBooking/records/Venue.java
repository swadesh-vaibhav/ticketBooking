package com.bookmyshow.compete.ticketBooking.records;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="venues")
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
