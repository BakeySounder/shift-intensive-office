package by.koronatech.office.repositories_test;

import by.koronatech.office.core.entities.EmployeeEntity;
import by.koronatech.office.core.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@Transactional
public class EmployeeRepositoryTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0"); // Use MySQL 8.0

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void whenFindByDepartment_thenReturnEmployeesInDepartment() {
        // Given
        EmployeeEntity employee1 = new EmployeeEntity();
        employee1.setName("Employee 1");
        employee1.setDepartment("Test Department");
        employeeRepository.save(employee1);

        EmployeeEntity employee2 = new EmployeeEntity();
        employee2.setName("Employee 2");
        employee2.setDepartment("Test Department");
        employeeRepository.save(employee2);

        EmployeeEntity employee3 = new EmployeeEntity();
        employee3.setName("Employee 3");
        employee3.setDepartment("Another Department");
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
        EmployeeEntity employee = new EmployeeEntity();
        employee.setName("Test Employee");
        employeeRepository.save(employee);

        // When
        Optional<EmployeeEntity> foundEmployee = employeeRepository.findAllById(employee.getId());

        // Then
        assertThat(foundEmployee).isPresent();
        assertThat(foundEmployee.get().getName()).isEqualTo("Test Employee");
    }

    @Test
    void whenFindAllById_thenReturnEmptyOptional_whenEmployeeNotFound() {
        // Given
        Long nonExistentId = 999L;

        // When
        Optional<EmployeeEntity> foundEmployee = employeeRepository.findAllById(nonExistentId);

        // Then
        assertThat(foundEmployee).isEmpty();
    }
}
