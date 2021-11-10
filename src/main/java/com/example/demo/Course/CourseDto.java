package com.example.demo.Course;

import java.util.Objects;

public class CourseDto {
    private String id;
    private String name;
    private int maxEnrollment;

    public CourseDto(String id, String name, int maxEnrollment) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseDto)) return false;
        CourseDto courseDto = (CourseDto) o;
        return getMaxEnrollment() == courseDto.getMaxEnrollment() && Objects.equals(getId(), courseDto.getId()) && Objects.equals(getName(), courseDto.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getMaxEnrollment());
    }

}
