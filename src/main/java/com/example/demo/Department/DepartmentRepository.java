package com.example.demo.Department;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, String> {
}
