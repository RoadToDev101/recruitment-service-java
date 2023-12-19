package vn.unigap.api.service;

import vn.unigap.api.dto.in.JobDtoIn;
import vn.unigap.api.dto.out.JobDtoOut;
import vn.unigap.common.pagination.dto.in.PageDtoIn;
import vn.unigap.common.pagination.dto.out.PageDtoOut;

public interface JobService {
    JobDtoOut createJob(JobDtoIn jobDtoIn);

    // JobDtoOut updateJob(long id, JobUpdateDtoIn jobUpdateDtoIn);

    JobDtoOut getJobById(long id);

    // PageDtoOut<JobDtoOut> getAllJobs(PageDtoIn pageDtoIn);

    // String deleteJobById(long id);
}
