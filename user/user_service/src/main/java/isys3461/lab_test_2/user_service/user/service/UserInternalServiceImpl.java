package isys3461.lab_test_2.user_service.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import isys3461.lab_test_2.user_api.external.service.EventProducer;
import isys3461.lab_test_2.user_api.internal.dto.CreateUserDto.CreateUserReq;
import isys3461.lab_test_2.user_api.internal.dto.CreateUserDto.CreateUserRes;
import isys3461.lab_test_2.user_api.internal.dto.GetUserDto.GetUserRes;
import isys3461.lab_test_2.user_api.internal.dto.ListUsersDto.ListUsersRes;
import isys3461.lab_test_2.user_api.internal.service.UserInternalService;
import isys3461.lab_test_2.user_service.user.model.UserModel;
import isys3461.lab_test_2.user_service.user.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@SuppressWarnings("null")
public class UserInternalServiceImpl implements UserInternalService {
  @Autowired
  private UserRepo userRepo;

  @Autowired
  private EventProducer eventProducer;

  @Override
  public GetUserRes getUser(UUID id) {
    var user = userRepo.findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found with id " + id.toString()));

    return new GetUserRes(id, user.getName(), user.getAddress());
  }

  @Override
  public List<ListUsersRes> listUsers() {
    var users = userRepo.findAll();
    return users.stream().map(u -> {
      return new ListUsersRes(u.getId(), u.getName(), u.getAddress());
    }).toList();
  }

  @Override
  public CreateUserRes createUser(CreateUserReq req) {
    var newUser = userRepo.save(new UserModel(req.id(), req.name(), req.address()));
    return new CreateUserRes(
        newUser.getId(),
        newUser.getName(),
        newUser.getAddress());
  }
}
