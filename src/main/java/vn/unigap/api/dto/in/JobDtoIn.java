package vn.unigap.api.dto.in;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDtoIn {
    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Employer is required")
    private long employerId;

    @Min(value = 1, message = "Quantity is required")
    private Integer quantity;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Fields is required")
    private List<Integer> fieldIds;

    @NotNull(message = "Provinces is required")
    private List<Integer> provinceIds;

    @NotNull(message = "Salary is required")
    private Integer salary;

    @NotNull(message = "Expired at is required")
    private Date expiredAt;
}
