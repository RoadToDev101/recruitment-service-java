package vn.unigap.api.employer.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // Generate constructor with all arguments
public class EmployerDtoIn {
    private long id;

    @NotBlank(message = "Email is required")
    @Size(max = 255, message = "Email must be less than 255 characters")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @Min(value = 1, message = "Province is required")
    private int province;

    private String description;
}
