package vn.unigap.api.employer.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.unigap.api.employer.entity.jpa.Employer;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployerDtoOut {
    private Long id;
    private String email;
    private String name;
    private int province;
    private String description;

    public static EmployerDtoOut fromEntity(Employer employer) {
        return EmployerDtoOut.builder()
                .id(employer.getId())
                .email(employer.getEmail())
                .name(employer.getName())
                .province(employer.getProvince())
                .description(employer.getDescription())
                .build();
    }
}
