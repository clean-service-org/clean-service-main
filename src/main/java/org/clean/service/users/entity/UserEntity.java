package org.clean.service.users.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.clean.service.infrastructure.entity.base.BaseEntity;
import org.clean.service.users.enums.Gender;
import org.clean.service.users.enums.Status;
import org.clean.service.users.enums.UserType;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseEntity {
  @Id
  @UuidGenerator
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", updatable = false, nullable = false)
  private String id;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_type")
  private UserType userType;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private Gender gender;

  @Column(name = "profile_picture")
  private String profilePicture;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Column(name = "date_of_birth", nullable = false)
  private String dateOfBirth;

  @Column(name = "identity_card")
  private String identityCard;

  @Column(name = "address")
  private String address;

  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private Status status = Status.ACTIVE;

  @Column(name = "number_of_violations")
  private Integer numberOfViolations;

  @Override
  protected void onPrePersist() {
    this.status = Status.ACTIVE;
    super.onPrePersist();
  }
}
