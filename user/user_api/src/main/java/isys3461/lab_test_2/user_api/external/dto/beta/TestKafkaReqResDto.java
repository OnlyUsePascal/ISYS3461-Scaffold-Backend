package isys3461.lab_test_2.user_api.external.dto.beta;

public class TestKafkaReqResDto {
  static public record TestKafkaRequestReplyReq(
      Integer num1,
      Integer num2) {
  }

  static public record TestKafkaRequestReplyRes(
      Integer sum) {
  }

  // if u wanna return a list, wrap that list inside a class
  // instead of return the whole list
}
