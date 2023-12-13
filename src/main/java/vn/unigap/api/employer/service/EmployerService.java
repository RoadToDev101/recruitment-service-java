package vn.unigap.api.employer.service;

import vn.unigap.api.employer.dto.in.EmployerDtoIn;
import vn.unigap.api.employer.dto.in.EmployerUpdateDtoIn;
import vn.unigap.api.employer.dto.out.EmployerDtoOut;
import vn.unigap.common.pagination.dto.in.PageDtoIn;
import vn.unigap.common.pagination.dto.out.PageDtoOut;

public interface EmployerService {
    EmployerDtoOut createEmployer(EmployerDtoIn employerDtoIn);

    EmployerDtoOut updateEmployer(long id, EmployerUpdateDtoIn employerUpdateDtoIn);

    EmployerDtoOut getEmployerById(long id);

    PageDtoOut<EmployerDtoOut> getAllEmployers(PageDtoIn pageDtoIn);

    String deleteEmployerById(long id);
}
