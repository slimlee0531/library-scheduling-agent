package com.slim.agent.mapper;

import com.slim.agent.entity.SystemConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemConfigMapper {

    int insert(SystemConfig systemConfig);

    int updateById(SystemConfig systemConfig);

    int deleteById(@Param("id") Long id);

    SystemConfig selectById(@Param("id") Long id);

    List<SystemConfig> selectAll();

    SystemConfig selectByConfigKey(@Param("configKey") String configKey);

}
