package isys3461.lab_test_2.user_service.auth.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import isys3461.lab_test_2.user_api.external.service.EventProducer;
import isys3461.lab_test_2.user_api.internal.dto.CreateUserDto.CreateUserReq;
import isys3461.lab_test_2.user_api.internal.dto.SignInDto.SignInReq;
import isys3461.lab_test_2.user_api.internal.dto.SignInDto.SignInRes;
import isys3461.lab_test_2.user_api.internal.dto.SignUpDto.SignUpReq;
import isys3461.lab_test_2.user_api.internal.dto.SignUpDto.SignUpRes;
import isys3461.lab_test_2.user_api.internal.service.AuthInternalService;
import isys3461.lab_test_2.user_api.internal.service.UserInternalService;
import isys3461.lab_test_2.user_service.auth.model.AuthModel;
import isys3461.lab_test_2.user_service.auth.repo.AuthRepo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@SuppressWarnings("null")
public class AuthInternalServiceImpl implements AuthInternalService {
  @Autowired
  private UserInternalService userInternalService;

  @Autowired
  private AuthRepo authRepo;

  @Autowired
  private EventProducer eventProducer;

  @Override
  public SignInRes signIn(SignInReq req) {
    var user = authRepo
        .findByUsername(req.username())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "user not found with username" + req.username()));

    if (!user.getPassword().equals(req.password())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "wrong password");
    }

    return new SignInRes(
        "access token",
        "refresh token");
  }

  @Override
  public SignUpRes signUp(SignUpReq req) {
    var user = authRepo
        .findByUsername(req.getUsername());
    if (user.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "user already existed with username + " + req.getUsername());
    }

    var newUser = authRepo.save(new AuthModel(
        UUID.randomUUID(),
        req.getUsername(),
        req.getPassword()));

    userInternalService.createUser(new CreateUserReq(newUser.getId(), req.getName(), req.getAddress()));

    return new SignUpRes(
        "access token",
        "refresh token");
  }
}
