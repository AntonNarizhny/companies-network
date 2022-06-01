package ru.wanderer.companies.domain.mappers;

import org.springframework.stereotype.Component;
import ru.wanderer.companies.domain.dto.DepartmentReadDto;
import ru.wanderer.companies.domain.entity.Department;

@Component
public class DepartmentReadMapper implements Mapper<Department, DepartmentReadDto> {

    @Override
    public DepartmentReadDto map(Department object) {
        return new DepartmentReadDto(
                object.getId(),
                object.getName()
        );
    }
}
