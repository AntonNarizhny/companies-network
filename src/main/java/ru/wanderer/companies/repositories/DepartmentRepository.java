package ru.wanderer.companies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.wanderer.companies.domain.entity.Department;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query(
            nativeQuery = true,
            value = "SELECT * " +
                    "FROM department")
    List<Department> findAll();

    @Query(
            nativeQuery = true,
            value = "SELECT * " +
                    "FROM department d " +
                    "WHERE d.id = :id"
    )
    Optional<Department> findById(@Param("id") Integer id);
}
