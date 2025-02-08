package by.koronatech.office.core.services;

import by.koronatech.office.api.controller.dto.AddEmployeeDTO;
import by.koronatech.office.api.controller.dto.GetEmployeeDTO;
import by.koronatech.office.core.entities.EmployeeEntity;
import by.koronatech.office.core.exceptions.EmployeeNotFoundException;
import by.koronatech.office.core.mapper.employee.AddEmployeeMapper;
import by.koronatech.office.core.mapper.employee.GetEmployeeMapper;
import by.koronatech.office.core.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeesRepository;

    public GetEmployeeDTO createEmployee(AddEmployeeDTO addEmployeeDTO) {

        EmployeeEntity created_employeeEntity = AddEmployeeMapper.INSTANCE.toEntity(addEmployeeDTO);

        created_employeeEntity = employeesRepository.save(created_employeeEntity);
        return GetEmployeeMapper.INSTANCE.toDto(created_employeeEntity);
    }

    public List<GetEmployeeDTO> findEmployeeInDepartment(String department, int page, int pageSize) {

        return GetEmployeeMapper.INSTANCE.toDtos(
                employeesRepository.findByDepartment(department, PageRequest.of(page, pageSize))
        );
    }

    public GetEmployeeDTO setManager(Long employeeId) {
        EmployeeEntity employeeEntity = employeesRepository
                .findAllById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("SetManager: Employee not found"));
        employeeEntity.setManager(true);
        employeesRepository.save(employeeEntity);
        return GetEmployeeMapper.INSTANCE.toDto(employeeEntity);
    }

    public GetEmployeeDTO updateEmployee(Long employeeId, AddEmployeeDTO employeeData) {
        EmployeeEntity employeeEntity = employeesRepository
                .findAllById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("UpdateEmployee: Employee not found"));
        employeeEntity = AddEmployeeMapper.INSTANCE.merge(employeeEntity, employeeData);
        employeesRepository.save(employeeEntity);
        return GetEmployeeMapper.INSTANCE.toDto(employeeEntity);
    }

    public void fireEmployeeFromDepartment(Long employeeId) {
        EmployeeEntity employeeEntity = employeesRepository
                .findAllById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("DeleteEmployee: Employee not found"));
        employeesRepository.delete(employeeEntity);
    }
}
