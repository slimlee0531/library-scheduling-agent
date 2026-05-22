package com.slim.agent.entity;

import lombok.Data;

@Data
public class StudentCourse {

    private Long id;
    private Long studentId;
    private Integer dayOfWeek;
    private Integer periodNumber;

}
