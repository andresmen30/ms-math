package ms.math.infraestructure.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CacheConfig {

   @Bean
   public Caffeine<Object, Object> caffeineConfig(@Value("${cache.expiration.minutes}") final int expirationMinutes,
         @Value("${cache.maximum.size}") final int maximumSize) {
      return Caffeine.newBuilder().expireAfterWrite(expirationMinutes, TimeUnit.MINUTES).maximumSize(maximumSize).recordStats();
   }

   @Bean
   public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
      final CaffeineCacheManager cacheManager = new CaffeineCacheManager();
      cacheManager.setCaffeine(caffeine);
      return cacheManager;
   }
}