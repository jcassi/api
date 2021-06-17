package com.example.demo.Course;

import com.example.demo.Student.Student;
import com.example.demo.Student.StudentRepository;
import com.example.demo.Student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentService studentService;

    public Set<Course> getAllCourses() {
        Set<Course> result = new HashSet<>();
        Iterable<Course> iterable =  courseRepository.findAll();
        iterable.forEach(result::add);
        return result;
    }

    public Course getCourseById(String id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return course.get();
        } else {
            throw new CourseNotFoundException();
        }
    }

    public void addCourse(Course course) {
        if (courseRepository.existsById(course.getCode())) {
            throw new CourseAlreadyExistsException();
        } else {
            courseRepository.save(course);
        }
    }

    public ResponseEntity<Course> updateCourse(String id, Course course) {
        if (courseRepository.existsById(id)) {
            return new ResponseEntity<>(courseRepository.save(course), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(courseRepository.save(course), HttpStatus.CREATED);
        }
    }

    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }

    public void addStudentToCourse(String courseId, Long studentId) {
        try {
            Course course = this.getCourseById(courseId);
            Student student = this.studentService.getStudentById(studentId);
            course.addStudent(student);
            updateCourse(courseId, course);
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
