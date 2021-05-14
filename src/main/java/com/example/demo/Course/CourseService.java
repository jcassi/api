package com.example.demo.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        Iterable<Course> iterable =  courseRepository.findAll();
        List<Course> result = new ArrayList<>();
        iterable.forEach(result::add);
        return result;
    }

    public Course getCourseById(int id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new CourseAlreadyExistsException();
        }
    }

    public void addCourse(Course course) {
        try {
            getCourseById(course.getCode());
        } catch (CourseNotFoundException e) {
            courseRepository.save(course);
            return;
        }
        throw new CourseAlreadyExistsException();
    }
}
