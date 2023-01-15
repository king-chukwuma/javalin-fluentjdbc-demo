package com.francis.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenericExceptionResponse {
    private int code;
    private String status;
    private String reason;
}
