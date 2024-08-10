package com.example.lms.controller;

import com.example.lms.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/data")
public class DataController {
    @Autowired
    private DataService dataService;

    @GetMapping
    public String getDataCsv() throws Exception {
        return dataService.exportAllDataToCsv();
    }
}
