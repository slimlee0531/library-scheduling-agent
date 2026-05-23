package com.slim.agent.service;

import com.slim.agent.dto.TimeSlot;
import com.slim.agent.entity.*;
import com.slim.agent.mapper.DutyAssignmentMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SchedulingAlgorithm {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private ShiftConfigService shiftConfigService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private DutyAssignmentMapper dutyAssignmentMapper;

    @Autowired
    private CoursePeriodService coursePeriodService;

    public List<DutyAssignment> generateScheduleForWeek(Integer weekNum) {
        LocalDate startDate = getMondayOfWeek(weekNum);
        List<DutyAssignment> assignments = new ArrayList<>();

        List<Student> activeStudents = getActiveStudents();
        Map<Long, Integer> shiftCountMap = initializeShiftCount(activeStudents, weekNum);

        for (int dayOffset = 0; dayOffset < 7; dayOffset++) {
            LocalDate dutyDate = startDate.plusDays(dayOffset);
            int dayOfWeek = dutyDate.getDayOfWeek().getValue();

            if (dayOfWeek == systemConfigService.getClosedWeekday()) {
                continue;
            }

            int dayType = getDayType(dayOfWeek);
            List<ShiftConfig> shifts = shiftConfigService.getByDayTypeOrderByStartTime(dayType);

            for (ShiftConfig shift : shifts) {
                if (isClosedPeriod(dayOfWeek, shift.getStartTime(), shift.getEndTime())) {
                    continue;
                }

                for (int i = 0; i < shift.getMinStu(); i++) {
                    Optional<Student> assignedStudent = findBestStudent(
                            activeStudents, shiftCountMap, dutyDate, shift, weekNum);

                    if (assignedStudent.isPresent()) {
                        Student student = assignedStudent.get();
                        DutyAssignment assignment = createAssignment(student, dutyDate, shift, weekNum);
                        assignments.add(assignment);
                        shiftCountMap.put(student.getId(), shiftCountMap.get(student.getId()) + 1);
                    }
                }
            }
        }

        return assignments;
    }

    private List<Student> getActiveStudents() {
        return studentService.getByStatus(1).stream()
                .map(response -> {
                    Student student = new Student();
                    BeanUtils.copyProperties(response, student);
                    return student;
                })
                .collect(Collectors.toList());
    }

    private Map<Long, Integer> initializeShiftCount(List<Student> students, Integer weekNum) {
        Map<Long, Integer> countMap = new HashMap<>();
        for (Student student : students) {
            List<DutyAssignment> existing = dutyAssignmentMapper.selectByStudentIdAndWeekNum(student.getId(), weekNum);
            countMap.put(student.getId(), existing.size());
        }
        return countMap;
    }

    private Optional<Student> findBestStudent(List<Student> students, Map<Long, Integer> shiftCountMap,
                                             LocalDate dutyDate, ShiftConfig shift, Integer weekNum) {
        int dayOfWeek = dutyDate.getDayOfWeek().getValue();

        List<Student> sortedStudents = students.stream()
                .sorted(Comparator.comparingInt(s -> shiftCountMap.get(s.getId())))
                .collect(Collectors.toList());

        for (Student student : sortedStudents) {
            if (shiftCountMap.get(student.getId()) >= systemConfigService.getWeeklyShifts()) {
                continue;
            }

            if (isStudentFree(student.getId(), dayOfWeek, shift)) {
                if (isStudentAlreadyAssigned(student.getId(), dutyDate, weekNum)) {
                    continue;
                }
                return Optional.of(student);
            }
        }

        return Optional.empty();
    }

    private boolean isStudentFree(Long studentId, int dayOfWeek, ShiftConfig shift) {
        List<StudentCourse> courses = studentCourseService.getByStudentIdAndDayOfWeek(studentId, dayOfWeek);
        TimeSlot shiftSlot = new TimeSlot(shift.getStartTime(), shift.getEndTime());

        for (StudentCourse course : courses) {
            CoursePeriod period = coursePeriodService.getByPeriodNumber(course.getPeriodNumber());
            if (period != null) {
                TimeSlot courseSlot = new TimeSlot(period.getStartTime(), period.getEndTime());
                if (shiftSlot.overlaps(courseSlot)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isStudentAlreadyAssigned(Long studentId, LocalDate dutyDate, Integer weekNum) {
        List<DutyAssignment> existing = dutyAssignmentMapper.selectByStudentIdAndWeekNum(studentId, weekNum);
        for (DutyAssignment assignment : existing) {
            if (assignment.getDutyDate().equals(dutyDate)) {
                return true;
            }
        }
        return false;
    }

    private DutyAssignment createAssignment(Student student, LocalDate dutyDate, ShiftConfig shift, Integer weekNum) {
        DutyAssignment assignment = new DutyAssignment();
        assignment.setStudentId(student.getId());
        assignment.setStudentName(student.getName());
        assignment.setDutyDate(dutyDate);
        assignment.setStartTime(shift.getStartTime());
        assignment.setEndTime(shift.getEndTime());
        assignment.setWeekNum(weekNum);
        assignment.setIsEarly(0);
        return assignment;
    }

    private int getDayType(int dayOfWeek) {
        if (dayOfWeek == 6) return 2;
        if (dayOfWeek == 7) return 3;
        return 1;
    }

    private boolean isClosedPeriod(int dayOfWeek, LocalTime startTime, LocalTime endTime) {
        if (dayOfWeek != systemConfigService.getClosedWeekday()) {
            return false;
        }

        String closedStartStr = systemConfigService.getClosedStartTime();
        String closedEndStr = systemConfigService.getClosedEndTime();
        LocalTime closedStart = LocalTime.parse(closedStartStr);
        LocalTime closedEnd = LocalTime.parse(closedEndStr);

        TimeSlot shiftSlot = new TimeSlot(startTime, endTime);
        TimeSlot closedSlot = new TimeSlot(closedStart, closedEnd);

        return shiftSlot.overlaps(closedSlot);
    }

    private LocalDate getMondayOfWeek(Integer weekNum) {
        LocalDate now = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int currentWeek = now.get(weekFields.weekOfYear());
        int weeksToAdd = weekNum - currentWeek;
        LocalDate targetMonday = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .plusWeeks(weeksToAdd);
        return targetMonday;
    }

}
