package ru.wanderer.companies.domain.dto;

import lombok.Value;
import ru.wanderer.companies.domain.entity.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
public class EmployeeReadDto {
    Long id;
    String username;
    boolean activated;
    String firstname;
    String lastname;
    String email;
    String phoneNumber;
    LocalDate birthDate;
    LocalDateTime lastVisit;
    Integer salary;
    Role role;
    DepartmentReadDto department;
}
