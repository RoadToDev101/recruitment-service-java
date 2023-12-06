package vn.unigap.api.employer.entity.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.DynamicUpdate;

@Data // Lombok annotation to generate all the getters, setters, equals, hash, and toString methods, based on the fields
@AllArgsConstructor // Lombok annotation to generate the constructor with all the fields as arguments
@NoArgsConstructor // Lombok annotation to generate the default constructor
@Entity // JPA annotation to make this object ready for storage in a JPA-based data store
@DynamicUpdate // Hibernate annotation to update only the changed fields
@Builder // Lombok annotation to generate the builder pattern for the Pojo class
@Table(name="employer")
public class Employer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="email", unique = true)
    private String email;

    @Column(name="name")
    private String name;

    @Column(name="province")
    private int province;

    @Column(name="description")
    private String description;

    @Builder.Default
    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Builder.Default
    @Column(name = "updated_at")
    private Date updatedAt = new Date();
}