package vn.unigap.api.entity.jpa;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@Builder
@Table(name = "jobs")
public class Job implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "employer_id", referencedColumnName = "id")
    private Employer employerId;

    @Column(name = "title")
    private String title;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "description")
    private String description;

    @Column(name = "salary")
    private int salary;

    // List of relevant job_field, example value: "-64-32-10-"
    @Column(name = "fields")
    private String fields;

    // List of relevant job_province, example value: "-1-"
    @Column(name = "provinces")
    private String provinces;

    @Builder.Default
    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Builder.Default
    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @Column(name = "expired_at")
    private Date expiredAt;
}
