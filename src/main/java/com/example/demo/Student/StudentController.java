package com.example.demo.Student;

import com.example.demo.Course.Course;
import com.example.demo.Student.Student;
import com.example.demo.Student.StudentAlreadyExistsException;
import com.example.demo.Student.StudentNotFoundException;
import com.example.demo.Student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    StudentMapper studentMapper;

    @GetMapping
    public Set<StudentDto> getStudents() {
        Set<Student> students = studentService.getAllStudents();
        Set<StudentDto> studentDtos = new HashSet<>();
        for (Student student : students) {
            studentDtos.add(studentMapper.studentToStudentDto(student));
        }

        return studentDtos;
    }

    @GetMapping(path = "/{id}")
    public StudentDto getStudentById(@PathVariable Long id) {
        Student student;
        try {
            student = studentService.getStudentById(id);
        } catch (StudentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found", e);
        }
        return studentMapper.studentToStudentDto(student);
    }


    @PostMapping
    public Student addStudent(@RequestBody StudentDto studentDto) {
        Student student = studentMapper.studentDtoToStudent(studentDto);
        return studentService.addStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
    }

    @GetMapping("{id}/courses")
    public Set<Course> getCourses(@PathVariable Long id){
        try {
            return studentService.getCourses(id);
        } catch (StudentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found", e);
        }
    }


    @PatchMapping("/{id}")
    public void updateCourse(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        Student student = studentService.getStudentById(id);
        // Map key is field name, v is value
        fields.forEach((k, v) -> {
            // use reflection to get field k on manager and set it to value v
            Field field = ReflectionUtils.findField(Student.class, (String) k);
            field.setAccessible(true);
            ReflectionUtils.setField(field, student, v);
        });
        studentService.updateStudent(id, student);
    }
}