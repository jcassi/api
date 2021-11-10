package com.example.demo.Department;

import com.example.demo.Course.Course;
import com.example.demo.Course.CourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
    })
    DepartmentDto departmentToDepartmentDto(Department department);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
    })
    Department departmentDtoToDepartment(DepartmentDto departmentDto);
}
