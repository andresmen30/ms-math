package ms.math.mock;

import java.math.BigDecimal;

import ms.math.application.request.PercentageRequest;

public class PercentageMock {

   public static PercentageRequest createMockOk() {
      return PercentageRequest.builder()
                              .num1(BigDecimal.valueOf(10.5))
                              .num2(BigDecimal.valueOf(20.3))
                              .build();
   }

   public static PercentageRequest createMockBadRequest() {
      return PercentageRequest.builder()
                              .num1(BigDecimal.valueOf(10.5))
                              .build();
   }
}
