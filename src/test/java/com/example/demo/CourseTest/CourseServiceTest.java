package com.example.demo.CourseTest;

import com.example.demo.Course.*;
import com.example.demo.Student.Student;
import com.example.demo.Student.StudentNotFoundException;
import com.example.demo.Student.StudentService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    CourseRepository courseRepository;

    @Mock
    StudentService studentService;

    @InjectMocks
    CourseService courseService;

    @Captor
    ArgumentCaptor<Course> courseArgumentCaptor;



    /*@Test
    public void getCoursesNoCourseTest() {
        Course[] courses = new Course[] {};
        when(courseRepository.findAll()).thenReturn(new HashSet<Course>());
        assertEquals(new HashSet<Course>(), courseService.getAllCourses());
    }*/

    @Test
    public void getCourseById() {
        Course course1 = new Course();
        Course course2 = new Course();

        courseService.addCourse(course1);
        courseService.addCourse(course2);

        course1.setId("8101");
        course2.setId("8201");

        when(courseRepository.findById("8201")).thenReturn(Optional.of(course2));

        assertEquals(course2, courseService.getCourseById("8201"));

        verify(courseRepository, times(1)).findById("8201");
    }


    @Test
    public void getAllCoursesTwoCoursesTest() {
        Course course1 = new Course();
        Course course2 = new Course();
        course1.setId("8101");
        course2.setId("8202");

        courseService.addCourse(course1);
        courseService.addCourse(course2);

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

        assertEquals(new HashSet<Course> (Arrays.asList(course1, course2)), courseService.getAllCourses());

        verify(courseRepository, times(2)).save(courseArgumentCaptor.capture());
    }

    @Test
    public void deleteCourseTest() {
        Course course1 = new Course();
        Course course2 = new Course();

        course1.setId("8101");
        course2.setId("8202");

        courseService.addCourse(course1);
        courseService.addCourse(course2);

        courseService.deleteCourse(course1.getId());

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course2));

        assertEquals(new HashSet<Course> (Arrays.asList(course2)), courseService.getAllCourses());

        verify(courseRepository, times(2)).save(courseArgumentCaptor.capture());
        verify(courseRepository, times(1)).deleteById("8101");
    }


    @Test
    public void getCourseInvalidIdTest() {
        Course course = new Course();
        course.setId("8101");
        courseService.addCourse(course);
        when(courseRepository.findById("8202")).thenReturn(Optional.empty());
        assertThrows(CourseNotFoundException.class, () -> {courseService.getCourseById("8202");});
    }


    @Test
    public void getStudents() {
        Course course = new Course();
        course.setId("8101");
        Student student1 = new Student();
        Student student2 = new Student();
        course.setStudents(new HashSet<>(Arrays.asList(student1, student2)));

        when(courseRepository.findById("8101")).thenReturn(Optional.of(course));

        assertEquals(new HashSet<>(Arrays.asList(student1, student2)), courseService.getStudents("8101"));
    }

    @Test
    public void getStudentsInvalidCourseId() {
        Course course = new Course();
        course.setId("8101");
        Student student1 = new Student();
        Student student2 = new Student();
        course.setStudents(new HashSet<>(Arrays.asList(student1, student2)));

        when(courseRepository.findById("8202")).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> {courseService.getStudents("8202");});
    }

    @Test
    public void addStudentsInvalidStudentIdThrowsException() {
        Course course = new Course();
        course.setId("8101");
        course.setMaxEnrollment(5);
        course.setStudents(new HashSet<>());
        Student student = new Student();
        student.setId(3L);

        when(courseRepository.findById("8101")).thenReturn(Optional.of(course));
        when(studentService.getStudentById(3L)).thenThrow(StudentNotFoundException.class);

        assertThrows(StudentNotFoundException.class,
                () -> {courseService.addStudentToCourse("8101", 3L);});
    }

    @Test
    public void addStudentsInvalidCourseIdThrowsException() {
        Course course = new Course();
        course.setId("8101");
        course.setMaxEnrollment(5);
        course.setStudents(new HashSet<>());
        Student student = new Student();
        student.setId(3L);

        when(courseRepository.findById("8201")).thenThrow(CourseNotFoundException.class);

        assertThrows(CourseNotFoundException.class,
                () -> {courseService.addStudentToCourse("8201", 3L);});
    }

    @Test
    public void addStudentToFullCourseThrowsException() {
        Course course = new Course("8101", "Análisis Matemático II", 1);
        Student student1 = new Student();
        student1.setId(1L);
        Student student2 = new Student();
        student2.setId(2L);
        course.addStudent(student1);

        when(courseRepository.findById("8101")).thenReturn(Optional.of(course));
        when(studentService.getStudentById(2L)).thenReturn(student2);
        assertThrows(CourseAlreadyFullException.class,
                () -> {courseService.addStudentToCourse("8101", 2L);});
    }
}
