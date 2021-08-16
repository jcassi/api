package com.example.demo.Department;

import com.example.demo.Course.Course;
import com.example.demo.Course.CourseRepository;
import com.example.demo.Department.Department;
import com.example.demo.Department.DepartmentAlreadyExistsException;
import com.example.demo.Department.DepartmentNotFoundException;
import com.example.demo.Department.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CourseRepository courseRepository;


    public Set<Department> getAllDepartments() {
        Set<Department> result = new HashSet<>();
        Iterable<Department> iterable =  departmentRepository.findAll();
        iterable.forEach(result::add);
        return result;
    }

    public Department getDepartmentById(String id) {
        Optional<Department> department = departmentRepository.findById(id);
        if (department.isPresent()) {
            return department.get();
        } else {
            throw new DepartmentNotFoundException();
        }
    }

    public void addDepartment(Department department) {
        if (departmentRepository.existsById(department.getId())) {
            throw new DepartmentAlreadyExistsException();
        } else {
            departmentRepository.save(department);
        }
    }

    public ResponseEntity<Department> updateDepartment(String id, Department department) {
        if (departmentRepository.existsById(id)) {
            return new ResponseEntity<>(departmentRepository.save(department), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(departmentRepository.save(department), HttpStatus.CREATED);
        }
    }

    public void deleteDepartment(String id) {
        departmentRepository.deleteById(id);
    }

    public void addCourseToDepartment(String departmentId, Course course) {
        try {
            courseRepository.save(course);
            Department department = this.getDepartmentById(departmentId);
            department.addCourse(course);
            updateDepartment(departmentId, department);
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
