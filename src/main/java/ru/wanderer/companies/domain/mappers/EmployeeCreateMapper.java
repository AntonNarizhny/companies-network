package ru.wanderer.companies.domain.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.wanderer.companies.domain.dto.EmployeeCreateDto;
import ru.wanderer.companies.domain.entity.Department;
import ru.wanderer.companies.domain.entity.Employee;
import ru.wanderer.companies.repositories.DepartmentRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmployeeCreateMapper implements Mapper<EmployeeCreateDto, Employee> {

    private final DepartmentRepository departmentRepository;

    @Override
    public Employee map(EmployeeCreateDto object) {
        Employee employee = new Employee();

        employee.setFirstname(object.getFirstname());
        employee.setLastname(object.getLastname());
        employee.setEmail(object.getEmail());
        employee.setBirthDate(object.getBirthDate());
        employee.setRole(object.getRole());
        employee.setDepartment(getDepartment(object.getDepartmentId()));

        return employee;

        /*return new Employee(
                object.getFirstname(),
                object.getLastname(),
                object.getEmail(),
                object.getBirthDate(),
                object.getRole(),
                getDepartment(object.getDepartmentId())
        );*/
    }

    private Department getDepartment(Integer departmentId) {
        return Optional.ofNullable(departmentId)
                .flatMap(departmentRepository::findById)
                .orElse(null);
    }
}
