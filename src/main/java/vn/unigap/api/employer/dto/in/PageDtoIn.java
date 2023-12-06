package vn.unigap.api.employer.dto.in;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageDtoIn {
    @NotNull
    @Min(value = 1)
    private Integer page = 1;

    @NotNull
    @Min(value = 1)
    @Max(value = 500)
    private Integer size = 10;
}
