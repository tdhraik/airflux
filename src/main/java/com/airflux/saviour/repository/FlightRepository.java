package com.airflux.saviour.repository;

import com.airflux.saviour.domain.Flight;
import com.airflux.saviour.domain.enums.AirportCode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends CrudRepository<Flight, Long> {

    List<Flight> findByBase(AirportCode base);

    Optional<Flight> findByRegistrationNumber(String registrationNo);
}
