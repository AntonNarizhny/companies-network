package ru.wanderer.companies.domain.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.wanderer.companies.domain.dto.DepartmentReadDto;
import ru.wanderer.companies.domain.dto.EmployeeReadDto;
import ru.wanderer.companies.domain.entity.Employee;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmployeeReadMapper implements Mapper<Employee, EmployeeReadDto> {

    private final DepartmentReadMapper departmentReadMapper;

    @Override
    public EmployeeReadDto map(Employee object) {
        DepartmentReadDto department = Optional.ofNullable(object.getDepartment())
                .map(departmentReadMapper::map)
                .orElse(null);
        return new EmployeeReadDto(
                object.getId(),
                object.getUsername(),
                object.isActivated(),
                object.getFirstname(),
                object.getLastname(),
                object.getEmail(),
                object.getPhoneNumber(),
                object.getBirthDate(),
                object.getLastVisit(),
                object.getSalary(),
                object.getRole(),
                department
        );
    }
}
