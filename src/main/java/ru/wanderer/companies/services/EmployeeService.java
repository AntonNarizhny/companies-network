package ru.wanderer.companies.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wanderer.companies.domain.dto.EmployeeCreateDto;
import ru.wanderer.companies.domain.dto.EmployeeEditDto;
import ru.wanderer.companies.domain.dto.EmployeeReadDto;
import ru.wanderer.companies.domain.dto.Email;
import ru.wanderer.companies.domain.entity.Employee;
import ru.wanderer.companies.domain.exceptions.EmployeeExistsException;
import ru.wanderer.companies.domain.mappers.EmployeeCreateMapper;
import ru.wanderer.companies.domain.mappers.EmployeeEditMapper;
import ru.wanderer.companies.domain.mappers.EmployeeReadMapper;
import ru.wanderer.companies.repositories.EmployeeRepository;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeReadMapper employeeReadMapper;
    private final EmployeeCreateMapper employeeCreateMapper;
    private final EmployeeEditMapper employeeEditMapper;
    private final MailSenderService mailSenderService;
    private final PasswordGeneratorService passwordGeneratorService;

    public List<EmployeeReadDto> findAll() {
        return employeeRepository.findAll().stream()
                .map(employeeReadMapper::map)
                .toList();
    }

    public Optional<EmployeeReadDto> findById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeReadMapper::map);
    }

    @Transactional
    public EmployeeReadDto create(EmployeeCreateDto employeeCreateDto) {
        if (employeeRepository.findByEmail(employeeCreateDto.getEmail()).isPresent()) {
            throw new EmployeeExistsException("Пользователь с таким почтовым адресом уже зарегистрирован");
        }

        Employee newEmployee = employeeCreateMapper.map(employeeCreateDto);
        newEmployee.setActivationCode(UUID.randomUUID());
        newEmployee.setRegistrationDate(LocalDate.now());
        newEmployee.setAvatar("Его пока нет");

        employeeRepository.saveAndFlush(newEmployee);

        Optional<Employee> employeeFromDb = employeeRepository.findByEmail(newEmployee.getEmail());
        employeeFromDb.ifPresentOrElse(employee -> {
            Map<String, Object> properties = new HashMap<>();
            properties.put("fullName", employee.getFirstname() + " " + employee.getLastname());
            properties.put("registrationDate", employee.getRegistrationDate());
            properties.put("email", employee.getEmail());
            properties.put("link", "http://localhost:8080/employees/activate/" + employee.getActivationCode());

            Email email = new Email(
                    employee.getEmail(),
                    "Активация аккаунта",
                    "email/activate-email",
                    properties
            );

            try {
                mailSenderService.sendEmail(email);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }, () -> new Exception("Что-то пошло не так во время отправки письма на почту"));//Посмотреть как тут это работает

        return employeeReadMapper.map(employeeFromDb.get());
    }

    @Transactional
    public boolean activate(UUID code) {
        return employeeRepository.findByActivationCode(code)
                .map(employee -> {
                    employee.setActivationCode(null);
                    String password = passwordGeneratorService.generatePassayPassword();
                    employee.setPassword(password);
                    employee.setActivated(true);
                    employeeRepository.saveAndFlush(employee);

                    Map<String, Object> properties = new HashMap<>();
                    properties.put("fullName", employee.getFirstname() + " " + employee.getLastname());
                    properties.put("password", password);

                    Email email = new Email(
                            employee.getEmail(),
                            "Пароль для входа в Companies",
                            "email/password-email",
                            properties
                    );

                    try {
                        mailSenderService.sendEmail(email);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }

                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public Optional<EmployeeReadDto> update(Long id, EmployeeEditDto employeeEditDto) {
        return employeeRepository.findById(id)
                .map(entity -> employeeEditMapper.map(employeeEditDto, entity))
                .map(employeeRepository::saveAndFlush)
                .map(employeeReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return employeeRepository.findById(id)
                .map(entity -> {
                    employeeRepository.delete(entity);
                    employeeRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}


