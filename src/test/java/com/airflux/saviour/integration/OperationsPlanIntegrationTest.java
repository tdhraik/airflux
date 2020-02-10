package com.airflux.saviour.integration;

import com.airflux.saviour.controller.views.response.OperatingInstructionResList;
import com.airflux.saviour.domain.enums.AirportCode;
import com.airflux.saviour.domain.enums.ZoneName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class OperationsPlanIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenGetOperationPlanIsCalledWithValidRegistration_thenReturnOperatingInstructions() {
        // arrange
        String registration = "FL-0001";

        // act
        ResponseEntity<OperatingInstructionResList> operatingInstructions = restTemplate.getForEntity("/flights/operationsplan?registration=" + registration, OperatingInstructionResList.class);

        // assert
        assertThat(operatingInstructions.getStatusCodeValue()).isEqualTo(200);
        OperatingInstructionResList operatingInstructionResList = operatingInstructions.getBody();
        assertThat(operatingInstructionResList.getOperatingInstructions().size()).isEqualTo(5);
        assertThat(operatingInstructionResList.getOperatingInstructions().get(0).getDeparture()).isEqualTo(
                ZonedDateTime.of(LocalDate.of(2018, Month.APRIL, 13) , LocalTime.parse("10:00"), ZoneId.of(ZoneName.EUROPE_AMSTERDAM.toString())));
        assertThat(operatingInstructionResList.getOperatingInstructions().get(1).getDeparture()).isEqualTo(
                ZonedDateTime.of(LocalDate.of(2018, Month.APRIL, 13) , LocalTime.parse("15:00"), ZoneId.of(ZoneName.EUROPE_AMSTERDAM.toString())));
        assertThat(operatingInstructionResList.getOperatingInstructions().get(0).getDestination()).isEqualTo(AirportCode.MUC);
        assertThat(operatingInstructionResList.getOperatingInstructions().get(4).getOrigin()).isEqualTo(AirportCode.TXL);
    }

}
