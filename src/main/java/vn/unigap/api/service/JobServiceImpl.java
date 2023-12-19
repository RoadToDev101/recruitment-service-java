package vn.unigap.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import vn.unigap.api.repository.JobRepository;
import vn.unigap.api.dto.in.JobDtoIn;
import vn.unigap.api.dto.out.JobDtoOut;
import vn.unigap.api.entity.jpa.Job;
import vn.unigap.api.entity.jpa.JobField;
import vn.unigap.api.entity.jpa.JobProvince;
import vn.unigap.api.repository.EmployerRepository;
import vn.unigap.api.repository.JobFieldRepository;
import vn.unigap.api.repository.JobProvinceRepository;
import vn.unigap.common.utils.StringUtils;

@Service
public class JobServiceImpl implements JobService {

        @Autowired
        private JobRepository jobRepository;

        @Autowired
        private EmployerRepository employerRepository;

        @Autowired
        private JobProvinceRepository jobProvinceRepository;

        @Autowired
        private JobFieldRepository jobFieldRepository;

        @Override
        public JobDtoOut createJob(JobDtoIn jobDtoIn) {
                Date date = new Date();

                // Convert the fieldIds and provinceIds from list to string. Example: [1,2,3] to
                // "-1-2-3-"
                String fieldIds = StringUtils.convertIdsToString(jobDtoIn.getFieldIds());
                String provinceIds = StringUtils.convertIdsToString(jobDtoIn.getProvinceIds());

                Job job = Job.builder().title(jobDtoIn.getTitle())
                                .employerId(employerRepository.findById(jobDtoIn.getEmployerId()).get())
                                .quantity(jobDtoIn.getQuantity()).description(jobDtoIn.getDescription())
                                .salary(jobDtoIn.getSalary()).fields(fieldIds).provinces(provinceIds)
                                .expiredAt(jobDtoIn.getExpiredAt()).createdAt(date).updatedAt(date).build();

                jobRepository.save(job);

                List<JobField> fieldList = StringUtils.mapIdsToEntities(fieldIds,
                                fieldId -> jobFieldRepository.findById(fieldId).get());
                List<JobProvince> provinceList = StringUtils.mapIdsToEntities(provinceIds,
                                provinceId -> jobProvinceRepository.findById(provinceId).get());

                JobDtoOut jobDtoOut = JobDtoOut.from(job, fieldList, provinceList);
                return jobDtoOut;
        }

        @Override
        public JobDtoOut getJobById(long id) {
                Job job = jobRepository.findById(id).get();

                List<JobField> fieldList = StringUtils.mapIdsToEntities(job.getFields(),
                                fieldId -> jobFieldRepository.findById(fieldId).get());
                List<JobProvince> provinceList = StringUtils.mapIdsToEntities(job.getProvinces(),
                                provinceId -> jobProvinceRepository.findById(provinceId).get());

                return JobDtoOut.from(job, fieldList, provinceList);
        }
}
