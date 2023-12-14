package vn.unigap.api.controller;

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
import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.in.EmployerUpdateDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.api.service.EmployerService;
import vn.unigap.common.error.ErrorCode;
import vn.unigap.common.pagination.dto.in.PageDtoIn;
import vn.unigap.common.pagination.dto.out.PageDtoOut;
import vn.unigap.common.response.ApiResponse;

/**
 * This class represents the controller for managing employers in the
 * recruitment service.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/employers")
public class EmployerController {

    private final EmployerService employerService;

    /**
     * Constructs a new EmployerController with the specified EmployerService.
     *
     * @param employerService the EmployerService to be used
     */
    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    /**
     * Creates a new employer.
     *
     * @param employerDtoIn the input data for creating the employer
     * @return the response entity containing the created employer and a success
     *         message
     */
    @PostMapping()
    public ResponseEntity<ApiResponse<EmployerDtoOut>> createEmployer(@RequestBody @Valid EmployerDtoIn employerDtoIn) {
        String message = "Employer has been created successfully";
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.withDataResponse(
                employerService.createEmployer(employerDtoIn), ErrorCode.SUCCESS, HttpStatus.CREATED, message));
    }

    /**
     * Updates an existing employer.
     *
     * @param id                  the ID of the employer to be updated
     * @param employerUpdateDtoIn the input data for updating the employer
     * @return the response entity containing the updated employer and a success
     *         message
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployerDtoOut>> updateEmployer(@PathVariable long id,
            @RequestBody @Valid EmployerUpdateDtoIn employerUpdateDtoIn) {
        String message = "Employer has been updated successfully";
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.withDataResponse(
                employerService.updateEmployer(id, employerUpdateDtoIn), ErrorCode.SUCCESS, HttpStatus.OK, message));
    }

    /**
     * Retrieves an employer by ID.
     *
     * @param id the ID of the employer to be retrieved
     * @return the response entity containing the retrieved employer and a success
     *         message
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployerDtoOut>> getEmployerById(@PathVariable long id) {
        String message = "Employer has been retrieved successfully";
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse
                .withDataResponse(employerService.getEmployerById(id), ErrorCode.SUCCESS, HttpStatus.OK, message));
    }

    /**
     * Retrieves all employers.
     *
     * @param pageDtoIn the input data for pagination
     * @return the response entity containing the retrieved employers and a success
     *         message
     */
    @GetMapping()
    public ResponseEntity<ApiResponse<PageDtoOut<EmployerDtoOut>>> getAllEmployers(@Valid PageDtoIn pageDtoIn) {
        String message = "Employers have been retrieved successfully";
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.withDataResponse(
                employerService.getAllEmployers(pageDtoIn), ErrorCode.SUCCESS, HttpStatus.OK, message));
    }

    /**
     * Deletes an employer by ID.
     *
     * @param id the ID of the employer to be deleted
     * @return the response entity with a success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployerById(@PathVariable long id) {
        String message = "Employer has been deleted successfully";
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.noDataResponse(ErrorCode.SUCCESS, HttpStatus.OK, message));
    }
}