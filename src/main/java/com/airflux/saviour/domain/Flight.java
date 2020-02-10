package com.airflux.saviour.domain;

import com.airflux.saviour.domain.enums.AirportCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AirportCode base;

    private String aircraftName;

    private String equipment;

    private String registrationNumber;

    @OneToMany(mappedBy = "flight", fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private List<FlightSchedule> schedules;
}
