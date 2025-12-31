package isys3461.lab_test2.alpha_service.alpha.model;

import java.time.LocalDateTime;
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
@Table(name = "alpha")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AlphaModel {
  // do not use @UUIDGenerator if u want to seed
  @Id
  @Column(nullable = false, name = "id")
  private UUID id;
  
  @Column(nullable = false, name = "name")
  private String name;

  @Column(nullable = false, name = "price")
  private Double price;

  @Column(nullable = false, name = "created_at")
  private LocalDateTime createdAt;
}
