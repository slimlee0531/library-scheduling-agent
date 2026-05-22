package com.slim.agent.agent;

import org.springframework.stereotype.Component;

@Component
public class ScheduleTools {
    // Function calling tools for the agent
    public String getAllStudents() { return ""; }
    public String getStudentFreeTime(Long studentId) { return ""; }
    public String saveDutyAssignments(Object assignments) { return ""; }
}