package com.airflux.saviour.controller.views.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatingInstructionResList {
    private List<OperatingInstructionRes> operatingInstructions;
}
