package com.example.demo.CourseTest;

import com.example.demo.Course.Course;
import com.example.demo.Course.CourseDto;
import com.example.demo.Course.CourseMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseDtoTest {
    private CourseMapper courseMapper = Mappers.getMapper(CourseMapper.class);

    @Disabled("check hashset comparison issue")
    @Test
    public void courseToDto() {
        Course course = new Course("8101", "Análisis Matemático II", 15);
        CourseDto courseDto = new CourseDto("8101", "Análisis Matemático II", 15);

        assertEquals(course, courseMapper.courseDtoToCourse(courseDto));
    }

    @Test
    public void DtoToCourse() {
        Course course = new Course("8101", "Análisis Matemático II", 15);
        CourseDto courseDto = new CourseDto("8101", "Análisis Matemático II", 15);

        assertEquals(courseDto, courseMapper.courseToCourseDto(course));
    }
}
