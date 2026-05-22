package com.slim.agent.mapper;

import com.slim.agent.entity.CoursePeriod;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CoursePeriodMapper {

    int insert(CoursePeriod coursePeriod);

    int updateById(CoursePeriod coursePeriod);

    int deleteById(@Param("id") Long id);

    CoursePeriod selectById(@Param("id") Long id);

    List<CoursePeriod> selectAll();

    CoursePeriod selectByPeriodNumber(@Param("periodNumber") Integer periodNumber);

}
