package vn.unigap.api.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.unigap.api.entity.jpa.Employer;
import vn.unigap.api.entity.jpa.JobProvince;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployerDtoOut {
    private Long id;
    private String email;
    private String name;
    private JobProvince province;
    // private String provinceName;
    private String description;

    public static EmployerDtoOut from(Employer employer) {
        return EmployerDtoOut.builder().id(employer.getId()).email(employer.getEmail()).name(employer.getName())
                .province(employer.getProvince())
                // .provinceName(employer.getProvince().getName())
                .description(employer.getDescription()).build();
    }
}
