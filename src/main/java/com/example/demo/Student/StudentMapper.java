package com.example.demo.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "firstName", target = "firstName"),
        @Mapping(source = "lastName", target = "lastName"),
        @Mapping(source = "email",  target = "email")
    })
    StudentDto studentToStudentDto(Student student);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "email",  target = "email")
    })
    Student studentDtoToStudent(StudentDto studentDto);
}
