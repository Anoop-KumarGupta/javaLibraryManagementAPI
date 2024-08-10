package com.example.lms.dto;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AdminDTO {
    private String adminName;
    private String adminPosition;
}
