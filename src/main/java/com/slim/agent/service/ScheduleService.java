package com.slim.agent.service;

import com.slim.agent.dto.response.DutyAssignmentResponse;
import com.slim.agent.entity.DutyAssignment;
import com.slim.agent.mapper.DutyAssignmentMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private DutyAssignmentMapper dutyAssignmentMapper;

    @Autowired
    private SchedulingAlgorithm schedulingAlgorithm;

    public List<DutyAssignmentResponse> getByWeekNum(Integer weekNum) {
        List<DutyAssignment> assignments = dutyAssignmentMapper.selectByWeekNum(weekNum);
        return assignments.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<DutyAssignmentResponse> getByDateRange(LocalDate startDate, LocalDate endDate) {
        List<DutyAssignment> assignments = dutyAssignmentMapper.selectByDutyDateBetween(startDate, endDate);
        return assignments.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<DutyAssignmentResponse> generateAndSaveSchedule(Integer weekNum) {
        dutyAssignmentMapper.deleteByWeekNum(weekNum);
        List<DutyAssignment> assignments = schedulingAlgorithm.generateScheduleForWeek(weekNum);
        for (DutyAssignment assignment : assignments) {
            dutyAssignmentMapper.insert(assignment);
        }
        return assignments.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public Integer getCurrentWeekNum() {
        LocalDate now = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return now.get(weekFields.weekOfYear());
    }

    private DutyAssignmentResponse convertToResponse(DutyAssignment assignment) {
        DutyAssignmentResponse response = new DutyAssignmentResponse();
        BeanUtils.copyProperties(assignment, response);
        return response;
    }

}
