package by.koronatech.office.api.controller.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetEmployeeDTO {
    public Long id;
    public String name;
    public BigDecimal salary;
    public String department;
    public Boolean manager;
}
