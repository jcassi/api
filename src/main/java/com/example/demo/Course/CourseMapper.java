package com.example.demo.Course;

import com.example.demo.Student.Student;
import com.example.demo.Student.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "maxEnrollment", target = "maxEnrollment"),
    })
    CourseDto courseToCourseDto(Course course);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "maxEnrollment", target = "maxEnrollment"),
    })
    Course courseDtoToCourse(CourseDto courseDto);
}
