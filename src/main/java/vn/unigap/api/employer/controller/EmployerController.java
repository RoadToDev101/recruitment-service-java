package vn.unigap.api.employer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.unigap.api.employer.dto.in.EmployerDtoIn;
import vn.unigap.api.employer.service.EmployerService;
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
        String message = employerService.createEmployer(employerDtoIn);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(message));
    }

    // @PatchMapping()
    // public ResponseEntity<EmployerDto> updateEmployer(@PathVariable long id, @RequestBody @Valid EmployerDto employerDto) {
    //     Employer employer = modelMapper.map(employerDto, Employer.class); // convert dto to entity
    //     Employer updatedEmployer = employerService.updateEmployer(id, employer); // update employer
    //     EmployerDto updatedEmployerDto = modelMapper.map(updatedEmployer, EmployerDto.class); // convert entity to dto
    //     return ResponseEntity.ok(updatedEmployerDto);
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<EmployerDto> getEmployerById(@PathVariable long id) {
    //     Employer employer = employerService.getEmployerById(id);
    //     EmployerDto employerDto = modelMapper.map(employer, EmployerDto.class); // convert entity to dto
    //     return ResponseEntity.ok(employerDto);
    // }

    // @GetMapping()
    // public List<EmployerDto> getAllEmployers() {
    //     List<Employer> employers = employerService.getAllEmployers();
    //     return employers.stream().map(employer -> modelMapper.map(employer, EmployerDto.class)).toList(); // convert list entity to list dto
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<String> deleteEmployerById(@PathVariable long id) {
    //     String message = employerService.deleteEmployerById(id);
    //     return ResponseEntity.status(HttpStatus.OK).body(message);
    // }
}