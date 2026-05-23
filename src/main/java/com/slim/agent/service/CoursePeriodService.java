package com.slim.agent.service;

import com.slim.agent.entity.CoursePeriod;
import com.slim.agent.mapper.CoursePeriodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CoursePeriodService {

    @Autowired
    private CoursePeriodMapper coursePeriodMapper;

    public List<CoursePeriod> getAll() {
        return coursePeriodMapper.selectAll();
    }

    public CoursePeriod getByPeriodNumber(Integer periodNumber) {
        return coursePeriodMapper.selectByPeriodNumber(periodNumber);
    }

}
