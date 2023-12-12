package vn.unigap.api.employer.dto.in;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerUpdateDtoIn {
    @NotEmpty
    @Size(max = 255, message = "Name length should not be greater than 255")
    private String name;

    @NotNull
    @Min(value = 1, message = "Province ID must be at least 1")
    private long province;

    private String description;
}
