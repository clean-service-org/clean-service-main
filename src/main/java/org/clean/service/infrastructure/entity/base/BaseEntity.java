package org.clean.service.infrastructure.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;
import org.hibernate.type.TrueFalseConverter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@MappedSuperclass
@SoftDelete(strategy = SoftDeleteType.ACTIVE, converter = TrueFalseConverter.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseEntity {
  @Column(name = "created_at")
  protected LocalDateTime createdAt;

  @Column(name = "created_by")
  protected String createdBy;

  @Column(name = "updated_at")
  protected LocalDateTime updatedAt;

  @Column(name = "updated_by")
  protected String updatedBy;

  @PrePersist
  protected void onPrePersist() {
    LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
    this.createdAt = now;
    this.updatedAt = now;
  }

  @PreUpdate
  protected void onPreUpdate() {
    this.updatedAt = LocalDateTime.now(ZoneOffset.UTC);
  }
}
