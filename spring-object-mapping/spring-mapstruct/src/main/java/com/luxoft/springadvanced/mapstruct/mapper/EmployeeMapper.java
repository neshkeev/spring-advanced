package com.luxoft.springadvanced.mapstruct.mapper;

import com.luxoft.springadvanced.mapstruct.domain.Department;
import com.luxoft.springadvanced.mapstruct.domain.Employee;
import com.luxoft.springadvanced.mapstruct.dto.DepartmentDTO;
import com.luxoft.springadvanced.mapstruct.dto.EmployeeDTO;
import org.mapstruct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@SuppressWarnings("unused")
@Mapper
public interface EmployeeMapper {
    Logger LOG = LoggerFactory.getLogger(EmployeeMapper.class);

    @Mappings({ @Mapping(target = "empId", source = "entity.id"), @Mapping(target = "empName", source = "entity.name"), @Mapping(target = "dept", source = "entity.department")})
    EmployeeDTO employeeToEmployeeDTO(Employee entity);

    @Mappings({ @Mapping(target = "id", source = "dto.empId"), @Mapping(target = "name", source = "dto.empName"), @Mapping(target = "department", source = "dto.dept")})
    Employee employeeDTOtoEmployee(EmployeeDTO dto);

    DepartmentDTO divisionToDepartmentDTO(Department entity);

    Department divisionDTOtoDepartment(DepartmentDTO dto);

    List<Employee> convertEmployeeDTOListToEmployeeList(List<EmployeeDTO> list);

    List<EmployeeDTO> convertEmployeeListToEmployeeDTOList(List<Employee> list);

    @BeforeMapping
    default void postMapping(Employee emp, @MappingTarget EmployeeDTO employeeDTO) {
        LOG.info("Before Mapping Employee to EmployeeDTO");
    }

    @AfterMapping
    default void postMapping(@MappingTarget EmployeeDTO employeeDTO) {
        LOG.info("After Mapping Employee to EmployeeDTO");
    }
}
