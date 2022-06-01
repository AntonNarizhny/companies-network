package ru.wanderer.companies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.wanderer.companies.domain.entity.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * " +
                    "FROM employee")
    List<Employee> findAll();

    @Query(
            nativeQuery = true,
            value = "SELECT * " +
                    "FROM employee e " +
                    "WHERE e.id = :id")
    Optional<Employee> findById(@Param("id") Long id);

    @Query(
            nativeQuery = true,
            value = "SELECT * " +
                    "FROM employee e " +
                    "WHERE e.email = :email"
    )
    Optional<Employee> findByEmail(@Param("email") String email);

    @Query(
            nativeQuery = true,
            value = "SELECT * " +
                    "FROM employee e " +
                    "WHERE e.activation_code = :activation_code"
    )
    Optional<Employee> findByActivationCode(@Param("activation_code") UUID activationCode);
}
