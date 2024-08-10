package com.example.lms.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class AdminCSV {
    @CsvBindByName(column="admin_id")
    private long adminId;

    @CsvBindByName(column="admin_name")
    private String adminName;

    @CsvBindByName(column="admin_position")
    private String adminPosition;
}
