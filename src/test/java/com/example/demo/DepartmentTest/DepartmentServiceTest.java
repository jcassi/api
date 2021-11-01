package com.example.demo.DepartmentTest;

import com.example.demo.Course.Course;
import com.example.demo.Course.CourseService;
import com.example.demo.Department.Department;
import com.example.demo.Department.DepartmentNotFoundException;
import com.example.demo.Department.DepartmentRepository;
import com.example.demo.Department.DepartmentService;
import org.checkerframework.checker.nullness.Opt;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    CourseService courseService;

    @InjectMocks
    DepartmentService departmentService;

    @Captor
    ArgumentCaptor<Department> departmentArgumentCaptor;


    @Test
    public void addDepartmentSingleDepartmentsTest() {
        Department department = new Department("81", "Matemática");

        when(departmentRepository.save(department)).thenReturn(department);

        assertEquals(department, departmentService.addDepartment(department));
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    public void getDepartmentsNoDepartmentTest() {
        Department[] departments = new Department[] {};
        when(departmentRepository.findAll()).thenReturn(new HashSet<Department>());
        assertEquals(new HashSet<Department>(), departmentService.getAllDepartments());
    }

    @Test
    public void getDepartmentById() {
        Department department1 = new Department("81", "Matemática");
        Department department2 = new Department("82", "Física");

        departmentService.addDepartment(department1);
        departmentService.addDepartment(department2);

        when(departmentRepository.findById("8101")).thenReturn(Optional.of(department1));

        assertEquals(department1,
                departmentService.getDepartmentById("8101"));
        verify(departmentRepository, times(2)).save(departmentArgumentCaptor.capture());
    }

    @Test
    public void getDepartmentByIdInvalidId() {
        Department department1 = new Department("81", "Matemática");
        Department department2 = new Department("82", "Física");

        departmentService.addDepartment(department1);
        departmentService.addDepartment(department2);

        when(departmentRepository.findById("8202")).thenThrow(DepartmentNotFoundException.class);

        assertThrows(DepartmentNotFoundException.class,
                () -> {departmentService.getDepartmentById("8202");});
        verify(departmentRepository, times(2)).save(departmentArgumentCaptor.capture());
    }

    @Test
    public void getAllDepartmentsTwoDepartmentsTest() {
        Department department1 = new Department("81", "Matemática");
        Department department2 = new Department("82", "Física");

        departmentService.addDepartment(department1);
        departmentService.addDepartment(department2);

        when(departmentRepository.findAll()).thenReturn(Arrays.asList(department1, department2));

        assertEquals(new HashSet<Department> (Arrays.asList(department1, department2)),
                departmentService.getAllDepartments());
        verify(departmentRepository, times(2)).save(departmentArgumentCaptor.capture());
    }

    @Test
    public void deleteDepartmentTest() {
        Department department1 = new Department("81", "Matemática");
        Department department2 = new Department("82", "Física");

        departmentService.addDepartment(department1);
        departmentService.addDepartment(department2);

        departmentService.deleteDepartment(department1.getId());

        when(departmentRepository.findAll()).thenReturn(Arrays.asList(department2));

        assertEquals(new HashSet<Department> (Arrays.asList(department2)), departmentService.getAllDepartments());
        verify(departmentRepository, times(2)).save(departmentArgumentCaptor.capture());
        verify(departmentRepository, times(1)).deleteById("81");
    }



    @Test
    public void deleteDepartmentInvalidIdTest() {
        Department department = new Department("81", "Matemática");
        departmentService.addDepartment(department);
        when(departmentRepository.findById("82")).thenReturn(Optional.empty());
        assertThrows(DepartmentNotFoundException.class, () -> {departmentService.getDepartmentById("82");});
    }

    @Test
    public void addCourseToDepartment() {
        Department department = new Department("81", "Matemática");
        Department departmentWithCourses = new Department("81", "Matemática");
        Course course = new Course("8101", "Análisis Matemático II", 30);
        departmentWithCourses.addCourse(course);

        when(departmentRepository.findById("81")).thenReturn(Optional.of(department))
                .thenReturn(Optional.of(department))
                .thenReturn(Optional.of(departmentWithCourses));
        doNothing().when(courseService).addCourse(course);
        when(departmentRepository.save(departmentWithCourses)).thenReturn(departmentWithCourses);

        departmentService.addCourseToDepartment("81", course);

        assertEquals(departmentWithCourses,
                departmentService.getDepartmentById("81"));
    }

    /*@Test
    public void addCoursesToDepartment() {
        Department department = new Department("81", "Matemática");
        Department departmentWithCourses = new Department("81", "Matemática");
        Course course1 = new Course("8101", "Análisis Matemático II", 30);
        Course course2 = new Course("8105", "Análisis Matemático III", 25);
        departmentWithCourses.addCourse(course1);
        departmentWithCourses.addCourse(course2);

        when(departmentRepository.findById("81")).thenReturn(Optional.of(department))
                .thenReturn(Optional.of(department))
                .thenReturn(Optional.of(departmentWithCourses));
        when(departmentRepository.save(departmentWithCourses)).thenReturn(departmentWithCourses);
        doNothing().when(courseService).addCourse(course1);
        doNothing().when(courseService).addCourse(course2);

        departmentService.addCourseToDepartment("81", course1);
        assertEquals(new ResponseEntity<>(departmentWithCourses, HttpStatus.OK),
                departmentService.getDepartmentById("81"));

    }*/
}
