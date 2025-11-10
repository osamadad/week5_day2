package com.tuwaiq.week5_day2_projectsystem.Model;

import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Project {
    @NotEmpty(message = "ID can't be empty")
    @Size(min = 2,message = "ID must be at least 2 characters")
    private String id;
    @NotEmpty(message = "Title can't be empty")
    @Size(min = 8,message = "Title must be at least 8 characters")
    private String title;
    @NotEmpty(message = "Description can't be empty")
    @Size(min = 15,message = "Description must be at least 15 characters")
    private String description;
    @NotEmpty(message = "Status can't be empty")
    @Pattern(regexp = "Not Started|In Progress|Completed",message = "Status must be Not Started or In Progress or Completed")
    private String status;
    @NotEmpty(message = "Company name can't be empty")
    @Size(min = 6,message = "Company name must be at least 6 characters")
    private String companyName;
}
