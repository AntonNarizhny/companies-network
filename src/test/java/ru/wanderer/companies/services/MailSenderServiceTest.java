package ru.wanderer.companies.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.wanderer.companies.domain.dto.Email;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class MailSenderServiceTest {

    @Autowired
    private MailSenderService mailSenderService;

    @Test
    void sendEmail() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("fullName", "Firstname Lastname");
        properties.put("registrationDate", LocalDate.now());
        properties.put("email", "test@gmail.com");
        properties.put("link", "https://localhost:8080/activate/" + UUID.randomUUID());

        Email email = new Email(
                "test@gmail.com",
                "Активация аккаунта",
                "employee/activate-email",
                properties
        );

        assertDoesNotThrow(() -> mailSenderService.sendEmail(email));
    }
}