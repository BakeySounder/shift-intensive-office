package by.koronatech.office.repositories_test;

import by.koronatech.office.core.entities.DepartmentEntity;
import by.koronatech.office.core.repositories.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void whenFindById_thenReturnDepartment() {
        // Given
        departmentRepository.deleteAll();
        DepartmentEntity department = new DepartmentEntity();
        department.setName("Test Department");
        departmentRepository.save(department);

        // When
        Optional<DepartmentEntity> found = departmentRepository.findById(department.getId());

        // Then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getName()).isEqualTo("Test Department");
    }

    @Test
    void whenCreateDepartment_thenDepartmentIsSaved() {
        // Given
        departmentRepository.deleteAll();
        DepartmentEntity department = new DepartmentEntity();
        department.setName("New Department");

        // When
        DepartmentEntity savedDepartment = departmentRepository.save(department);

        // Then
        assertThat(savedDepartment.getId()).isNotNull();
        assertThat(savedDepartment.getName()).isEqualTo("New Department");
    }

    @Test
    void whenFindAll_thenReturnAllDepartments() {
        // Given
        departmentRepository.deleteAll();
        DepartmentEntity department1 = new DepartmentEntity();
        department1.setName("Department 1");
        departmentRepository.save(department1);

        DepartmentEntity department2 = new DepartmentEntity();
        department2.setName("Department 2");
        departmentRepository.save(department2);

        // When
        Iterable<DepartmentEntity> departments = departmentRepository.findAll();

        // Then
        assertThat(departments).hasSize(2);
        assertThat(departments).extracting(DepartmentEntity::getName).contains("Department 1", "Department 2");
    }

    @Test
    void whenFindAllWithPagination_thenReturnPageOfDepartments() {
        // Given
        departmentRepository.deleteAll();
        DepartmentEntity department1 = new DepartmentEntity();
        department1.setName("Department 1");
        departmentRepository.save(department1);

        DepartmentEntity department2 = new DepartmentEntity();
        department2.setName("Department 2");
        departmentRepository.save(department2);

        DepartmentEntity department3 = new DepartmentEntity();
        department3.setName("Department 3");
        departmentRepository.save(department3);

        // When
        Page<DepartmentEntity> departmentPage = departmentRepository.findAll(PageRequest.of(0, 2));

        // Then
        assertThat(departmentPage.getTotalElements()).isEqualTo(3);
        assertThat(departmentPage.getSize()).isEqualTo(2);
        assertThat(departmentPage.getContent()).hasSize(2);
        assertThat(departmentPage.getContent()).extracting(DepartmentEntity::getName)
                .contains("Department 1", "Department 2");
    }
}
