package com.example.demo.Student;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;


@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @MockBean
    StudentService studentService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void addOneStudent() throws Exception {
        Student savedStudent = new Student(1L, "Juan", "Pérez", "jp@gmail.com");

        when(studentService.getStudentById(1L)).thenReturn(savedStudent);

        mockMvc.perform(get("/students/1"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.firstName").value("Juan"))
            .andExpect(jsonPath("$.lastName").value("Pérez"))
            .andExpect(jsonPath("$.email").value("jp@gmail.com"));
    }
}

