package com.slim.agent.controller;

import com.slim.agent.dto.request.ScheduleGenerateRequest;
import com.slim.agent.dto.response.ApiResponse;
import com.slim.agent.dto.response.DutyAssignmentResponse;
import com.slim.agent.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/current-week")
    public ApiResponse<List<DutyAssignmentResponse>> getCurrentWeekSchedule() {
        Integer weekNum = scheduleService.getCurrentWeekNum();
        List<DutyAssignmentResponse> assignments = scheduleService.getByWeekNum(weekNum);
        return ApiResponse.success(assignments);
    }

    @GetMapping("/week/{weekNum}")
    public ApiResponse<List<DutyAssignmentResponse>> getWeekSchedule(@PathVariable Integer weekNum) {
        List<DutyAssignmentResponse> assignments = scheduleService.getByWeekNum(weekNum);
        return ApiResponse.success(assignments);
    }

    @GetMapping("/date-range")
    public ApiResponse<List<DutyAssignmentResponse>> getScheduleByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<DutyAssignmentResponse> assignments = scheduleService.getByDateRange(startDate, endDate);
        return ApiResponse.success(assignments);
    }

    @PostMapping("/generate")
    public ApiResponse<List<DutyAssignmentResponse>> generateSchedule(@RequestBody ScheduleGenerateRequest request) {
        Integer weekNum = request.getWeekNum();
        if (weekNum == null) {
            weekNum = scheduleService.getCurrentWeekNum();
        }
        List<DutyAssignmentResponse> assignments = scheduleService.generateAndSaveSchedule(weekNum);
        return ApiResponse.success(assignments);
    }

}
