package vn.unigap.api.employer.entity.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok annotation to generate all the getters, setters, equals, hash, and toString methods, based on the fields
@AllArgsConstructor // Lombok annotation to generate the constructor with all the fields as arguments
@NoArgsConstructor // Lombok annotation to generate the default constructor
@Entity // JPA annotation to make this object ready for storage in a JPA-based data store
@Table(name="job_province")
public class JobProvince {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name = "name")
    private String name;
}
