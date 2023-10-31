package vn.unigap.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.unigap.api.entity.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
}
