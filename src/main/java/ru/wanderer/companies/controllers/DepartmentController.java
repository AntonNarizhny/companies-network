package ru.wanderer.companies.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.wanderer.companies.domain.dto.DepartmentCreateEditDto;
import ru.wanderer.companies.services.DepartmentService;

@Controller
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        return "department/departments";
    }

    @GetMapping("/{id}")
    public String findById(Model model,
                           @PathVariable Integer id) {
        return departmentService.findById(id)
                .map(department -> {
                    model.addAttribute("department", department);
                    return "department/department";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/new")
    public String createPage(Model model,
                             @ModelAttribute("department") DepartmentCreateEditDto departmentDto) {
        model.addAttribute("department", departmentDto);// понадобится для валидации
        return "department/new";
    }

    @PostMapping
    public String create(@ModelAttribute DepartmentCreateEditDto departmentDto,
                         RedirectAttributes redirectAttributes) {
        /*if (true) {
            redirectAttributes.addFlashAttribute("department", departmentDto);
            return "redirect:/departments/new";
        }*/
        departmentService.create(departmentDto);
        return "redirect:/departments";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Integer id,
                         @ModelAttribute DepartmentCreateEditDto departmentDto) {
        return departmentService.update(id, departmentDto)
                .map(it -> "redirect:/departments")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        if(!departmentService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/departments";
    }
}
