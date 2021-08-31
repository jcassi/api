package com.example.demo.Department;


import com.example.demo.Course.Course;
import com.example.demo.Course.CourseNotFoundException;
import com.example.demo.Course.CourseService;
import com.example.demo.Department.DepartmentRepository;
import com.example.demo.Student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CourseService courseService;

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

    @PutMapping("/{departmentId}/courses")
    public void addCourseToDepartment(@PathVariable String departmentId, @RequestBody Course course) {
        try {
            departmentService.addCourseToDepartment(departmentId, course);
        } catch (DepartmentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found", e);
        }
    }

    @GetMapping("{id}/courses")
    public Set<Course> getCourses(@PathVariable String id){
        try {
            return departmentService.getCourses(id);
        } catch (DepartmentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found", e);
        }
    }

    @PatchMapping("/{id}")
    public void updateDepartment(@PathVariable String id, @RequestBody Map<Object, Object> fields) {
        Department department = getDepartmentId(id);
        // Map key is field name, v is value
        fields.forEach((k, v) -> {
            // use reflection to get field k on manager and set it to value v
            Field field = ReflectionUtils.findField(Department.class, (String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, department, v);
        });
        departmentService.updateDepartment(id, department);
    }
}
