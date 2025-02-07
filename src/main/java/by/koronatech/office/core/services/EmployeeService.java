package by.koronatech.office.core.services;

import by.koronatech.office.api.controller.dto.AddEmployeeDTO;
import by.koronatech.office.api.controller.dto.GetEmployeeDTO;
import by.koronatech.office.core.entities.Employee;
import by.koronatech.office.core.exceptions.EmployeeNotFoundException;
import by.koronatech.office.core.mapper.employee.AddEmployeeMapper;
import by.koronatech.office.core.mapper.employee.GetEmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private static long idCounter = 0;

    private final List<Employee> employeesRepository = new ArrayList<>();


    public GetEmployeeDTO createEmployee(AddEmployeeDTO addEmployeeDTO) {

        Employee created_employee = AddEmployeeMapper.INSTANCE.toEntity(addEmployeeDTO);
        created_employee.setId(idCounter);
        idCounter++;

        employeesRepository.add(created_employee);
        return GetEmployeeMapper.INSTANCE.toDto(created_employee);
    }
    public List<GetEmployeeDTO> findEmployeeInDepartment( String department, int from, int count) {
        return GetEmployeeMapper.INSTANCE.toDtos(
                employeesRepository.stream()
                        .filter(e -> e.getDepartment().equals(department))
                        .skip(from)
                        .limit(count)
                        .toList()
        );
    }

    public GetEmployeeDTO setManager(Long employeeId) {
        Employee employee = findEmployee(employeeId);
        employee.setManager(true);
        return GetEmployeeMapper.INSTANCE.toDto(employee);
    }

    public GetEmployeeDTO updateEmployee( Long employeeId, AddEmployeeDTO employeeData) {
        Employee employee = findEmployee(employeeId);
        return GetEmployeeMapper.INSTANCE.toDto(
                AddEmployeeMapper.INSTANCE.merge(employee,employeeData)
        );
    }

    public void fireEmployeeFromDepartment( Long employeeId) {
        Employee employee = findEmployee(employeeId);
        employeesRepository.remove(employee);
    }

    private Employee findEmployee(Long employeeId) {
        Employee employee = employeesRepository.stream()
                .filter(e -> e.getId().equals(employeeId))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        return employee;
    }

}
