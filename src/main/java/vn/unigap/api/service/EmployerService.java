package vn.unigap.api.service;

import vn.unigap.api.dto.in.EmployerDtoIn;
import vn.unigap.api.dto.in.EmployerUpdateDtoIn;
import vn.unigap.api.dto.out.EmployerDtoOut;
import vn.unigap.common.pagination.dto.in.PageDtoIn;
import vn.unigap.common.pagination.dto.out.PageDtoOut;

public interface EmployerService {
    EmployerDtoOut createEmployer(EmployerDtoIn employerDtoIn);

    EmployerDtoOut updateEmployer(long id, EmployerUpdateDtoIn employerUpdateDtoIn);

    EmployerDtoOut getEmployerById(long id);

    PageDtoOut<EmployerDtoOut> getAllEmployers(PageDtoIn pageDtoIn);

    String deleteEmployerById(long id);
}
