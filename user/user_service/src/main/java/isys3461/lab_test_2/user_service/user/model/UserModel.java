package isys3461.lab_test_2.user_service.user.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserModel {
  // do not use @UUIDGenerator if u want to seed
  @Id
  @Column(nullable = false, name = "id")
  private UUID id;

  @Column(nullable = false, name = "name")
  private String name;

  @Column(nullable = false, name = "address")
  private String address;
}
