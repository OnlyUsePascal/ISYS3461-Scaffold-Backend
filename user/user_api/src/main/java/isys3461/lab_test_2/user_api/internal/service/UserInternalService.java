package isys3461.lab_test_2.user_api.internal.service;

import java.util.List;
import java.util.UUID;

import isys3461.lab_test_2.user_api.internal.dto.CreateUserDto.CreateUserReq;
import isys3461.lab_test_2.user_api.internal.dto.CreateUserDto.CreateUserRes;
import isys3461.lab_test_2.user_api.internal.dto.GetUserDto.GetUserRes;
import isys3461.lab_test_2.user_api.internal.dto.ListUsersDto.ListUsersRes;

public interface UserInternalService {
  List<ListUsersRes> listUsers();

  GetUserRes getUser(UUID id);

  CreateUserRes createUser(CreateUserReq req);
}
