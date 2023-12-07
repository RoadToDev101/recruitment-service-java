package vn.unigap.api.employer.service;

import vn.unigap.api.employer.dto.in.EmployerDtoIn;
import vn.unigap.api.employer.dto.in.EmployerUpdateDtoIn;
import vn.unigap.api.employer.dto.in.PageDtoIn;
import vn.unigap.api.employer.dto.out.EmployerDtoOut;
import vn.unigap.api.employer.dto.out.PageDtoOut;

public interface EmployerService {
    String createEmployer(EmployerDtoIn  employerDtoIn);

    String updateEmployer(long id, EmployerUpdateDtoIn employerUpdateDtoIn);

    EmployerDtoOut getEmployerById(long id);

    PageDtoOut<EmployerDtoOut> getAllEmployers(PageDtoIn pageDtoIn);

    String deleteEmployerById(long id);
}
