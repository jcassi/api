package com.example.demo.Department;

import com.example.demo.Course.Course;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "department")
public class Department {

    @Id
    String id;

    String name;

    @JsonIgnore
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Course> courses;

    public void addCourse(Course course) {
        courses.add(course);
        course.setDepartment(this);
    }

    public Department() {
    }

    public Department(String id, String name) {
        this.id = id;
        this.name = name;;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Course> getCourses() {return this.courses;}

    public void setCourses(Course course) {this.courses = courses;}
}
