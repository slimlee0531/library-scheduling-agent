package com.slim.agent.mapper;

import com.slim.agent.entity.ShiftConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShiftConfigMapper {

    int insert(ShiftConfig shiftConfig);

    int updateById(ShiftConfig shiftConfig);

    int deleteById(@Param("id") Long id);

    ShiftConfig selectById(@Param("id") Long id);

    List<ShiftConfig> selectAll();

    List<ShiftConfig> selectByDayType(@Param("dayType") Integer dayType);

    List<ShiftConfig> selectByDayTypeOrderByStartTimeAsc(@Param("dayType") Integer dayType);

}
