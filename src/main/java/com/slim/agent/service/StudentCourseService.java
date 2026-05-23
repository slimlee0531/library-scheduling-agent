package com.slim.agent.service;

import com.slim.agent.entity.StudentCourse;
import com.slim.agent.mapper.StudentCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseService {

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    public List<StudentCourse> getByStudentId(Long studentId) {
        return studentCourseMapper.selectByStudentId(studentId);
    }

    public List<StudentCourse> getByStudentIdAndDayOfWeek(Long studentId, Integer dayOfWeek) {
        return studentCourseMapper.selectByStudentIdAndDayOfWeek(studentId, dayOfWeek);
    }

    public void add(StudentCourse course) {
        studentCourseMapper.insert(course);
    }

    public void deleteById(Long id) {
        studentCourseMapper.deleteById(id);
    }

    public void deleteByStudentId(Long studentId) {
        studentCourseMapper.deleteByStudentId(studentId);
    }

}
