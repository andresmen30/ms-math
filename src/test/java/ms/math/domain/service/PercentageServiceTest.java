package ms.math.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ms.math.application.response.ApiResponse;
import ms.math.application.response.PercentageResponse;
import ms.math.domain.exception.PercentageServiceUnavailableException;

@SpringBootTest
class PercentageServiceTest {

   @Mock
   private CachedPercentageService cachedPercentageService;

   @InjectMocks
   private PercentageService percentageService;

   @ParameterizedTest
   @CsvSource({
         "20, 11, 40.30",
         "59, 44, 133.90",
         "70, 55, 162.50"
   })
   void calculatePercentage(final BigDecimal num1, final BigDecimal num2, final BigDecimal resultExpect)
         throws PercentageServiceUnavailableException {
      final BigDecimal cachedPercentage = BigDecimal.valueOf(30);
      when(cachedPercentageService.getCachedPercentage()).thenReturn(cachedPercentage);
      final ApiResponse response = percentageService.calculatePercentage(num1, num2);
      assertEquals(HttpStatus.OK.name(), response.message());
      final PercentageResponse data = (PercentageResponse) response.data();
      final BigDecimal expectedValue = resultExpect.setScale(2, RoundingMode.HALF_UP);
      assertEquals(expectedValue, data.value());
   }

   @Test
   void calculatePercentage_ServiceUnavailable() throws PercentageServiceUnavailableException {
      final BigDecimal num1 = BigDecimal.valueOf(100);
      final BigDecimal num2 = BigDecimal.valueOf(50);
      when(cachedPercentageService.getCachedPercentage()).thenThrow(new PercentageServiceUnavailableException("Service unavailable"));
      ApiResponse response = percentageService.calculatePercentage(num1, num2);
      assertEquals(HttpStatus.SERVICE_UNAVAILABLE.name(), response.message());
      assertEquals("Service unavailable", response.data());
   }
}