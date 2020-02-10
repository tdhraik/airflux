package com.airflux.saviour.repository;

import com.airflux.saviour.domain.Flight;
import com.airflux.saviour.domain.enums.AirportCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FlightRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;

    @Test
    public void whenGetFlightByBaseIsCalled_thenReturnFlightWithGivenBase() {
        // arrange
        AirportCode base = AirportCode.TXL;

        // act
        List<Flight> flight = flightRepository.findByBase(base);

        // assert
        assertThat(flight.size()).isEqualTo(1);
        assertThat(flight.get(0).getBase()).isEqualTo(base);
    }

    @Test
    public void whenGetFlightByRegistrationIsCalledWithNonExistentNo_thenReturnNoResult() {
        // arrange
        String registration = "FL-0005";

        // act
        Optional<Flight> flight = flightRepository.findByRegistrationNumber(registration);

        // assert
        assertThat(flight.isPresent()).isEqualTo(false);
    }

    @Test
    public void whenGetFlightByRegistrationIsCalled_thenReturnFlightWithGivenRegistration() {
        // arrange
        String registration = "FL-0004";

        // act
        Optional<Flight> flight = flightRepository.findByRegistrationNumber(registration);

        // assert
        assertThat(flight.get().getRegistrationNumber()).isEqualTo(registration);
    }
}
