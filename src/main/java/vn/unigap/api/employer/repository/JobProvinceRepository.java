package vn.unigap.api.employer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.unigap.api.employer.entity.jpa.JobProvince;

public interface JobProvinceRepository extends JpaRepository<JobProvince, Long> {
}
