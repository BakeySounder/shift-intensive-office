package by.koronatech.office.api.controller;


import by.koronatech.office.api.controller.dto.GetDepartmentDTO;
import by.koronatech.office.core.services.DepartmentService;
import by.koronatech.office.core.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public List<GetDepartmentDTO> allDepartments(@RequestParam int from, @RequestParam int count) {
        return departmentService.getAllDepartments(from, count);
    }


}
