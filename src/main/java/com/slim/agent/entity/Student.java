package com.slim.agent.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Student {

    private Long id;
    private String name;
    private String studentNo;
    private String phone;
    private String password;
    private Integer status;
    private LocalDateTime createdAt;

}
