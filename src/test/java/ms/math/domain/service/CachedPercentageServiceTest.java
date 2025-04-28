package ms.math.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import ms.math.domain.exception.PercentageServiceUnavailableException;
import ms.math.domain.port.out.PercentagePort;

@SpringBootTest
class CachedPercentageServiceTest {

   @Mock
   private PercentagePort percentagePort;

   @Mock
   private CacheManager cacheManager;

   @Mock
   private Cache cache;

   @InjectMocks
   private CachedPercentageService cachedPercentageService;

   @Test
   void getCachedPercentage_Success() throws PercentageServiceUnavailableException {
      final BigDecimal expectedPercentage = BigDecimal.valueOf(30);
      when(percentagePort.getPercentage()).thenReturn(expectedPercentage);
      final BigDecimal result = cachedPercentageService.getCachedPercentage();

      assertEquals(expectedPercentage, result);
      verify(percentagePort, times(1)).getPercentage();
   }

   @Test
   void getCachedPercentage_UsesCachedValue() throws PercentageServiceUnavailableException {
      final BigDecimal cachedValue = BigDecimal.valueOf(20);
      when(cacheManager.getCache("currentPercentage")).thenReturn(cache);
      when(cache.get("percentage", BigDecimal.class)).thenReturn(cachedValue);
      doThrow(new PercentageServiceUnavailableException("Service unavailable")).when(percentagePort).getPercentage();
      final BigDecimal result = cachedPercentageService.getCachedPercentage();
      assertEquals(cachedValue, result);
      verify(percentagePort, times(1)).getPercentage();
   }

   @Test
   void getCachedPercentage_NoCachedValue_ThrowsException() throws PercentageServiceUnavailableException {
      when(cacheManager.getCache("currentPercentage")).thenReturn(cache);
      when(cache.get("percentage", BigDecimal.class)).thenReturn(null);
      doThrow(new PercentageServiceUnavailableException("Service unavailable")).when(percentagePort).getPercentage();
      assertThrows(PercentageServiceUnavailableException.class, () -> cachedPercentageService.getCachedPercentage());
      verify(percentagePort, times(1)).getPercentage();
   }

}