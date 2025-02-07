package by.koronatech.office.core.services;

import by.koronatech.office.api.controller.dto.GetDepartmentDTO;
import by.koronatech.office.core.entities.Department;
import by.koronatech.office.core.mapper.department.GetDepartmentMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//@RequiredArgsConstructor
@AllArgsConstructor
public class DepartmentService {
    private final List<Department> departmentRepository = new ArrayList<>(
            List.of(
                    Department.builder().id(100500L).name("Бухгалтерия").build()
            )
    );
    private final GetDepartmentMapper getDepartmentMapper;

    public List<GetDepartmentDTO> getAllDepartments(int from, int count){
        return getDepartmentMapper.toDtos(departmentRepository.stream().toList());
    }
}
