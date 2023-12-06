package vn.unigap.api.employer.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import vn.unigap.api.employer.dto.in.EmployerDtoIn;
import vn.unigap.api.employer.entity.jpa.Employer;
import vn.unigap.api.employer.repository.EmployerRepository;
import vn.unigap.common.error.ErrorCode;
import vn.unigap.common.exceptions.ApiException;


@Service
public class EmployerServiceImpl implements EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    // public EmployerServiceImpl(EmployerRepository employerRepository) {
    //     super();
    //     this.employerRepository = employerRepository;
    // }

    @Override
    public String createEmployer(EmployerDtoIn employerDtoIn) {
        // Check if email exists
        if (employerRepository.findByEmail(employerDtoIn.getEmail()).isPresent()) {
            throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Email already exists");
        }

        Date date = new Date();
        Employer employer = Employer.builder()
            .name(employerDtoIn.getName())
            .email(employerDtoIn.getEmail())
            .province(employerDtoIn.getProvince())
            .description(employerDtoIn.getDescription())
            .createdAt(date)
            .updatedAt(date)
            .build();

        employerRepository.save(employer);
        return "Employer has been created successfully";
    }

    // @Override
    // public Employer updateEmployer(long id, Employer employer) {
    //     Employer existingEmployer = employerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employer not found"));

    //     existingEmployer.setName(employer.getName());
    //     existingEmployer.setProvince(employer.getProvince());
    //     existingEmployer.setDescription(employer.getDescription());
    //     existingEmployer.setUpdatedAt(LocalDateTime.now());
    //     return employerRepository.save(existingEmployer);
    // }

    // @Override
    // public Employer getEmployerById(long id) {
    //     return employerRepository.findById(id).orElse(null);
    // }

    // @Override
    // public List<Employer> getAllEmployers() {
    //     return employerRepository.findAll();
    // }

    // @Override
    // public String deleteEmployerById(long id) {
    //     employerRepository.deleteById(id);
    //     return "Employer has been deleted successfully";
    // }
}
