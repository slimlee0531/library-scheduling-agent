package com.slim.agent.controller;

import com.slim.agent.agent.ScheduleAgent;
import com.slim.agent.dto.request.ScheduleGenerateRequest;
import com.slim.agent.dto.response.ApiResponse;
import com.slim.agent.dto.response.DutyAssignmentResponse;
import com.slim.agent.entity.DutyAssignment;
import com.slim.agent.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired(required = false)
    private ScheduleAgent scheduleAgent;

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
            @RequestParam String startDate,
            @RequestParam String endDate) {
        List<DutyAssignmentResponse> assignments = scheduleService.getByDateRange(
                java.time.LocalDate.parse(startDate),
                java.time.LocalDate.parse(endDate)
        );
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

    @PostMapping("/generate-ai")
    public ApiResponse<List<DutyAssignmentResponse>> generateScheduleByAI(@RequestBody ScheduleGenerateRequest request) {
        Integer weekNum = request.getWeekNum();
        if (weekNum == null) {
            weekNum = scheduleService.getCurrentWeekNum();
        }

        if (scheduleAgent == null) {
            return ApiResponse.error(500, "AI Agent未配置，请检查LangChain4j配置");
        }

        List<DutyAssignment> assignments = scheduleAgent.generateSchedule(weekNum);
        List<DutyAssignmentResponse> responseList = assignments.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(responseList);
    }

    private DutyAssignmentResponse convertToResponse(DutyAssignment assignment) {
        DutyAssignmentResponse response = new DutyAssignmentResponse();
        BeanUtils.copyProperties(assignment, response);
        return response;
    }

}
