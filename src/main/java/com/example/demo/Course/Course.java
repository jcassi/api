package com.example.demo.Course;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table
public class Course {
    @Id
    int code;
    String name;
    int maxEnrollment;

    //@ManyToMany
    //private Set<Student> students;

    public Course() {

    }

    public Course(int code, String name, int maxCapacity) {
        this.code = code;
        this.name = name;
        this.maxEnrollment = maxCapacity;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
