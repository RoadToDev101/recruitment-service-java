package vn.unigap.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.unigap.api.entity.Employer;
import vn.unigap.api.service.EmployerService;

@RestController
public class EmployerController {
    
    @Autowired
    private EmployerService employerService;

    @GetMapping("/employers")
    public List<Employer> getAllEmployers() {
        return employerService.getAllEmployers();
    }
}
