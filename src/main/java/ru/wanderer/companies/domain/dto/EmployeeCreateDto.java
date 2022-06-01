package ru.wanderer.companies.domain.dto;

import lombok.Value;
import ru.wanderer.companies.domain.entity.Role;

import java.time.LocalDate;

@Value
public class EmployeeCreateDto {
    String firstname;
    String lastname;
    String email;
    LocalDate birthDate;
    Role role;
    Integer departmentId;
}
