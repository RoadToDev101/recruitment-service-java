package vn.unigap.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.unigap.api.entity.Employer;
import vn.unigap.api.repository.EmployerRepository;

@Service
public class EmployerService {
    @Autowired
    private EmployerRepository employerRepository;

    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }

}
