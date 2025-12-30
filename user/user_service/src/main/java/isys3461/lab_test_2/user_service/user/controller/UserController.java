package isys3461.lab_test_2.user_service.user.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import isys3461.lab_test_2.user_api.internal.dto.GetUserDto.GetUserRes;
import isys3461.lab_test_2.user_api.internal.dto.ListUsersDto.ListUsersRes;
import isys3461.lab_test_2.user_api.internal.service.UserInternalService;
import isys3461.lab_test_2.user_service.common.http.GenericResponseDto;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/user")
@Slf4j
public class UserController {
  @Autowired
  private UserInternalService userInternalService;

  @Value("${spring.application.name}")
  private String appName;

  @GetMapping("/greeting")
  public ResponseEntity<GenericResponseDto<String>> greeting() {
    return GenericResponseDto.getGenericResponseDto("hello from service + " + appName);
  }

  @GetMapping("/test/err")
  public ResponseEntity<String> testErr() {
    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bruh no");
  }

  @PostMapping("/")
  public ResponseEntity<GenericResponseDto<List<ListUsersRes>>> listUsers() {
    var res = userInternalService.listUsers();
    return GenericResponseDto.getGenericResponseDto(res);
  }

  @PostMapping("/{id}")
  public ResponseEntity<GenericResponseDto<GetUserRes>> getUser(@PathVariable UUID id) {
    var res = userInternalService.getUser(id);
    return GenericResponseDto.getGenericResponseDto(res);
  }

}
