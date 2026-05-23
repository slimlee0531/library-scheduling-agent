package com.slim.agent.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DutyAssignmentResponse {

    private Long id;
    private Long studentId;
    private String studentName;
    private LocalDate dutyDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer weekNum;
    private Integer isEarly;

}
