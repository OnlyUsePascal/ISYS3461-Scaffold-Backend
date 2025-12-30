package isys3461.lab_test_2.user_service.auth.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import isys3461.lab_test_2.user_service.auth.model.AuthModel;

@Repository
public interface AuthRepo extends JpaRepository<AuthModel, UUID>,
        JpaSpecificationExecutor<AuthModel> {

    Optional<AuthModel> findByUsername(String username);
}