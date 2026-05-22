package com.slim.agent.mapper;

import com.slim.agent.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {

    int insert(Student student);

    int updateById(Student student);

    int deleteById(@Param("id") Long id);

    Student selectById(@Param("id") Long id);

    List<Student> selectAll();

    List<Student> selectByStatus(@Param("status") Integer status);

    Student selectByStudentNo(@Param("studentNo") String studentNo);

    Student selectByName(@Param("name") String name);

}
