package com.airflux.saviour.controller.views.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaviourErrorRes {

    private String message;
    private String details;
    private Instant timestamp;
}
