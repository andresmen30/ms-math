package ms.math.mock;

import org.springframework.http.HttpStatus;

import ms.math.application.response.ApiResponse;
import ms.math.application.util.ResponseUtil;

public class ApiResponseMock {

   public static ApiResponse createPercentageSuccessMock() {
      return ResponseUtil.response(HttpStatus.OK, PercentageMock.createMockOk());
   }

   public static ApiResponse createCallHistorySuccessMock() {
      return ResponseUtil.response(HttpStatus.OK, CallHistoryMock.createMockPageable());
   }

   public static ApiResponse createCallHistoryEmptyMock() {
      return ResponseUtil.response(HttpStatus.OK, CallHistoryMock.createMockPageableEmpty());
   }

}
