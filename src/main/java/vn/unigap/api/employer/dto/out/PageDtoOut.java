package vn.unigap.api.employer.dto.out;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a generic page response DTO (Data Transfer Object) containing paginated data.
 *
 * @param <T> the type of data contained in the page
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDtoOut<T> {
    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer pageSize = 10;

    @Builder.Default
    private Long totalElements = 0L;

    @Builder.Default
    private Long totalPages = 0L;

    @Builder.Default
    private List<T> data = new ArrayList<>();

    /**
     * Creates a new instance of PageDtoOut with the specified page, pageSize, totalElements, and data.
     *
     * @param page          the current page number
     * @param pageSize      the number of elements per page
     * @param totalElements the total number of elements
     * @param data          the list of data elements
     * @param <T>           the type of data contained in the page
     * @return a new instance of PageDtoOut
     */
    public static <T> PageDtoOut<T> from(Integer page, Integer pageSize, Long totalElements, List<T> data) {
        Long totalPages = totalElements / pageSize;
        if (totalElements % pageSize != 0) {
            totalPages++;
        }

        return PageDtoOut.<T>builder()
                .page(page)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .data(data).build();
    }
}
