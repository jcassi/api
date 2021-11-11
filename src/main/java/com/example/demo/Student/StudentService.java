package com.example.demo.Student;

import com.example.demo.Course.Course;
import com.example.demo.Student.Student;
import com.example.demo.Student.StudentAlreadyExistsException;
import com.example.demo.Student.StudentNotFoundException;
import com.example.demo.Student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Set<Student> getAllStudents() {
        Set<Student> result = new HashSet<>();
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
        for(Course course : getCourses(id)) {
            course.deleteStudent(getStudentById(id));
        }
        studentRepository.deleteById(id);
    }

    public Set<Course> getCourses(Long id) {
        Student student = getStudentById(id);
        return student.getCourses();
    }

    public ResponseEntity<Student> updateStudent(Long id, Student student) {
        if (studentRepository.existsById(id)) {
            return new ResponseEntity<>(studentRepository.save(student), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(studentRepository.save(student), HttpStatus.CREATED);
        }
    }
}