package com.tuwaiq.week5_day2_eventsystem.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Event {
    @NotEmpty(message = "ID can't be empty")
    @Size(min = 2, message = "ID must be at least 2 characters")
    private String id;
    @NotEmpty(message = "description can't be empty")
    @Size(min = 15, message = "description must be at least 15 characters")
    private String description;
    @NotNull(message = "Capacity can't be empty")
    @Positive(message = "Capacity can't be negative number")
    @Min(value = 25,message = "Capacity can't be less than 25 capacity")
    private int capacity;
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime endDate;
}
