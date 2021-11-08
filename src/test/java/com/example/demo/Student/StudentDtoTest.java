package com.example.demo.Student;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentDtoTest {
    private StudentMapper studentMapper = Mappers.getMapper(StudentMapper.class);

    @Test
    public void test1() {
        Student student = new Student(1L, "juli", "cassi",
                "j@gmail.com");
        StudentDto studentDto = new StudentDto(1L, "juli", "cassi", "j@gmail.com");
        assertEquals(studentDto, studentMapper.studentToStudentDto(student));
        assertEquals(student, studentMapper.studentDtoToStudent(studentDto));
    }
}
