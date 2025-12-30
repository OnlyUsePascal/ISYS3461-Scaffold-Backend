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
import isys3461.lab_test_2.user_service.common.util.JwtService;
import isys3461.lab_test_2.user_service.common.util.dto.CreateTokenDto.CreateJwsTokenReq;
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

  @Autowired
  private JwtService jwtService;

  @Override
  public SignInRes signIn(SignInReq req) {
    var user = authRepo
        .findByUsername(req.username())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "user not found with username" + req.username()));

    // password hash
    if (!user.getPassword().equals(req.password())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "wrong password");
    }

    // jwt
    try {
      var token = jwtService.createJwsToken(
          new CreateJwsTokenReq(user.getId().toString(), user.getUsername(), "ADMIN"));

      return new SignInRes(token);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
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

    // jwt
    try {
      var token = jwtService.createJwsToken(
          new CreateJwsTokenReq(newUser.getId().toString(), newUser.getUsername(), "ADMIN"));

      return new SignUpRes(token);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
