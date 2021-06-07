package com.example.demo.Course;

import com.example.demo.Student.Student;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table (name = "course")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "code")
public class Course {
    @Id
    private String code;
    private String name;
    private int maxEnrollment;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="course_student",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;

    public Course() {

    }

    public Course(String code, String name, int maxEnrollment) {
        this.code = code;
        this.name = name;
        this.maxEnrollment = maxEnrollment;
    }

    public Course(String code, String name, int maxEnrollment, Set<Student> students) {
        this.code = code;
        this.name = name;
        this.maxEnrollment = maxEnrollment;
        this.students = students;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
