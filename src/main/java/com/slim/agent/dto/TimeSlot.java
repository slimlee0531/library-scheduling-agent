package com.slim.agent.dto;

import lombok.Data;
import java.time.LocalTime;

@Data
public class TimeSlot {

    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlot(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean overlaps(TimeSlot other) {
        return !this.endTime.isBefore(other.startTime) && !this.startTime.isAfter(other.endTime);
    }

}
