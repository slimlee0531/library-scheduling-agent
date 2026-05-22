package com.slim.agent.mapper;

import com.slim.agent.entity.DutyAssignment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DutyAssignmentMapper {

    int insert(DutyAssignment dutyAssignment);

    int updateById(DutyAssignment dutyAssignment);

    int deleteById(@Param("id") Long id);

    int deleteByWeekNum(@Param("weekNum") Integer weekNum);

    DutyAssignment selectById(@Param("id") Long id);

    List<DutyAssignment> selectAll();

    List<DutyAssignment> selectByDutyDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<DutyAssignment> selectByWeekNum(@Param("weekNum") Integer weekNum);

    List<DutyAssignment> selectByStudentIdAndWeekNum(@Param("studentId") Long studentId, @Param("weekNum") Integer weekNum);

    List<DutyAssignment> selectByDutyDate(@Param("dutyDate") LocalDate dutyDate);

}
