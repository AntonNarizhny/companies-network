package ru.wanderer.companies.domain.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class EmployeeEditDto {
    String username;
    String firstname;
    String lastname;
    String email;
    String phoneNumber;
    LocalDate birthDate;
}
