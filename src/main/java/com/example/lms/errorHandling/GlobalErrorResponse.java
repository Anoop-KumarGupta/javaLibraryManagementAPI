package com.example.lms.errorHandling;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
