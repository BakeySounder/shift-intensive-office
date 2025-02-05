package by.koronatech.office.core.entities;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employer {
    public Long id;
    public String name;
    public double salary;
    public String department;
    public Boolean manager;
}
