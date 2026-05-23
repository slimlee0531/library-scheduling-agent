package com.slim.agent.service;

import com.slim.agent.dto.request.StudentRequest;
import com.slim.agent.dto.response.StudentResponse;
import com.slim.agent.entity.Student;
import com.slim.agent.mapper.StudentMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public StudentResponse create(StudentRequest request) {
        Student student = new Student();
        BeanUtils.copyProperties(request, student);
        if (student.getStatus() == null) {
            student.setStatus(1);
        }
        studentMapper.insert(student);
        return convertToResponse(student);
    }

    public StudentResponse update(Long id, StudentRequest request) {
        Student student = studentMapper.selectById(id);
        if (student == null) {
            throw new RuntimeException("学生不存在");
        }
        BeanUtils.copyProperties(request, student);
        studentMapper.updateById(student);
        return convertToResponse(student);
    }

    public void delete(Long id) {
        studentMapper.deleteById(id);
    }

    public StudentResponse getById(Long id) {
        Student student = studentMapper.selectById(id);
        return student != null ? convertToResponse(student) : null;
    }

    public List<StudentResponse> getAll() {
        List<Student> students = studentMapper.selectAll();
        return students.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<StudentResponse> getByStatus(Integer status) {
        List<Student> students = studentMapper.selectByStatus(status);
        return students.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<Student> getActiveStudents() {
        return studentMapper.selectByStatus(1);
    }

    public StudentResponse getByStudentNo(String studentNo) {
        Student student = studentMapper.selectByStudentNo(studentNo);
        return student != null ? convertToResponse(student) : null;
    }

    public StudentResponse getByName(String name) {
        Student student = studentMapper.selectByName(name);
        return student != null ? convertToResponse(student) : null;
    }

    private StudentResponse convertToResponse(Student student) {
        StudentResponse response = new StudentResponse();
        BeanUtils.copyProperties(student, response);
        return response;
    }

}
