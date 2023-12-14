package vn.unigap.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import org.modelmapper.ModelMapper;
import vn.unigap.api.repository.JobRepository;
import vn.unigap.api.dto.in.JobDtoIn;
import vn.unigap.api.dto.out.JobDtoOut;
import vn.unigap.api.entity.jpa.Job;
import vn.unigap.api.repository.EmployerRepository;
// import vn.unigap.api.repository.JobFieldRepository;
// import vn.unigap.api.repository.JobProvinceRepository;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmployerRepository employerRepository;

    // @Autowired
    // private JobProvinceRepository jobProvinceRepository;

    // @Autowired
    // private JobFieldRepository jobFieldRepository;

    @Override
    public JobDtoOut createJob(JobDtoIn jobDtoIn) {
        Date date = new Date();

        Job job = Job.builder().title(jobDtoIn.getTitle())
                .employerId(employerRepository.findById(jobDtoIn.getEmployerId()).get())
                .quantity(jobDtoIn.getQuantity()).description(jobDtoIn.getDescription()).salary(jobDtoIn.getSalary())
                .expiredAt(jobDtoIn.getExpiredAt()).createdAt(date).updatedAt(date).build();

        jobRepository.save(job);

        JobDtoOut jobDtoOut = modelMapper.map(job, JobDtoOut.class);
        return jobDtoOut;
    }
}
