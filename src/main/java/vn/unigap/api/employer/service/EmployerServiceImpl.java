package vn.unigap.api.employer.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import vn.unigap.api.employer.dto.in.EmployerDtoIn;
import vn.unigap.api.employer.dto.in.EmployerUpdateDtoIn;
import vn.unigap.api.employer.dto.out.EmployerDtoOut;
import vn.unigap.api.entity.jpa.Employer;
import vn.unigap.api.entity.jpa.JobProvince;
import vn.unigap.api.repository.EmployerRepository;
import vn.unigap.api.repository.JobProvinceRepository;
import vn.unigap.common.error.ErrorCode;
import vn.unigap.common.exceptions.ApiException;
import vn.unigap.common.pagination.dto.in.PageDtoIn;
import vn.unigap.common.pagination.dto.out.PageDtoOut;

@Service
public class EmployerServiceImpl implements EmployerService {

        @Autowired
        private EmployerRepository employerRepository;

        @Autowired
        private JobProvinceRepository jobProvinceRepository;

        @Autowired
        private ModelMapper modelMapper;

        @Override
        public EmployerDtoOut createEmployer(EmployerDtoIn employerDtoIn) {
                // Check if email exists
                if (employerRepository.findByEmail(employerDtoIn.getEmail()).isPresent()) {
                        throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "Email already exists");
                }

                Date date = new Date();
                JobProvince province = jobProvinceRepository.findById(employerDtoIn.getProvince())
                                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND,
                                                "Province does not exist"));
                Employer employer = Employer.builder().name(employerDtoIn.getName()).email(employerDtoIn.getEmail())
                                .province(province).description(employerDtoIn.getDescription()).createdAt(date)
                                .updatedAt(date).build();

                employerRepository.save(employer);

                EmployerDtoOut employerDtoOut = modelMapper.map(employer, EmployerDtoOut.class);
                return employerDtoOut;
        }

        @Override
        public EmployerDtoOut updateEmployer(long id, EmployerUpdateDtoIn employerUpdateDtoIn) {
                // Check if employer exists
                Employer employer = employerRepository.findById(id)
                                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND,
                                                "Employer does not exist"));
                JobProvince province = jobProvinceRepository.findById(employerUpdateDtoIn.getProvince())
                                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND,
                                                "Province does not exist"));
                employer.setName(employerUpdateDtoIn.getName());
                employer.setProvince(province);
                employer.setDescription(employerUpdateDtoIn.getDescription());

                employerRepository.save(employer);

                EmployerDtoOut employerDtoOut = modelMapper.map(employer, EmployerDtoOut.class);
                return employerDtoOut;
        }

        @Override
        public EmployerDtoOut getEmployerById(long id) {
                // Check if employer exists
                Employer employer = employerRepository.findById(id)
                                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND,
                                                "Employer does not exist"));
                JobProvince province = jobProvinceRepository.findById(employer.getProvince().getId())
                                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND,
                                                "Province does not exist"));
                return EmployerDtoOut.builder().id(employer.getId()).name(employer.getName()).email(employer.getEmail())
                                .province(province)
                                // .provinceName(province.getName())
                                .description(employer.getDescription()).build();
        }

        @Override
        public PageDtoOut<EmployerDtoOut> getAllEmployers(PageDtoIn pageDtoIn) {
                Page<Employer> employers = this.employerRepository.findAll(PageRequest.of(pageDtoIn.getPage() - 1,
                                pageDtoIn.getSize(), Sort.by("name").ascending()));

                List<EmployerDtoOut> employerDtoOuts = employers.stream().map(EmployerDtoOut::from)
                                .collect(Collectors.toList());

                return PageDtoOut.from(pageDtoIn.getPage(), pageDtoIn.getSize(), employers.getTotalElements(),
                                employerDtoOuts);
        }

        @Override
        public String deleteEmployerById(long id) {
                Employer employer = employerRepository.findById(id)
                                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND,
                                                "Employer does not exist"));
                employerRepository.delete(employer);
                return "Employer has been deleted successfully";
        }
}
