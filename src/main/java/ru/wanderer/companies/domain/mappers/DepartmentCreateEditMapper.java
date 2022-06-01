package ru.wanderer.companies.domain.mappers;

import org.springframework.stereotype.Component;
import ru.wanderer.companies.domain.dto.DepartmentCreateEditDto;
import ru.wanderer.companies.domain.entity.Department;

@Component
public class DepartmentCreateEditMapper implements Mapper<DepartmentCreateEditDto, Department> {

    @Override
    public Department map(DepartmentCreateEditDto object) {
        Department department = new Department();
        copy(object, department);

        return department;
    }

    @Override
    public Department map(DepartmentCreateEditDto fromObject, Department toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(DepartmentCreateEditDto departmentDto, Department department) {
        department.setName(departmentDto.getName());
    }
}
