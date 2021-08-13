package com.example.demo.Course;

import com.example.demo.Department.Department;
import com.example.demo.Student.Student;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table (name = "course")
public class Course {
    @Id
    private String id;
    private String name;
    private int maxEnrollment;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name="course_student",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;

    @ManyToOne
    private Department department;

    public Course() {

    }

    public Course(String id, String name, int maxEnrollment) {
        this.id = id;
        this.name = name;
        this.maxEnrollment = maxEnrollment;
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

    public void addStudent(Student student) {
        if (this.students.size() >= this.maxEnrollment) {
            throw new CourseAlreadyFullException ();
        }
        this.students.add(student);
    }
}
