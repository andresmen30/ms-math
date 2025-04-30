package ms.math.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.math.application.response.ApiResponse;
import ms.math.application.response.PercentageResponse;
import ms.math.domain.exception.PercentageServiceUnavailableException;
import ms.math.domain.port.in.PercentageUseCase;
import ms.math.infrastructure.util.ResponseUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class PercentageService implements PercentageUseCase {

   private final CachedPercentageService cachedPercentageService;

   public ApiResponse calculatePercentage(final BigDecimal num1, final BigDecimal num2) {
      try {
         final BigDecimal sum = num1.add(num2);
         final BigDecimal percentage = cachedPercentageService.getCachedPercentage();
         final BigDecimal increment = sum.multiply(percentage).divide(BigDecimal.valueOf(100), 2, RoundingMode.DOWN);
         final BigDecimal result = sum.add(increment);
         return ResponseUtil.response(HttpStatus.OK, PercentageResponse.builder().value(result).build());
      } catch (final PercentageServiceUnavailableException e) {
         return ResponseUtil.response(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
      }

   }

}

