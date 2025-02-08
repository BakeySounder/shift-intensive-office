package by.koronatech.office.core.utils.mapper.department;

import by.koronatech.office.api.controller.dto.GetDepartmentDTO;
import by.koronatech.office.core.entities.DepartmentEntity;
import by.koronatech.office.core.utils.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = BaseMapper.class)
public interface GetDepartmentMapper extends BaseMapper<DepartmentEntity, GetDepartmentDTO> {
    GetDepartmentMapper INSTANCE = Mappers.getMapper(GetDepartmentMapper.class);

}
