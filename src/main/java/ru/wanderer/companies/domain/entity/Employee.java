package ru.wanderer.companies.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true, length = 64)
    private String username;

    @Column(name = "password", length = 128)
    private String password;

    @Column(name = "activation_code")
    private UUID activationCode;

    @Column(name = "activated")
    private boolean activated;

    @Column(name = "firstname", nullable = false, length = 64)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 64)
    private String lastname;

    @Column(name = "avatar", nullable = false, length = 64)
    private String avatar;

    @Column(name = "email", unique = true, nullable = false, length = 32)
    private String email;

    @Column(name = "phone_number", unique = true, length = 32)
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "last_visit")
    private LocalDateTime lastVisit;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "role", length = 16)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}
