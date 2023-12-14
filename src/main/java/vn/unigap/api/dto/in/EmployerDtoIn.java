package vn.unigap.api.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // Generate constructor with all arguments
@NoArgsConstructor
@Builder
public class EmployerDtoIn {
    // private long id;

    @NotBlank(message = "Email is required")
    @Size(max = 255, message = "Email must be less than 255 characters")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @Min(value = 1, message = "Province is required")
    private Integer provinceId;

    private String description;
}
