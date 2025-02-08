package by.koronatech.office.core.utils.mapper.employee;

import by.koronatech.office.api.controller.dto.AddEmployeeDTO;
import by.koronatech.office.core.entities.EmployeeEntity;
import by.koronatech.office.core.utils.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = BaseMapper.class)
public interface AddEmployeeMapper extends BaseMapper<EmployeeEntity, AddEmployeeDTO>  {

    AddEmployeeMapper INSTANCE = Mappers.getMapper( AddEmployeeMapper.class );

}
