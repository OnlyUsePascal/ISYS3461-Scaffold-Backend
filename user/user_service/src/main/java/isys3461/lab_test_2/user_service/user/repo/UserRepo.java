package isys3461.lab_test_2.user_service.user.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import isys3461.lab_test_2.user_service.user.model.UserModel;

@Repository
public interface UserRepo extends JpaRepository<UserModel, UUID>,
        JpaSpecificationExecutor<UserModel> {
}