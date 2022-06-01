package ru.wanderer.companies.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wanderer.companies.domain.dto.DepartmentCreateEditDto;
import ru.wanderer.companies.domain.dto.DepartmentReadDto;
import ru.wanderer.companies.domain.mappers.DepartmentCreateEditMapper;
import ru.wanderer.companies.domain.mappers.DepartmentReadMapper;
import ru.wanderer.companies.repositories.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentReadMapper departmentReadMapper;
    private final DepartmentCreateEditMapper departmentCreateEditMapper;

    public List<DepartmentReadDto> findAll() {
        return departmentRepository.findAll().stream()
                .map(departmentReadMapper::map)
                .toList();
    }

    public Optional<DepartmentReadDto> findById(Integer id) {
        return departmentRepository.findById(id)
                .map(departmentReadMapper::map);
    }

    @Transactional
    public void create(DepartmentCreateEditDto departmentDto) {
        Optional.of(departmentDto)
                .map(departmentCreateEditMapper::map)
                .map(departmentRepository::save)
                .orElseThrow();
    }

    @Transactional
    public Optional<DepartmentReadDto> update(Integer id, DepartmentCreateEditDto departmentDto) {
        return departmentRepository.findById(id)
                .map(entity -> departmentCreateEditMapper.map(departmentDto, entity))
                .map(departmentRepository::saveAndFlush)
                .map(departmentReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return departmentRepository.findById(id)
                .map(entity -> {
                    departmentRepository.delete(entity);
                    departmentRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
