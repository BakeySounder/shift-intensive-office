package by.koronatech.office.core.repositories;

import by.koronatech.office.core.entities.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends
        PagingAndSortingRepository<EmployeeEntity, Long>,
        CrudRepository<EmployeeEntity, Long>  {
    public Page<EmployeeEntity> findByDepartment(String department, Pageable pageable);
    Optional<EmployeeEntity> findAllById(Long employeeId);
}
