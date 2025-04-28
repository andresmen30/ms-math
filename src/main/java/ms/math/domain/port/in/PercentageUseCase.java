package ms.math.domain.port.in;

import java.math.BigDecimal;

import ms.math.application.response.ApiResponse;

public interface PercentageUseCase {

   ApiResponse calculatePercentage(final BigDecimal num1, final BigDecimal num2);

}
