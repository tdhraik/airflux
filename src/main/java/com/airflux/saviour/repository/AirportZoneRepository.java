package com.airflux.saviour.repository;

import com.airflux.saviour.domain.AirportZone;
import com.airflux.saviour.domain.enums.AirportCode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AirportZoneRepository extends CrudRepository<AirportZone, Long> {

    List<AirportZone> findByAirportCodeIn(List<AirportCode> airportCodes);
}
