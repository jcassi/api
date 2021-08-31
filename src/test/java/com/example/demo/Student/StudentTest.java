package com.example.demo.Student;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class StudentTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentService studentService;

    @Captor
    ArgumentCaptor<Student> studentArgumentCaptor;


    @Test
    public void addStudentSingleStudentsTest() {
        Student student = new Student();
        Student savedStudent = new Student();
        savedStudent.setId(1L);

        Mockito.when(studentRepository.save(student)).thenReturn(savedStudent);

        assertEquals(savedStudent, studentService.addStudent(student));

        Mockito.verify(studentRepository, Mockito.times(1)).save(student);
    }

    @Test
    public void getStudentsNoStudentTest() {
        Student[] students = new Student[] {};
        Mockito.when(studentRepository.findAll()).thenReturn(new HashSet<Student>());
        assertEquals(new HashSet<Student>(), studentService.getAllStudents());
    }

    @Test
    public void getAllStudentsTwoStudentsTest() {
        Student student1 = new Student();
        Student student2 = new Student();

        student1.setId(1L);
        student2.setId(2L);

        studentService.addStudent(student1);
        studentService.addStudent(student2);

        Mockito.when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        assertEquals(new HashSet<Student> (Arrays.asList(student1, student2)), studentService.getAllStudents());

        Mockito.verify(studentRepository, Mockito.times(2)).save(studentArgumentCaptor.capture());
    }

    @Test
    public void deleteStudentTest() {
        Student student = new Student();

        Student student1 = new Student();
        Student student2 = new Student();

        student1.setId(1L);
        student2.setId(2L);

        studentService.addStudent(student1);
        studentService.addStudent(student2);

        studentService.deleteStudent(student1.getId());

        Mockito.when(studentRepository.findAll()).thenReturn(Arrays.asList(student2));

        assertEquals(new HashSet<Student> (Arrays.asList(student2)), studentService.getAllStudents());

        Mockito.verify(studentRepository, Mockito.times(2)).save(studentArgumentCaptor.capture());
        Mockito.verify(studentRepository, Mockito.times(1)).deleteById(1L);
    }
}
