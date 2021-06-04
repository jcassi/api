package com.example.demo.Student;

import com.example.demo.Student.Student;
import com.example.demo.Student.StudentAlreadyExistsException;
import com.example.demo.Student.StudentNotFoundException;
import com.example.demo.Student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping(path = "/{id}")
    public Student getStudentId(@PathVariable Long id) {
        Student student;
        try {
            student = studentService.getStudentById(id);
        } catch (StudentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found", e);
        }
        return student;
    }


    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
    }
}