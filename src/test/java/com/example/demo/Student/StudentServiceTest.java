package com.example.demo.Student;

import com.example.demo.Course.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentService studentService;

    @Captor
    ArgumentCaptor<Student> studentArgumentCaptor;


    @Test
    public void addStudentTest() {
        Student student = new Student();
        Student savedStudent = new Student();
        savedStudent.setId(1L);

        when(studentRepository.save(student)).thenReturn(savedStudent);

        assertEquals(savedStudent, studentService.addStudent(student));

        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void getStudentsNoStudentTest() {
        Student[] students = new Student[] {};
        when(studentRepository.findAll()).thenReturn(new HashSet<Student>());
        assertEquals(new HashSet<Student>(), studentService.getAllStudents());
    }

    @Test
    public void getStudentById() {
        Student student1 = new Student();
        Student student2 = new Student();

        studentService.addStudent(student1);
        studentService.addStudent(student2);

        student1.setId(1L);
        student2.setId(2L);

        when(studentRepository.findById(2L)).thenReturn(Optional.of(student2));

        assertEquals(student2, studentService.getStudentById(2L));

        verify(studentRepository, times(1)).findById(2L);
    }


    @Test
    public void getAllStudentsTwoStudentsTest() {
        Student student1 = new Student();
        Student student2 = new Student();
        student1.setId(1L);
        student2.setId(2L);

        studentService.addStudent(student1);
        studentService.addStudent(student2);

        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        assertEquals(new HashSet<Student> (Arrays.asList(student1, student2)), studentService.getAllStudents());

        verify(studentRepository, times(2)).save(studentArgumentCaptor.capture());
    }

    @Test
    public void deleteStudentTest() {
        Student student1 = new Student();
        Student student2 = new Student();

        student1.setId(1L);
        student2.setId(2L);

        studentService.addStudent(student1);
        studentService.addStudent(student2);

        studentService.deleteStudent(student1.getId());

        when(studentRepository.findAll()).thenReturn(Arrays.asList(student2));

        assertEquals(new HashSet<Student> (Arrays.asList(student2)), studentService.getAllStudents());

        verify(studentRepository, times(2)).save(studentArgumentCaptor.capture());
        verify(studentRepository, times(1)).deleteById(1L);
    }



    @Test
    public void getStudentInvalidIdTest() {
        Student student = new Student();
        student.setId(1L);
        studentService.addStudent(student);
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class, () -> {studentService.getStudentById(2L);});
    }

    @Test
    public void getCoursesTest() {
        Student student = new Student();
        student.setId(1L);
        Course course1 = new Course();
        Course course2 = new Course();
        student.setCourses(new HashSet<>(Arrays.asList(course1, course2)));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        assertEquals(new HashSet<>(Arrays.asList(course1, course2)), studentService.getCourses(1L));
    }
}
