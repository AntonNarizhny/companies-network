package ru.wanderer.companies.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.wanderer.companies.domain.dto.EmployeeCreateDto;
import ru.wanderer.companies.domain.dto.EmployeeEditDto;
import ru.wanderer.companies.domain.entity.Role;
import ru.wanderer.companies.services.DepartmentService;
import ru.wanderer.companies.services.EmployeeService;

import java.util.UUID;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employee/employees";
    }

    @GetMapping("/{id}")
    public String findById(Model model,
                           @PathVariable Long id) {
        return employeeService.findById(id)
                .map(employee -> {
                    model.addAttribute("employee", employee);
                    return "employee/employee";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,
                       @PathVariable Long id) {
        return employeeService.findById(id)
                .map(employee -> {
                    model.addAttribute("employee", employee);
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("departments", departmentService.findAll());
                    return "employee/edit";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registration")
    public String registrationPage(Model model,
                                   @ModelAttribute("employee") EmployeeCreateDto employeeCreateDto) {
        model.addAttribute("employee", employeeCreateDto);// понадобится для валидации
        model.addAttribute("roles", Role.values());
        model.addAttribute("departments", departmentService.findAll());
        return "employee/registration";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model,
                           @PathVariable UUID code) {
        String message = "Ваш аккаунт активирован. На вашу почту будет выслан пароль для входа.";

        boolean isActivated = employeeService.activate(code);

        if (isActivated) {
            model.addAttribute("message", message);
        } else {
            model.addAttribute("message", "Такой код активации не найден");
        }

        return "employee/activate";
    }

    @PostMapping
    public String create(@ModelAttribute EmployeeCreateDto employeeCreateDto,
                         RedirectAttributes redirectAttributes) {
        /*if (true) {
            redirectAttributes.addFlashAttribute("employee", employeeCreateDto);
            return "redirect:/employees/registration";
        }*/
        return "redirect:/employees/" + employeeService.create(employeeCreateDto).getId();
    }

    @PutMapping("{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute EmployeeEditDto employeeEditDto) {
        return employeeService.update(id, employeeEditDto)
                .map(it -> "redirect:/employees/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id) {
        if (!employeeService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return "redirect:employees";
    }
}
