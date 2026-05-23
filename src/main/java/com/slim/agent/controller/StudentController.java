package com.slim.agent.controller;

import com.slim.agent.dto.request.StudentRequest;
import com.slim.agent.dto.response.ApiResponse;
import com.slim.agent.dto.response.StudentResponse;
import com.slim.agent.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ApiResponse<StudentResponse> create(@RequestBody StudentRequest request) {
        StudentResponse student = studentService.create(request);
        return ApiResponse.success(student);
    }

    @PutMapping("/{id}")
    public ApiResponse<StudentResponse> update(@PathVariable Long id, @RequestBody StudentRequest request) {
        StudentResponse student = studentService.update(id, request);
        return ApiResponse.success(student);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ApiResponse.success();
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentResponse> getById(@PathVariable Long id) {
        StudentResponse student = studentService.getById(id);
        return ApiResponse.success(student);
    }

    @GetMapping
    public ApiResponse<List<StudentResponse>> getAll() {
        List<StudentResponse> students = studentService.getAll();
        return ApiResponse.success(students);
    }

    @GetMapping("/status/{status}")
    public ApiResponse<List<StudentResponse>> getByStatus(@PathVariable Integer status) {
        List<StudentResponse> students = studentService.getByStatus(status);
        return ApiResponse.success(students);
    }

    @GetMapping("/studentNo/{studentNo}")
    public ApiResponse<StudentResponse> getByStudentNo(@PathVariable String studentNo) {
        StudentResponse student = studentService.getByStudentNo(studentNo);
        return ApiResponse.success(student);
    }

    @GetMapping("/name/{name}")
    public ApiResponse<StudentResponse> getByName(@PathVariable String name) {
        StudentResponse student = studentService.getByName(name);
        return ApiResponse.success(student);
    }

}
