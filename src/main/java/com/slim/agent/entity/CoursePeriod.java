package com.slim.agent.entity;

import lombok.Data;
import java.time.LocalTime;

@Data
public class CoursePeriod {

    private Long id;
    private Integer periodNumber;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;

}
