package com.example.demo.Student;

import com.example.demo.Student.Student;
import com.example.demo.Student.StudentAlreadyExistsException;
import com.example.demo.Student.StudentNotFoundException;
import com.example.demo.Student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        List<Student> result = new ArrayList<>();
        Iterable<Student> iterable =  studentRepository.findAll();
        iterable.forEach(result::add);
        return result;
    }

    public Student getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get();
        } else {
            throw new StudentNotFoundException();
        }
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}