package vn.unigap;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
// Scan other packages outside of unigap package if needed
// scanBasePackages = {"vn.util"}
)
public class RecruitmentServiceApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(RecruitmentServiceApplication.class, args);
	}

}
