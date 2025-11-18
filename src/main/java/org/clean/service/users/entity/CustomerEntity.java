package org.clean.service.users.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.clean.service.users.enums.UserType;

@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerEntity extends UserEntity {
  private String customerNumber;

  @Override
  protected void onPrePersist() {
    this.setUserType(UserType.CUSTOMER);
    super.onPrePersist();
  }
}
