package by.koronatech.office.services_test;

import by.koronatech.office.api.controller.dto.GetDepartmentDTO;
import by.koronatech.office.core.entities.DepartmentEntity;
import by.koronatech.office.core.utils.mapper.department.GetDepartmentMapper;
import by.koronatech.office.core.repositories.DepartmentRepository;
import by.koronatech.office.core.services.DepartmentService;
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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentEntityRepository;

    @Mock
    private GetDepartmentMapper getDepartmentMapper;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void getAllDepartments_ShouldReturnListOfGetDepartmentDTOs() {
        // Arrange
        int page = 0;
        int pageSize = 10;

        List<DepartmentEntity> departmentEntities = new ArrayList<>();
        DepartmentEntity department1 = new DepartmentEntity();
        department1.setId(1L);
        department1.setName("Department 1");
        departmentEntities.add(department1);

        DepartmentEntity department2 = new DepartmentEntity();
        department2.setId(2L);
        department2.setName("Department 2");
        departmentEntities.add(department2);

        Page<DepartmentEntity> departmentPage = new PageImpl<>(departmentEntities, PageRequest.of(page, pageSize), departmentEntities.size());

        when(departmentEntityRepository.findAll(PageRequest.of(page, pageSize))).thenReturn(departmentPage);

        List<GetDepartmentDTO> getDepartmentDTOs = new ArrayList<>();
        GetDepartmentDTO dto1 = new GetDepartmentDTO();
        dto1.setId(1L);
        dto1.setName("Department 1 DTO");
        getDepartmentDTOs.add(dto1);

        GetDepartmentDTO dto2 = new GetDepartmentDTO();
        dto2.setId(2L);
        dto2.setName("Department 2 DTO");
        getDepartmentDTOs.add(dto2);

        when(getDepartmentMapper.toDtos(departmentPage)).thenReturn(getDepartmentDTOs);

        // Act
        List<GetDepartmentDTO> result = departmentService.getAllDepartments(page, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Department 1 DTO", result.get(0).getName());
        assertEquals("Department 2 DTO", result.get(1).getName());

        verify(departmentEntityRepository, times(1)).findAll(PageRequest.of(page, pageSize));
        verify(getDepartmentMapper, times(1)).toDtos(departmentPage);
    }

    @Test
    void getAllDepartments_ShouldReturnEmptyList_WhenNoDepartmentsExist() {
        // Arrange
        int page = 0;
        int pageSize = 10;

        Page<DepartmentEntity> emptyPage = Page.empty(PageRequest.of(page, pageSize));

        when(departmentEntityRepository.findAll(PageRequest.of(page, pageSize))).thenReturn(emptyPage);
        when(getDepartmentMapper.toDtos(emptyPage)).thenReturn(new ArrayList<>());

        // Act
        List<GetDepartmentDTO> result = departmentService.getAllDepartments(page, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());

        verify(departmentEntityRepository, times(1)).findAll(PageRequest.of(page, pageSize));
        verify(getDepartmentMapper, times(1)).toDtos(emptyPage);
    }
}
