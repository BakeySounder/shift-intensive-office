package by.koronatech.office.controllers_test;

import by.koronatech.office.api.controller.DepartmentController;
import by.koronatech.office.api.controller.dto.GetDepartmentDTO;
import by.koronatech.office.core.services.DepartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Активирует Mockito аннотации
public class DepartmentControllerTest {

    @Mock // Создает мок DepartmentService
    private DepartmentService departmentService;

    @InjectMocks // Внедряет мок DepartmentService в DepartmentController
    private DepartmentController departmentController;

    @Test
    void allDepartments_ShouldReturnListOfDepartments() {
        // Arrange
        int page = 0;
        int pageSize = 10;

        List<GetDepartmentDTO> mockDepartments = new ArrayList<>();
        GetDepartmentDTO department1 = new GetDepartmentDTO();
        department1.setId(1L);
        department1.setName("Department 1");
        mockDepartments.add(department1);

        GetDepartmentDTO department2 = new GetDepartmentDTO();
        department2.setId(2L);
        department2.setName("Department 2");
        mockDepartments.add(department2);

        when(departmentService.getAllDepartments(page, pageSize)).thenReturn(mockDepartments); // Мокируем поведение сервиса

        // Act
        List<GetDepartmentDTO> result = departmentController.allDepartments(page, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Department 1", result.get(0).getName());
        assertEquals("Department 2", result.get(1).getName());

        verify(departmentService, times(1)).getAllDepartments(page, pageSize); // Проверяем, что метод сервиса был вызван
    }

    @Test
    void allDepartments_ShouldReturnEmptyList_WhenNoDepartmentsExist() {
        // Arrange
        int page = 0;
        int pageSize = 10;

        when(departmentService.getAllDepartments(page, pageSize)).thenReturn(new ArrayList<>()); // Мокируем пустой список

        // Act
        List<GetDepartmentDTO> result = departmentController.allDepartments(page, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());

        verify(departmentService, times(1)).getAllDepartments(page, pageSize); // Проверяем вызов сервиса
    }


}
