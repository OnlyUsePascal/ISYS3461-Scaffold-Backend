package isys3461.lab_test_2.beta_service.beta.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import isys3461.lab_test_2.beta_service.beta.model.BetaModel;

@Repository
public interface BetaRepo extends JpaRepository<BetaModel, UUID>,
    JpaSpecificationExecutor<BetaModel> {
}