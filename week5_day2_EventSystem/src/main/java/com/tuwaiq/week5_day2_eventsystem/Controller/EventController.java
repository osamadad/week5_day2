package com.tuwaiq.week5_day2_eventsystem.Controller;

import Api.ApiResponse;
import com.tuwaiq.week5_day2_eventsystem.Model.Event;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v2/event-system")
public class EventController {
    ArrayList<Event> events = new ArrayList<>();

    @PostMapping("/add")
    public ResponseEntity<?> addEvent(@RequestBody @Valid Event event, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        } else {
            this.events.add(event);
            return ResponseEntity.status(200).body(new ApiResponse("The event have been added successfully"));
        }
    }

    @PostMapping("/add/now-date")                                       /* added as extra */
    public ResponseEntity<?> addEventNowDate(@RequestBody @Valid Event event, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        } else {
            event.setStartDate(LocalDateTime.now());
            this.events.add(event);
            return ResponseEntity.status(200).body(new ApiResponse("The event have been added successfully"));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getEvents() {
        if (events.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There are no event to show"));
        }else {
            return ResponseEntity.status(200).body(events);
        }
    }

    @PutMapping("/update/{index}")
    public ResponseEntity<?> updateEvent(@PathVariable int index, @RequestBody @Valid Event event, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        else if (events.size() - 1 < index) {
            return ResponseEntity.status(400).body(new ApiResponse("Event not found"));
        }else {
            events.set(index, event);
            return ResponseEntity.status(200).body(new ApiResponse("The event have been updated successfully"));
        }
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity<?> deleteEvent(@PathVariable int index) {
        if (events.size() - 1 < index) {
            return ResponseEntity.status(400).body(new ApiResponse("Event not found"));
        }else {
            events.remove(index);
            return ResponseEntity.status(200).body(new ApiResponse("The event have been deleted successfully"));
        }

    }

    @PutMapping("/update/{id}/{capacity}")
    public ResponseEntity<?> changeCapacity(@PathVariable String id, @PathVariable int capacity) {
        for (Event event : events) {
            if (event.getId().equalsIgnoreCase(id)) {
                if (capacity<25){
                    return ResponseEntity.status(400).body(new ApiResponse("Capacity can't be less than 25 capacity"));
                }
                else {
                    event.setCapacity(capacity);
                    return ResponseEntity.status(200).body(new ApiResponse("The event capacity have been changed successfully"));
                }
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("Event not found"));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getEventById(@PathVariable String id) {
        for (Event event : events) {
            if (event.getId().equalsIgnoreCase(id)) {
                return ResponseEntity.status(200).body(event);
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("No event by that ID"));
    }

    @GetMapping("/get/start-date/{id}")                         /* added as extra */
    public ResponseEntity<?> getEventStartDate(@PathVariable String id) {
        for (Event event : events) {
            if (event.getId().equalsIgnoreCase(id)) {
                return ResponseEntity.status(200).body(event.getStartDate());
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("No event by that ID"));
    }

    @GetMapping("/get/end-date/{id}")                           /* added as extra */
    public ResponseEntity<?> getEventEndDate(@PathVariable String id) {
        for (Event event : events) {
            if (event.getId().equalsIgnoreCase(id)) {
                return ResponseEntity.status(200).body(event.getEndDate());
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("No event by that ID"));
    }

    @GetMapping("/get/duration/{id}")                           /* added as extra */
    public ResponseEntity<?> getEventDuration(@PathVariable String id) {
        Duration duration;
        for (Event event : events) {
            if (event.getId().equalsIgnoreCase(id)) {
                duration = Duration.between(event.getStartDate(), event.getEndDate());
                double days = duration.toDays();
                double hours = duration.toHours() - (days * 24);
                double minutes = duration.toMinutes() - (hours * 60) - (days * 24 * 60);
                return ResponseEntity.status(200).body(new ApiResponse("The duration of this event is " + days + " days and " + hours + " hours and " + minutes + " minutes"));
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("Event not found"));
    }
}
