package com.slim.agent.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DutyAssignment {

    private Long id;
    private Long studentId;
    private String studentName;
    private LocalDate dutyDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer weekNum;
    private Integer isEarly;

}
