package com.slim.agent.service;

import com.slim.agent.entity.ShiftConfig;
import com.slim.agent.mapper.ShiftConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftConfigService {

    @Autowired
    private ShiftConfigMapper shiftConfigMapper;

    public List<ShiftConfig> getAll() {
        return shiftConfigMapper.selectAll();
    }

    public List<ShiftConfig> getByDayType(Integer dayType) {
        return shiftConfigMapper.selectByDayType(dayType);
    }

    public List<ShiftConfig> getByDayTypeOrderByStartTime(Integer dayType) {
        return shiftConfigMapper.selectByDayTypeOrderByStartTimeAsc(dayType);
    }

}
