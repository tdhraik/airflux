package com.airflux.saviour.domain;

import com.airflux.saviour.domain.enums.AirportCode;
import com.airflux.saviour.domain.enums.ZoneName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AirportZone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AirportCode airportCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ZoneName zoneName;
}
