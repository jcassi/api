package com.example.demo.Course;

import com.example.demo.Department.Department;
import com.example.demo.Department.DepartmentNotFoundException;
import com.example.demo.Student.Student;
import com.example.demo.Student.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public Set<Course> getCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping(path = "/{id}")
    public Course getCourseId(@PathVariable String id) {
        Course course;
        try {
            course = courseService.getCourseById(id);
        } catch (CourseNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found", e);
        }
        return course;
    }


    @PostMapping
    public void addCourse(@RequestBody Course course,  @RequestParam String departmentId) {
        try {
            courseService.addCourse(course);
        } catch (CourseAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course already exists", e);
        }
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable String id,
                                               @RequestBody Course course) {
        return courseService.updateCourse(id, course);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable String id){
        courseService.deleteCourse(id);
    }

    @PutMapping("/{courseId}/students/{studentId}")
    public void addStudentToCourse(@PathVariable String courseId, @PathVariable Long studentId) {
        try {
            courseService.addStudentToCourse(courseId, studentId);
        } catch (CourseNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found", e);
        } catch (StudentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found", e);
        } catch (CourseAlreadyFullException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course is already full", e);
        }
    }

    @GetMapping("{id}/students")
    public Set<Student> getStudents(@PathVariable String id){
        try {
            return courseService.getStudents(id);
        } catch (CourseNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found", e);
        }
    }


    @PatchMapping("/{id}")
    public void updateCourse(@PathVariable String id, @RequestBody Map<Object, Object> fields) {
        Course course = getCourseId(id);
        // Map key is field name, v is value
        fields.forEach((k, v) -> {
            // use reflection to get field k on manager and set it to value v
            Field field = ReflectionUtils.findField(Course.class, (String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, course, v);
        });
        courseService.updateCourse(id, course);
    }

    @DeleteMapping("/{courseId}/students/{studentId}")
    public void deleteStudentFromCourse(@PathVariable String courseId, @PathVariable Long studentId) {
        try {
            courseService.deleteStudentFromCourse(courseId, studentId);
        } catch (CourseNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found", e);
        } catch (StudentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found", e);
        }
    }
}
