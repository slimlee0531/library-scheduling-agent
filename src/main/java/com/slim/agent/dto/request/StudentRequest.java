package com.slim.agent.dto.request;

import lombok.Data;

@Data
public class StudentRequest {

    private String name;
    private String studentNo;
    private String phone;
    private String password;
    private Integer status;

}
