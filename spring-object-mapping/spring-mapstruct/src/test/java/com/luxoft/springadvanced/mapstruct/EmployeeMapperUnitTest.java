package com.luxoft.springadvanced.mapstruct;

import com.luxoft.springadvanced.mapstruct.domain.Department;
import com.luxoft.springadvanced.mapstruct.domain.Employee;
import com.luxoft.springadvanced.mapstruct.dto.DepartmentDTO;
import com.luxoft.springadvanced.mapstruct.dto.EmployeeDTO;
import com.luxoft.springadvanced.mapstruct.mapper.EmployeeMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeMapperUnitTest {

    private static final EmployeeMapper MAPPER = Mappers.getMapper(EmployeeMapper.class);

    @Test
    @DisplayName("Given an EmployeeDTO, when we map it to an Employee, then the fields with different names are correctly mapped")
    public void testMappingEmployeeDTOToEmployeeWithDifferentFieldsNames() {
        final var dto = new EmployeeDTO(1, "John", null);

        final var entity = MAPPER.employeeDTOtoEmployee(dto);

        assertEquals(dto.empId(), entity.id());
        assertEquals(dto.empName(), entity.name());
    }

    @Test
    @DisplayName("Given an Employee, when we map it to an EmployeeDTO, then the fields with different names are correctly mapped")
    public void testMappingEmployeeToEmployeeDTOWithDifferentFieldsNames() {
        final var entity = new Employee(1, "John", null);

        final var dto = MAPPER.employeeToEmployeeDTO(entity);

        assertEquals(dto.empId(), entity.id());
        assertEquals(dto.empName(), entity.name());
    }

    @Test
    @DisplayName("Given an EmployeeDTO with a nested object, when we map it to an Employee, then the fields are correctly mapped")
    public void testEmployeeDTOWithNestedObjectToEmployee() {
        final var department = new DepartmentDTO(1, "Department1");
        final var dto = new EmployeeDTO(1, "John", department);

        final var entity = MAPPER.employeeDTOtoEmployee(dto);

        assertEquals(dto.dept().id(), entity.department().id());
        assertEquals(dto.dept().name(), entity.department().name());
    }

    @Test
    @DisplayName("Given an Employee with a nested object, when we map it to an EmployeeDTO, then the fields are correctly mapped")
    public void testEmployeeWithNestedObjectToEmployeeDTO() {
        final var department = new Department(1, "Department1");
        final var entity = new Employee(1, "John", department);

        final var dto = MAPPER.employeeToEmployeeDTO(entity);

        assertEquals(dto.dept().id(), entity.department().id());
        assertEquals(dto.dept().name(), entity.department().name());
    }

    @Test
    @DisplayName("Given a list of Employee, when we map it to a list of EmployeeDTO, then the fields with different names are correctly mapped")
    public void testEmployeeListToEmployeeDTOList() {
        final var department = new Department(1, "Department1");
        final var emp = new Employee(1, "John", department);
        final var employeeList = List.of(emp);

        final var employeeDtoList = MAPPER.convertEmployeeListToEmployeeDTOList(employeeList);
        EmployeeDTO employeeDTO = employeeDtoList.get(0);
        assertEquals(employeeDTO.empId(), emp.id());
        assertEquals(employeeDTO.empName(), emp.name());
        assertEquals(employeeDTO.dept().id(), emp.department().id());
        assertEquals(employeeDTO.dept().name(), emp.department().name());
    }

    @Test
    @DisplayName("Given a list of EmployeeDTO, when we map it to a list of Employee, then the fields with different names are correctly mapped")
    public void testEmployeeDTOListToEmployeeList() {
        final var dept = new DepartmentDTO(1, "Department1");
        final var empDTO = new EmployeeDTO(1, "John", dept);
        final var employeeDTOList = List.of(empDTO);

        final var employeeList = MAPPER.convertEmployeeDTOListToEmployeeList(employeeDTOList);
        final var employee = employeeList.get(0);

        assertEquals(employee.id(), empDTO.empId());
        assertEquals(employee.name(), empDTO.empName());
        assertEquals(employee.department().id(), empDTO.dept().id());
        assertEquals(employee.department().name(), empDTO.dept().name());
    }
}
