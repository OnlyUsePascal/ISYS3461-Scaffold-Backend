package isys3461.lab_test2.alpha_service.alpha.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import isys3461.lab_test2.alpha_service.alpha.model.AlphaModel;

@Repository
public interface AlphaRepo extends JpaRepository<AlphaModel, UUID>,
    JpaSpecificationExecutor<AlphaModel> {
}