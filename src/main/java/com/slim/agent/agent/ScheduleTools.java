package com.slim.agent.agent;

import com.slim.agent.dto.TimeSlot;
import com.slim.agent.entity.Student;
import com.slim.agent.entity.DutyAssignment;
import com.slim.agent.service.StudentService;
import com.slim.agent.service.StudentCourseService;
import com.slim.agent.service.CoursePeriodService;
import com.slim.agent.mapper.DutyAssignmentMapper;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleTools {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private CoursePeriodService coursePeriodService;

    @Autowired
    private DutyAssignmentMapper dutyAssignmentMapper;

    @Tool("获取所有在职学生")
    public List<Student> getAllStudents() {
        return studentService.getActiveStudents();
    }

    @Tool("获取指定学生的空闲时间")
    public List<TimeSlot> getStudentFreeTime(Long studentId) {
        // 这里可以扩展，根据课程时间计算学生空闲时间
        // 现在先返回空列表，后续完善
        return List.of();
    }

    @Tool("保存排班结果")
    public void saveDutyAssignments(List<DutyAssignment> assignments) {
        for (DutyAssignment assignment : assignments) {
            dutyAssignmentMapper.insert(assignment);
        }
    }

}
