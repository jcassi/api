package com.example.demo.Department;


import com.example.demo.Course.Course;
import com.example.demo.Department.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public Set<Department> getDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping(path = "/{id}")
    public Department getDepartmentId(@PathVariable String id) {
        Department department;
        try {
            department = departmentService.getDepartmentById(id);
        } catch (DepartmentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found", e);
        }
        return department;
    }
    
    @PostMapping
    public void addDepartment(@RequestBody Department department) {
        try {
            departmentService.addDepartment(department);
        } catch (DepartmentAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Department already exists", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable String id,
                                               @RequestBody Department department) {
        return departmentService.updateDepartment(id, department);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable String id){
        departmentService.deleteDepartment(id);
    }

    @PutMapping("/{departmentId}")
    public void addCourseToDepartment(@PathVariable String departmentId, @RequestBody Course course) {
        try {
            departmentService.addCourseToDepartment(departmentId, course);
        } catch (DepartmentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found", e);
        }
    }
}
