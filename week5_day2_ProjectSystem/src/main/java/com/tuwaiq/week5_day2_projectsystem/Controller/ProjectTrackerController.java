package com.tuwaiq.week5_day2_projectsystem.Controller;

import Api.ApiResponse;
import com.tuwaiq.week5_day2_projectsystem.Model.Project;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Retention;
import java.util.ArrayList;


@RestController
@RequestMapping("/api/v2/project-tracker-system")
public class ProjectTrackerController {
    ArrayList<Project> projects = new ArrayList<>();

    @PostMapping("/add")
    public ResponseEntity<?> addProject(@RequestBody @Valid Project project, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        } else {
            projects.add(project);
            return ResponseEntity.status(200).body(new ApiResponse("The project have been added successfully"));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProjects() {
        if (projects.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("There are no projects to show"));
        } else {
            return ResponseEntity.status(200).body(projects);
        }
    }

    @PutMapping("/update/{index}")
    public ResponseEntity<?> updateProject(@PathVariable int index, @RequestBody @Valid Project project, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        } else if (projects.size() - 1 < index) {
            return ResponseEntity.status(400).body(new ApiResponse("No project found"));
        } else {
            projects.set(index, project);
            return ResponseEntity.status(200).body(new ApiResponse("The project have been updated successfully"));
        }
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity<?> deleteProject(@PathVariable int index) {
        if (projects.size() - 1 < index) {
            return ResponseEntity.status(400).body(new ApiResponse("No project found"));
        } else {
            projects.remove(index);
            return ResponseEntity.status(200).body(new ApiResponse("The project have been deleted successfully"));
        }
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable String id) {
        for (Project project : projects) {
            if (project.getId().equalsIgnoreCase(id)) {
                String currentStatus=project.getStatus();
                if (currentStatus.equalsIgnoreCase("Not Started")){
                    project.setStatus("In Progress");
                } else if (currentStatus.equalsIgnoreCase("In Progress")) {
                    project.setStatus("Completed");
                }else if (currentStatus.equalsIgnoreCase("Completed")){
                    return ResponseEntity.status(200).body(new ApiResponse("The project status is already completed"));
                }
                return ResponseEntity.status(200).body(new ApiResponse("The project status have been changed to " + project.getStatus()));
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("No project found"));
    }

    @GetMapping("/get/status/{id}")                             /* added as extra */
    public ResponseEntity<?> getStatus(@PathVariable String id) {
        for (Project project : projects) {
            if (project.getId().equalsIgnoreCase(id)) {
                return ResponseEntity.status(200).body(new ApiResponse("The project status is " + project.getStatus()));
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("No project found"));
    }

    @GetMapping("/get/project/{title}")
    public ResponseEntity<?> searchByTitle(@PathVariable String title) {
        for (Project project : projects) {
            if (project.getTitle().equalsIgnoreCase(title)) {
                return ResponseEntity.status(200).body(project);
            }
        }
        return ResponseEntity.status(400).body("No project found");
    }

    @GetMapping("/get/projects/{companyName}")
    public ResponseEntity<?> getProjectsByCompany(@PathVariable String companyName) {
        ArrayList<Project> companyProjects = new ArrayList<>();
        for (Project project : projects) {
            if (project.getCompanyName().equalsIgnoreCase(companyName)) {
                companyProjects.add(project);
            }
        }
        if (!companyProjects.isEmpty()) {
            return ResponseEntity.status(200).body(companyProjects);
        } else {
            return ResponseEntity.status(400).body(new ApiResponse("No projects found"));
        }
    }

}
