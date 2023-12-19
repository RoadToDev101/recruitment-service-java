package vn.unigap.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.unigap.api.dto.in.JobDtoIn;

import vn.unigap.api.dto.out.JobDtoOut;
import vn.unigap.api.service.JobService;
import vn.unigap.common.error.ErrorCode;
import vn.unigap.common.response.ApiResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<JobDtoOut>> createJob(@RequestBody @Valid JobDtoIn jobDtoIn) {
        String message = "Job has been created successfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse
                .withDataResponse(jobService.createJob(jobDtoIn), ErrorCode.SUCCESS, HttpStatus.CREATED, message));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobDtoOut>> getJobById(@PathVariable long id) {
        String message = "Job has been retrieved successfully";
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.withDataResponse(jobService.getJobById(id), ErrorCode.SUCCESS, HttpStatus.OK, message));
    }
}
