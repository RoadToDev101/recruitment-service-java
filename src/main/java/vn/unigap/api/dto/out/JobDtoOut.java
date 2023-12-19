package vn.unigap.api.dto.out;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.unigap.api.entity.jpa.Job;
import vn.unigap.api.entity.jpa.JobField;
import vn.unigap.api.entity.jpa.JobProvince;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDtoOut {
    private Long id;
    private String title;
    private int quantity;
    private String description;
    private List<JobField> fields;
    private List<JobProvince> provinces;
    private int salary;
    private Date expiredAt;
    private long employerId;
    private String employerName;

    public static JobDtoOut from(Job job, List<JobField> fieldIds, List<JobProvince> provinceIds) {

        return JobDtoOut.builder().id(job.getId()).title(job.getTitle()).quantity(job.getQuantity())
                .description(job.getDescription()).fields(fieldIds).provinces(provinceIds).salary(job.getSalary())
                .expiredAt(job.getExpiredAt()).employerId(job.getEmployerId().getId())
                .employerName(job.getEmployerId().getName()).build();
    }
}
