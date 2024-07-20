package com.example.stayease.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int availableRooms;

    @OneToMany(mappedBy = "hotel")
    private Set<Booking> bookings;
}
