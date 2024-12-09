package com.bookmyshow.compete.ticketBooking.records;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotBlank(message = "Country must not be blank") String getCountry() {
        return country;
    }

    public void setCountry(@NotBlank(message = "Country must not be blank") String country) {
        this.country = country;
    }

    public @NotBlank(message = "City must not be blank") String getCity() {
        return city;
    }

    public void setCity(@NotBlank(message = "City must not be blank") String city) {
        this.city = city;
    }

    public @NotBlank(message = "Street must not be blank") String getStreet() {
        return street;
    }

    public void setStreet(@NotBlank(message = "Street must not be blank") String street) {
        this.street = street;
    }

    public @NotBlank(message = "Venue name must not be blank") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Venue name must not be blank") String name) {
        this.name = name;
    }
}
