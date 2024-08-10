package com.example.lms.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AdminResponeDto extends AdminDTO{
    private String adminDescription;
}
