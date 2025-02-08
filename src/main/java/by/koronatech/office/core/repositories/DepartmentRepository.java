package by.koronatech.office.core.repositories;

import by.koronatech.office.core.entities.DepartmentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends
        CrudRepository<DepartmentEntity, Long>,
        ListPagingAndSortingRepository<DepartmentEntity, Long> {
}
