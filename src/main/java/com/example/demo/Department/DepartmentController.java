package com.example.demo.Department;


import com.example.demo.Course.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    CourseMapper courseMapper;

    @GetMapping
    public Set<DepartmentDto> getDepartments() {
        Set<Department> departments = departmentService.getAllDepartments();
        Set<DepartmentDto> departmentDtos = new HashSet<>();
        for (Department department : departments) {
            departmentDtos.add(departmentMapper.departmentToDepartmentDto(department));
        }
        return departmentDtos;
    }

    @GetMapping(path = "/{id}")
    public DepartmentDto getDepartmentId(@PathVariable String id) {
        DepartmentDto departmentDto;
        try {
            Department department = departmentService.getDepartmentById(id);
            departmentDto = departmentMapper.departmentToDepartmentDto(department);
        } catch (DepartmentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found", e);
        }
        return departmentDto;
    }
    
    @PostMapping
    public void addDepartment(@RequestBody DepartmentDto departmentDto) {
        Department department = departmentMapper.departmentDtoToDepartment(departmentDto);
        try {
            departmentService.addDepartment(department);
        } catch (DepartmentAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Department already exists", e);
        }
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable String id,
                                               @RequestBody Department department) {
        return departmentService.updateDepartment(id, department);
    }*/

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable String id){
        departmentService.deleteDepartment(id);
    }

    @PostMapping("/{departmentId}/courses")
    public void addCourseToDepartment(@PathVariable String departmentId, @RequestBody CourseDto courseDto) {
        Course course = courseMapper.courseDtoToCourse(courseDto);
        try {
            departmentService.addCourseToDepartment(departmentId, course);
        } catch (DepartmentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found", e);
        }
    }

    @GetMapping("{id}/courses")
    public Set<CourseDto> getCourses(@PathVariable String id){
        try {
            Set<Course> courses = departmentService.getCourses(id);
            Set<CourseDto> courseDtos = new HashSet<>();
            for (Course course : courses) {
                courseDtos.add(courseMapper.courseToCourseDto(course));
            }
            return courseDtos;
        } catch (DepartmentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found", e);
        }
    }

   /* @PatchMapping("/{id}")
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
    }*/

    @DeleteMapping("/{departmentId}/courses/{courseId}")
    public void deleteCourseFromDepartment(@PathVariable String departmentId, @PathVariable String courseId) {
        try {
            departmentService.deleteCourseFromDepartment(departmentId, courseId);
        } catch (DepartmentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found", e);
        } catch (CourseNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found", e);
        }
    }
}
