package vn.unigap.api.employer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.unigap.api.employer.dto.in.EmployerDtoIn;
import vn.unigap.api.employer.dto.in.EmployerUpdateDtoIn;
import vn.unigap.api.employer.dto.in.PageDtoIn;
import vn.unigap.api.employer.dto.out.EmployerDtoOut;
import vn.unigap.api.employer.dto.out.PageDtoOut;
import vn.unigap.api.employer.service.EmployerService;
import vn.unigap.common.error.ErrorCode;
import vn.unigap.common.response.ApiResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/employers")
public class EmployerController {

    private final EmployerService employerService;

    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<String>> createEmployer(@RequestBody @Valid EmployerDtoIn employerDtoIn) {
        String message = "Employer has been created successfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.noDataResponse(ErrorCode.SUCCESS, HttpStatus.CREATED, message));
    }

    @PatchMapping()
    public ResponseEntity<ApiResponse<String>> updateEmployer(@PathVariable long id, @RequestBody @Valid EmployerUpdateDtoIn employerUpdateDtoIn) {
        String message = "Employer has been updated successfully";
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.noDataResponse(ErrorCode.SUCCESS, HttpStatus.OK, message));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployerDtoOut>> getEmployerById(@PathVariable long id) {
        String message = "Employer has been retrieved successfully";
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.withDataResponse(employerService.getEmployerById(id), ErrorCode.SUCCESS, HttpStatus.OK, message));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<PageDtoOut<EmployerDtoOut>>> getAllEmployers(@Valid PageDtoIn pageDtoIn) {
        String message = "Employers have been retrieved successfully";
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.withDataResponse(employerService.getAllEmployers(pageDtoIn), ErrorCode.SUCCESS, HttpStatus.OK, message));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployerById(@PathVariable long id) {
        String message = "Employer has been deleted successfully";
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.noDataResponse(ErrorCode.SUCCESS, HttpStatus.OK, message));
    }
}