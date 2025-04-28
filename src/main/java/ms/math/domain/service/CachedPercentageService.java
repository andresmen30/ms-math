package ms.math.domain.service;

import java.math.BigDecimal;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ms.math.domain.exception.PercentageServiceUnavailableException;
import ms.math.domain.port.out.PercentagePort;

@Service
@RequiredArgsConstructor
public class CachedPercentageService {

   private final PercentagePort percentagePort;

   private final CacheManager cacheManager;

   @Cacheable(value = "currentPercentage", key = "'percentage'")
   public BigDecimal getCachedPercentage() throws PercentageServiceUnavailableException {
      try {
         return percentagePort.getPercentage();
      } catch (PercentageServiceUnavailableException exception) {
         final BigDecimal cachedValue = getLastCachedValue();
         if (cachedValue != null) {
            return cachedValue;
         }
         throw new PercentageServiceUnavailableException("No cached percentage available.");
      }

   }

   private BigDecimal getLastCachedValue() {
      final Cache cache = cacheManager.getCache("currentPercentage");
      if (cache != null) {
         return cache.get("percentage", BigDecimal.class);
      }
      return null;
   }

}
