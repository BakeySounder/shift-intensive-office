package by.koronatech.office.services_test;

import by.koronatech.office.api.controller.dto.AddEmployeeDTO;
import by.koronatech.office.api.controller.dto.GetEmployeeDTO;
import by.koronatech.office.core.entities.EmployeeEntity;
import by.koronatech.office.core.utils.exceptions.EmployeeNotFoundException;
import by.koronatech.office.core.repositories.EmployeeRepository;
import by.koronatech.office.core.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeesRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void createEmployee_ShouldReturnGetEmployeeDTO() {
        // Arrange
        AddEmployeeDTO addEmployeeDTO = new AddEmployeeDTO();
        addEmployeeDTO.setName("Test Employee");
        addEmployeeDTO.setDepartment("Test Department");

        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setName("Test Employee");
        employeeEntity.setDepartment("Test Department");
        employeeEntity.setId(1L);

        GetEmployeeDTO getEmployeeDTO = new GetEmployeeDTO();
        getEmployeeDTO.setName("Test Employee");
        getEmployeeDTO.setDepartment("Test Department");
        getEmployeeDTO.setId(1L);

        when(employeesRepository.save(any(EmployeeEntity.class))).thenReturn(employeeEntity);

        // Act
        GetEmployeeDTO result = employeeService.createEmployee(addEmployeeDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Test Employee", result.getName());
        verify(employeesRepository, times(1)).save(any(EmployeeEntity.class));
    }

    @Test
    void findEmployeeInDepartment_ShouldReturnListOfGetEmployeeDTOs() {
        // Arrange
        String department = "Test Department";
        int page = 0;
        int pageSize = 10;

        List<EmployeeEntity> employeeEntities = new ArrayList<>();
        EmployeeEntity employee1 = new EmployeeEntity();
        employee1.setId(1L);
        employee1.setName("Employee 1");
        employee1.setDepartment(department);
        employeeEntities.add(employee1);

        EmployeeEntity employee2 = new EmployeeEntity();
        employee2.setId(2L);
        employee2.setName("Employee 2");
        employee2.setDepartment(department);
        employeeEntities.add(employee2);

        Page<EmployeeEntity> employeePage = new PageImpl<>(employeeEntities, PageRequest.of(page, pageSize), employeeEntities.size());

        when(employeesRepository.findByDepartment(department, PageRequest.of(page, pageSize))).thenReturn(employeePage);

        // Act
        List<GetEmployeeDTO> result = employeeService.findEmployeeInDepartment(department, page, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(employeesRepository, times(1)).findByDepartment(department, PageRequest.of(page, pageSize));
    }

    @Test
    void setManager_ShouldReturnGetEmployeeDTO() {
        // Arrange
        Long employeeId = 1L;
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(employeeId);
        employeeEntity.setName("Test Employee");
        employeeEntity.setManager(false);

        when(employeesRepository.findAllById(employeeId)).thenReturn(Optional.of(employeeEntity));
        when(employeesRepository.save(any(EmployeeEntity.class))).thenReturn(employeeEntity);

        // Act
        GetEmployeeDTO result = employeeService.setManager(employeeId);

        // Assert
        assertNotNull(result);
        verify(employeesRepository, times(1)).findAllById(employeeId);
        verify(employeesRepository, times(1)).save(any(EmployeeEntity.class));
    }

    @Test
    void setManager_ShouldThrowEmployeeNotFoundException_WhenEmployeeNotFound() {
        // Arrange
        Long employeeId = 1L;
        when(employeesRepository.findAllById(employeeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.setManager(employeeId));
        verify(employeesRepository, times(1)).findAllById(employeeId);
    }

    @Test
    void updateEmployee_ShouldReturnGetEmployeeDTO() {
        // Arrange
        Long employeeId = 1L;
        AddEmployeeDTO employeeData = new AddEmployeeDTO();
        employeeData.setName("Updated Employee");

        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(employeeId);
        employeeEntity.setName("Original Employee");

        when(employeesRepository.findAllById(employeeId)).thenReturn(Optional.of(employeeEntity));
        when(employeesRepository.save(any(EmployeeEntity.class))).thenReturn(employeeEntity);

        // Act
        GetEmployeeDTO result = employeeService.updateEmployee(employeeId, employeeData);

        // Assert
        assertNotNull(result);
        verify(employeesRepository, times(1)).findAllById(employeeId);
        verify(employeesRepository, times(1)).save(any(EmployeeEntity.class));
    }

    @Test
    void updateEmployee_ShouldThrowEmployeeNotFoundException_WhenEmployeeNotFound() {
        // Arrange
        Long employeeId = 1L;
        AddEmployeeDTO employeeData = new AddEmployeeDTO();
        employeeData.setName("Updated Employee");

        when(employeesRepository.findAllById(employeeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployee(employeeId, employeeData));
        verify(employeesRepository, times(1)).findAllById(employeeId);
    }

    @Test
    void fireEmployeeFromDepartment_ShouldDeleteEmployee() {
        // Arrange
        Long employeeId = 1L;
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(employeeId);
        employeeEntity.setName("Test Employee");

        when(employeesRepository.findAllById(employeeId)).thenReturn(Optional.of(employeeEntity));

        // Act
        employeeService.fireEmployeeFromDepartment(employeeId);

        // Assert
        verify(employeesRepository, times(1)).findAllById(employeeId);
        verify(employeesRepository, times(1)).delete(employeeEntity);
    }

    @Test
    void fireEmployeeFromDepartment_ShouldThrowEmployeeNotFoundException_WhenEmployeeNotFound() {
        // Arrange
        Long employeeId = 1L;
        when(employeesRepository.findAllById(employeeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.fireEmployeeFromDepartment(employeeId));
        verify(employeesRepository, times(1)).findAllById(employeeId);
    }
}
