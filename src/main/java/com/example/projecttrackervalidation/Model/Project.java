package com.example.projecttrackervalidation.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Project {

    @NotBlank(message = "ID cannot be null")
    @Size(min = 2, message = "ID should be 2 or more letters.")
    private String id;

    @NotBlank(message = "Title cannot be null")
    @Size(min = 8, message = "Title should be 8 or more letters.")
    private String title;

    @NotBlank(message = "Description cannot be null")
    @Size(min = 15, message = "Description should be 15 or more letters.")
    private String description;

    @NotBlank(message = "Status cannot be null")
    @Pattern(regexp = "^(Not Started|In Progress|Completed)$")
    private String status;

    @NotBlank(message = "Company name cannot be null")
    @Size(min = 6, message = "Company name should be 6 or more letters.")
    private String companyName;
}
