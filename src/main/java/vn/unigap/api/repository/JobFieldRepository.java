package vn.unigap.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.unigap.api.entity.jpa.JobField;

public interface JobFieldRepository extends JpaRepository<JobField, Integer> {

}
