package com.example.demo.DepartmentTest;

import com.example.demo.Department.Department;
import com.example.demo.Department.DepartmentNotFoundException;
import com.example.demo.Department.DepartmentRepository;
import com.example.demo.Department.DepartmentService;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    DepartmentRepository departmentRepository;

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
    public void getAllDepartmentsTwoDepartmentsTest() {
        Department department1 = new Department("81", "Matemática");
        Department department2 = new Department("82", "Física");

        departmentService.addDepartment(department1);
        departmentService.addDepartment(department2);

        when(departmentRepository.findAll()).thenReturn(Arrays.asList(department1, department2));

        assertEquals(new HashSet<Department> (Arrays.asList(department1, department2)), departmentService.getAllDepartments());
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
}
