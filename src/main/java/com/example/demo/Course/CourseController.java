package com.example.demo.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getDepartments() {
        return courseService.getAllCourses();
    }

    @GetMapping(path = "/{id}")
    public Course getDepartmentById(@PathVariable int id) {
        Course course;
        try {
            course = courseService.getCourseById(id);
        } catch (CourseNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found", e);
        }
        return course;
    }


    @PostMapping
    public void addCourse(@RequestBody Course course) {
        try {
            courseService.addCourse(course);
        } catch (CourseAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course already exists", e);
        }
    }
}
