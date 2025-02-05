package by.koronatech.office.api.controller.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDepartmentDTO {
    public Long id;
    public String name;
}
