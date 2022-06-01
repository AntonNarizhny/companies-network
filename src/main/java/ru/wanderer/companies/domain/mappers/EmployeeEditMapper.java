package ru.wanderer.companies.domain.mappers;

import org.springframework.stereotype.Component;
import ru.wanderer.companies.domain.dto.EmployeeEditDto;
import ru.wanderer.companies.domain.entity.Employee;

@Component
public class EmployeeEditMapper implements Mapper<EmployeeEditDto, Employee>{

    @Override
    public Employee map(EmployeeEditDto fromObject, Employee toObject) {
        toObject.setUsername(fromObject.getUsername());
        toObject.setFirstname(fromObject.getFirstname());
        toObject.setLastname(fromObject.getLastname());
        toObject.setEmail(fromObject.getEmail());
        toObject.setPhoneNumber(fromObject.getPhoneNumber());
        toObject.setBirthDate(fromObject.getBirthDate());

        return toObject;
    }
}
