package com.example.demo.Course;

import com.example.demo.Department.Department;
import com.example.demo.Student.Student;
import com.example.demo.Student.StudentNotFoundException;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table (name = "course")
public class Course {
    @Id
    private String id;
    private String name;
    private int maxEnrollment;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="course_student",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;

    @JsonIgnore
    @ManyToOne()
    private Department department;

    public Course() {

    }

    public Course(String id, String name, int maxEnrollment) {
        this.id = id;
        this.name = name;
        this.maxEnrollment = maxEnrollment;
        this.students = new HashSet<>();
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

    public Department getDepartment() {return department;}

    public void setDepartment(Department department) {this.department = department;}

    public void addStudent(Student student) {
        if (this.students.size() >= this.maxEnrollment) {
            throw new CourseAlreadyFullException ();
        }
        this.students.add(student);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", maxEnrollment=" + maxEnrollment +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return getMaxEnrollment() == course.getMaxEnrollment() && Objects.equals(getId(), course.getId()) && Objects.equals(getName(), course.getName()) && Objects.equals(getStudents(), course.getStudents()) && Objects.equals(getDepartment(), course.getDepartment());
    }

    public void deleteStudent(Student student) {
        if (students.contains(student)) {
            System.out.println("OUT");
            students.remove(student);
        } else {
            System.out.println("no tiene");
            throw new StudentNotFoundException();
        }
    }
}
