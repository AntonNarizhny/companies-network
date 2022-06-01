package ru.wanderer.companies.domain.exceptions;

public class EmployeeExistsException extends RuntimeException{

    public EmployeeExistsException(String message) {
        super(message);
    }
}
