package com.introidx.grpcservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDetails {

    private String localDateTime;
    private String message;
    private String details;

}
