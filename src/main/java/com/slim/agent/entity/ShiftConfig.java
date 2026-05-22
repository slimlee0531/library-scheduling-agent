package com.slim.agent.entity;

import lombok.Data;
import java.time.LocalTime;

@Data
public class ShiftConfig {

    private Long id;
    private Integer dayType;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer minStu;
    private Integer maxStu;

}
