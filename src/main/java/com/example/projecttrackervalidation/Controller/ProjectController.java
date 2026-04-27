package com.example.projecttrackervalidation.Controller;

import com.example.projecttrackervalidation.ApiResponse.ApiResponse;
import com.example.projecttrackervalidation.Model.Project;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    ArrayList<Project> projects = new ArrayList<>();

    //BASIC CRUD ENDPOINTS
    @PostMapping("/add")
    public ResponseEntity<?> addProject(@RequestBody @Valid Project newProject, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        for(Project p: projects){
            //check if id is already exist
            if(p.getId().equalsIgnoreCase(newProject.getId()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ApiResponse("The ID: " + newProject.getId() + " is already used please enter another ID."));
            //check if title is already exist
            if(p.getTitle().equalsIgnoreCase(newProject.getTitle()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ApiResponse("This project is already exist."));
        }

        projects.add(newProject);
        return ResponseEntity.status(200).body(new ApiResponse("Project added successfully."));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllProjects(){
        return ResponseEntity.status(200).body(projects);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProject(@PathVariable String id, @RequestBody @Valid Project newProject, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        for(int i=0; i < projects.size(); i++){
            if(projects.get(i).getId().equalsIgnoreCase(id)){
                projects.set(i, newProject);
                return ResponseEntity.status(200).body(new ApiResponse("Project updated successfully."));
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("Project with ID: " + id + " not found."));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable String id){
        for(int i=0; i < projects.size(); i++) {
            if (projects.get(i).getId().equalsIgnoreCase(id)) {
                projects.remove(i);
                return ResponseEntity.status(200).body(new ApiResponse("Project deleted successfully"));
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("Project with ID: " + id + " not found."));
    }


    //EXTRA ENDPOINTS
    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable String id){
        for (Project project : projects) {
            if (project.getId().equalsIgnoreCase(id)) {
                if(project.getStatus().equals("Not Started"))
                    project.setStatus("In Progress");
                else if(project.getStatus().equals("In Progress"))
                    project.setStatus("Completed");
                else
                    project.setStatus("Not Started");

                return ResponseEntity.status(200).body(new ApiResponse("Project status updated successfully."));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Project with ID: " + id + " not found."));
    }

    @GetMapping("/get-title/{title}")
    public ResponseEntity<?> searchByTitle(@PathVariable String title){
        for (Project project : projects) {
            if (project.getTitle().equalsIgnoreCase(title)) {
                return ResponseEntity.status(200).body(project);
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("There is no project with title: " + title));
    }

    @GetMapping("/get-company/{companyName}")
    public ResponseEntity<?> getByCompanyName(@PathVariable String companyName){
        ArrayList<Project> companyProjects = new ArrayList<>();
        for(Project project: projects){
            if(project.getCompanyName().equalsIgnoreCase(companyName))
                companyProjects.add(project);
        }
        if(companyProjects.isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("Company: " + companyName + " doesn't have any projects."));

        return ResponseEntity.status(200).body(companyProjects);
    }
}
