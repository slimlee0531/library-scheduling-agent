package com.slim.agent.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StudentResponse {

    private Long id;
    private String name;
    private String studentNo;
    private String phone;
    private Integer status;
    private LocalDateTime createdAt;

}
