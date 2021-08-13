package com.example.demo.Department;

import com.example.demo.Course.Course;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "department")
public class Department {

    @Id
    String id;

    String name;

    @OneToMany(mappedBy = "department")
    Set<Course> courses;

    public void addCourse(Course course) {
        courses.add(course);
    }

    public Department() {
    }

    public Department(String id, String name, Set<Course> courses) {
        this.id = id;
        this.name = name;
        this.courses = courses;
    }

    public String getCode() {
        return id;
    }

    public void setCode(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
