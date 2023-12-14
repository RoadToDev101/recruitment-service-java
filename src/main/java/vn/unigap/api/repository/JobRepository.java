package vn.unigap.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.jpa.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

}
