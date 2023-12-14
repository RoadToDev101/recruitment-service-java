package vn.unigap.api.dto.out;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.unigap.api.entity.jpa.Employer;
import vn.unigap.api.entity.jpa.Job;
import vn.unigap.api.entity.jpa.JobField;
import vn.unigap.api.entity.jpa.JobProvince;
import vn.unigap.api.repository.JobFieldRepository;
import vn.unigap.api.repository.JobProvinceRepository;
import vn.unigap.common.error.ErrorCode;
import vn.unigap.common.exceptions.ApiException;

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
    private Employer employerId;
    private String employerName;

    public static JobDtoOut from(Job job, JobProvinceRepository jobProvinceRepository,
            JobFieldRepository jobFieldRepository) {
        String fieldsString = job.getFields(); // This should return something like "-64-32-10-"
        String[] fieldIds = fieldsString.split("-");

        List<JobField> jobFields = new ArrayList<>();
        for (String fieldId : fieldIds) {
            if (!fieldId.isEmpty()) { // Skip empty strings resulting from leading/trailing '-'
                Integer id = Integer.parseInt(fieldId);
                JobField jobField = jobFieldRepository.findById(id)
                        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND,
                                "JobField not found with id: " + id));
                jobFields.add(jobField);
            }
        }

        // Do the same for provinces
        String provincesString = job.getProvinces();
        String[] provinceIds = provincesString.split("-");

        List<JobProvince> jobProvinces = new ArrayList<>();
        for (String provinceId : provinceIds) {
            if (!provinceId.isEmpty()) {
                Integer id = Integer.parseInt(provinceId);
                JobProvince jobProvince = jobProvinceRepository.findById(id)
                        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND,
                                "JobProvince not found with id: " + id));
                jobProvinces.add(jobProvince);
            }
        }

        return JobDtoOut.builder().id(job.getId()).title(job.getTitle()).quantity(job.getQuantity())
                .description(job.getDescription()).fields(jobFields).provinces(jobProvinces).salary(job.getSalary())
                .expiredAt(job.getExpiredAt()).employerId(job.getEmployerId())
                .employerName(job.getEmployerId().getName()).build();
    }
}
