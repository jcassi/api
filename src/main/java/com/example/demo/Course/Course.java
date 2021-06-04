package com.example.demo.Course;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table (name = "course")
public class Course {
    @Id
    String code;
    String name;
    int maxEnrollment;

    //@ManyToMany
    //private Set<Student> students;

    public Course() {

    }

    public Course(String code, String name, int maxCapacity) {
        this.code = code;
        this.name = name;
        this.maxEnrollment = maxCapacity;
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
}
