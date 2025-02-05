package by.koronatech.office.core.entities;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    public Long id;
    public String name;
    public BigDecimal salary;
    public String department;
    public Boolean manager;
}
