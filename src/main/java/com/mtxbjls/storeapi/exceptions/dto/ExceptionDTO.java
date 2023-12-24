package com.mtxbjls.storeapi.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExceptionDTO {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
}
