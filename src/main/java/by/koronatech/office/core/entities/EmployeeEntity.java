package by.koronatech.office.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees",schema = "office")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;


    @Column(name = "name", nullable = false, length = 200)
    public String name;

    @Column(name = "salary", nullable = false)
    public BigDecimal salary;

    @Column(name = "department", nullable = false, length = 200)
    public String department;

    @Column(name = "manager", nullable = false)
    public Boolean manager;
}
