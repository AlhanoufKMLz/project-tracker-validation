package com.example.projecttrackervalidation.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Project {

    @NotBlank(message = "ID cannot be blank")
    @Size(min = 2, message = "ID must be at least 2 letters.")
    private String id;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 8, max = 50, message = "Title must be between 8 and 50 letters.")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 15, max = 500, message = "Description must be between 15 and 500 letters.")
    private String description;

    @NotBlank(message = "Status cannot be blank")
    @Pattern(regexp = "^(Not Started|In Progress|Completed)$", message = "Status must be: Not Started, In Progress, or Completed")
    private String status;

    @NotBlank(message = "Company name cannot be blank")
    @Size(min = 6, message = "Company must be at least 6 letters.")
    private String companyName;
}
