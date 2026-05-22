package com.slim.agent.mapper;

import com.slim.agent.entity.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentCourseMapper {

    int insert(StudentCourse studentCourse);

    int deleteById(@Param("id") Long id);

    int deleteByStudentId(@Param("studentId") Long studentId);

    StudentCourse selectById(@Param("id") Long id);

    List<StudentCourse> selectAll();

    List<StudentCourse> selectByStudentId(@Param("studentId") Long studentId);

    List<StudentCourse> selectByStudentIdAndDayOfWeek(@Param("studentId") Long studentId, @Param("dayOfWeek") Integer dayOfWeek);

}
