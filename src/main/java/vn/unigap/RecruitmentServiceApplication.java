package vn.unigap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(
	// Scan other packages outside of unigap package if needed
	// scanBasePackages = {"vn.util"}
)
public class RecruitmentServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(RecruitmentServiceApplication.class, args);
	}

}
