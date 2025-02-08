package by.koronatech.office.controllers_test;

import by.koronatech.office.api.controller.EmployeeController;
import by.koronatech.office.api.controller.dto.AddEmployeeDTO;
import by.koronatech.office.api.controller.dto.GetEmployeeDTO;
import by.koronatech.office.core.services.EmployeeService;
import by.koronatech.office.core.utils.exceptions.EmployeeNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    void createEmployee_ShouldReturnCreatedEmployee() {
        // Arrange
        AddEmployeeDTO addEmployeeDTO = new AddEmployeeDTO(); // Заполните DTO данными для теста
        addEmployeeDTO.setName("Test Employee");
        addEmployeeDTO.setDepartment("Test Department");

        GetEmployeeDTO mockEmployee = new GetEmployeeDTO();
        mockEmployee.setId(1L);
        mockEmployee.setName("Test Employee");
        mockEmployee.setDepartment("Test Department");

        when(employeeService.createEmployee(addEmployeeDTO)).thenReturn(mockEmployee);

        // Act
        GetEmployeeDTO result = employeeController.createEmployee(addEmployeeDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Employee", result.getName());
        verify(employeeService, times(1)).createEmployee(addEmployeeDTO);
    }

    @Test
    void findEmployeeInDepartment_ShouldReturnListOfEmployees() {
        // Arrange
        String department = "Test Department";
        int page = 0;
        int pageSize = 10;

        List<GetEmployeeDTO> mockEmployees = new ArrayList<>();
        GetEmployeeDTO employee1 = new GetEmployeeDTO();
        employee1.setId(1L);
        employee1.setName("Employee 1");
        mockEmployees.add(employee1);

        when(employeeService.findEmployeeInDepartment(department, page, pageSize)).thenReturn(mockEmployees);

        // Act
        List<GetEmployeeDTO> result = employeeController.findEmployeeInDepartment(department, page, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Employee 1", result.get(0).getName());
        verify(employeeService, times(1)).findEmployeeInDepartment(department, page, pageSize);
    }

    @Test
    void setManager_ShouldReturnUpdatedEmployee() {
        // Arrange
        Long employeeId = 1L;
        GetEmployeeDTO mockEmployee = new GetEmployeeDTO();
        mockEmployee.setId(employeeId);
        mockEmployee.setName("Test Employee (Manager)");
        mockEmployee.setManager(true); // Предполагаем, что в GetEmployeeDTO есть поле "manager"

        when(employeeService.setManager(employeeId)).thenReturn(mockEmployee);

        // Act
        GetEmployeeDTO result = employeeController.setManager(employeeId);

        // Assert
        assertNotNull(result);
        assertEquals(employeeId, result.getId());
        assertTrue(result.getManager());
        verify(employeeService, times(1)).setManager(employeeId);
    }

    @Test
    void updateEmployee_ShouldReturnUpdatedEmployee() {
        // Arrange
        Long employeeId = 1L;
        AddEmployeeDTO employeeData = new AddEmployeeDTO(); // Заполните данными для обновления
        employeeData.setName("Updated Employee");

        GetEmployeeDTO mockEmployee = new GetEmployeeDTO();
        mockEmployee.setId(employeeId);
        mockEmployee.setName("Updated Employee");

        when(employeeService.updateEmployee(employeeId, employeeData)).thenReturn(mockEmployee);

        // Act
        GetEmployeeDTO result = employeeController.updateEmployee(employeeId, employeeData);

        // Assert
        assertNotNull(result);
        assertEquals(employeeId, result.getId());
        assertEquals("Updated Employee", result.getName());
        verify(employeeService, times(1)).updateEmployee(employeeId, employeeData);
    }

    @Test
    void fireEmployeeFromDepartment_ShouldCallServiceMethod() {
        // Arrange
        Long employeeId = 1L;

        // Act
        employeeController.fireEmployeeFromDepartment(employeeId);

        // Assert
        verify(employeeService, times(1)).fireEmployeeFromDepartment(employeeId);
    }

    @Test
    void handleException_ShouldReturnBadRequest_WhenEmployeeNotFound() {
        // Arrange
        EmployeeNotFoundException exception = new EmployeeNotFoundException("Employee not found");

        // Act
        ResponseEntity<Object> response = employeeController.handleException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Employee not found", response.getBody());
    }
}
