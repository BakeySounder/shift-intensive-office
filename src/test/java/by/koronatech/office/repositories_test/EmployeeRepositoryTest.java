package by.koronatech.office.repositories_test;

import by.koronatech.office.core.entities.EmployeeEntity;
import by.koronatech.office.core.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
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
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void whenFindByDepartment_thenReturnEmployeesInDepartment() {
        // Given
        employeeRepository.deleteAll();
        EmployeeEntity employee1 = new EmployeeEntity();
        employee1.setName("Employee 1");
        employee1.setDepartment("Test Department");
        employee1.setSalary(BigDecimal.valueOf(1.1));
        employee1.setManager(false);
        employeeRepository.save(employee1);

        EmployeeEntity employee2 = new EmployeeEntity();
        employee2.setName("Employee 2");
        employee2.setDepartment("Test Department");
        employee2.setSalary(BigDecimal.valueOf(1.1));
        employee2.setManager(false);
        employeeRepository.save(employee2);

        EmployeeEntity employee3 = new EmployeeEntity();
        employee3.setName("Employee 3");
        employee3.setDepartment("Another Department");
        employee3.setSalary(BigDecimal.valueOf(1.1));
        employee3.setManager(false);
        employeeRepository.save(employee3);

        // When
        Page<EmployeeEntity> employees = employeeRepository.findByDepartment("Test Department", PageRequest.of(0, 10));

        // Then
        assertThat(employees.getTotalElements()).isEqualTo(2);
        assertThat(employees.getContent()).extracting(EmployeeEntity::getName).contains("Employee 1", "Employee 2");
    }

    @Test
    void whenFindAllById_thenReturnEmployee() {
        // Given
        employeeRepository.deleteAll();
        EmployeeEntity employee = new EmployeeEntity();
        employee.setName("Test Employee");
        employee.setDepartment("Test Department");
        employee.setSalary(BigDecimal.valueOf(1.1));
        employee.setManager(false);
        employeeRepository.save(employee);

        // When
        Optional<EmployeeEntity> foundEmployee = employeeRepository.findAllById(employee.getId());

        // Then
//        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getName()).isEqualTo("Test Employee");
    }

    @Test
    void whenFindAllById_thenReturnEmptyOptional_whenEmployeeNotFound() {
        // Given
        employeeRepository.deleteAll();
        Long nonExistentId = 999L;

        // When
        Optional<EmployeeEntity> foundEmployee = employeeRepository.findAllById(nonExistentId);

        // Then
        assertThat(foundEmployee).isEmpty();
    }
}
