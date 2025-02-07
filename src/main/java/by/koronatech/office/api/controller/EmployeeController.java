package by.koronatech.office.api.controller;

import by.koronatech.office.api.controller.dto.AddEmployeeDTO;
import by.koronatech.office.api.controller.dto.GetDepartmentDTO;
import by.koronatech.office.api.controller.dto.GetEmployeeDTO;
import by.koronatech.office.core.exceptions.EmployeeNotFoundException;
import by.koronatech.office.core.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    @PostMapping
    public GetEmployeeDTO createEmployee(@RequestBody AddEmployeeDTO addEmployeeDTO) {
        return employeeService.createEmployee(addEmployeeDTO);
    }
    @GetMapping
    public List<GetEmployeeDTO> findEmployeeInDepartment(@RequestParam String department, @RequestParam int from, @RequestParam int count) {
        return employeeService.findEmployeeInDepartment(department, from, count);
    }

    @PatchMapping("/{employeeId}")
    public GetEmployeeDTO setManager(@PathVariable Long employeeId) {
        return employeeService.setManager(employeeId);
    }

    @PutMapping("/{employeeId}")
    public GetEmployeeDTO updateEmployee(@PathVariable Long employeeId, @RequestBody AddEmployeeDTO employeeData) {
        return employeeService.updateEmployee(employeeId,employeeData);
    }

    @DeleteMapping("/{employeeId}")
    public void fireEmployeeFromDepartment(@PathVariable Long employeeId) {
        employeeService.fireEmployeeFromDepartment(employeeId);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Object> handleException(EmployeeNotFoundException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
