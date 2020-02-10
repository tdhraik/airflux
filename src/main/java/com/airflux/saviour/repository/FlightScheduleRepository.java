package com.airflux.saviour.repository;

import com.airflux.saviour.domain.Flight;
import com.airflux.saviour.domain.FlightSchedule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FlightScheduleRepository extends CrudRepository<FlightSchedule, Long> {

    List<FlightSchedule> findByFlightIn(List<Flight> flights);
}
