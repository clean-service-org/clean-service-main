package org.clean.service.users.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.clean.service.users.enums.Status;
import org.clean.service.users.enums.UserType;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "helpers")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelperEntity extends UserEntity {
  @Column(name = "experience_description")
  private String experienceDescription;

  @Column(name = "resume_uploaded")
  private String resumeUploaded;

  @Column(name = "services_offered", columnDefinition = "varchar[]")
  private List<String> servicesOffered;

  @Column(name = "hourly_rate", precision = 10, scale = 2, nullable = false)
  private BigDecimal hourlyRate;

  @Column(name = "average_rating", precision = 2, scale = 1)
  private BigDecimal averageRating;

  @Column(name = "completed_jobs")
  private Integer completedJobs;

  @Column(name = "cancelled_jobs")
  private Integer cancelledJobs;

  @Override
  protected void onPrePersist() {
    this.setUserType(UserType.HELPER);
    this.setAverageRating(BigDecimal.valueOf(0));
    this.setCompletedJobs(0);
    this.setCancelledJobs(0);
    super.onPrePersist();
  }
}
