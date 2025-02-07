package by.koronatech.office.core.mapper.department;

import by.koronatech.office.api.controller.dto.GetDepartmentDTO;
import by.koronatech.office.core.entities.Department;
import by.koronatech.office.core.mapper.BaseMapper;
import by.koronatech.office.core.mapper.employee.AddEmployeeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = BaseMapper.class)
public interface GetDepartmentMapper extends BaseMapper<Department, GetDepartmentDTO> {
    GetDepartmentMapper INSTANCE = Mappers.getMapper( GetDepartmentMapper.class );

}
