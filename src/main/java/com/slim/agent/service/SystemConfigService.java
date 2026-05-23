package com.slim.agent.service;

import com.slim.agent.entity.SystemConfig;
import com.slim.agent.mapper.SystemConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemConfigService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    public Map<String, String> getAllConfigs() {
        List<SystemConfig> configs = systemConfigMapper.selectAll();
        Map<String, String> configMap = new HashMap<>();
        for (SystemConfig config : configs) {
            configMap.put(config.getConfigKey(), config.getConfigValue());
        }
        return configMap;
    }

    public String getConfigValue(String configKey) {
        SystemConfig config = systemConfigMapper.selectByConfigKey(configKey);
        return config != null ? config.getConfigValue() : null;
    }

    public Integer getWeeklyShifts() {
        String value = getConfigValue("weekly_shifts");
        try {
            return value != null ? Integer.parseInt(value) : 4;
        } catch (NumberFormatException e) {
            return 4;
        }
    }

    public Integer getBufferMinutes() {
        String value = getConfigValue("buffer_minutes");
        try {
            return value != null ? Integer.parseInt(value) : 20;
        } catch (NumberFormatException e) {
            return 20;
        }
    }

    public Integer getClosedWeekday() {
        String value = getConfigValue("closed_weekday");
        try {
            return value != null ? Integer.parseInt(value) : 3;
        } catch (NumberFormatException e) {
            return 3;
        }
    }

    public String getClosedStartTime() {
        return getConfigValue("closed_start");
    }

    public String getClosedEndTime() {
        return getConfigValue("closed_end");
    }

}
