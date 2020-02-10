package com.airflux.saviour.domain;

import com.airflux.saviour.domain.enums.AirportCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FlightSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AirportCode origin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AirportCode destination;

    @Column(nullable = false)
    private Integer flightTime;

    @Column(nullable = false)
    private String departureTime;

    @ManyToOne
    private Flight flight;
}
