package by.koronatech.office.core.services;

import by.koronatech.office.api.controller.dto.GetDepartmentDTO;
import by.koronatech.office.core.utils.mapper.department.GetDepartmentMapper;
import by.koronatech.office.core.repositories.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
@AllArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentEntityRepository;
    private final GetDepartmentMapper getDepartmentMapper;

    public List<GetDepartmentDTO> getAllDepartments(int page, int pageSize) {
        return getDepartmentMapper.toDtos(departmentEntityRepository.findAll(PageRequest.of(page, pageSize)));
    }
}
