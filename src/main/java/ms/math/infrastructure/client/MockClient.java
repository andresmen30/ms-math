package ms.math.infrastructure.client;

import java.math.BigDecimal;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import ms.math.domain.exception.PercentageServiceUnavailableException;
import ms.math.infrastructure.client.dto.PercentageDto;
import reactor.util.retry.Retry;

@Component
public class MockClient {

   @Value("${rest.client.mock.url}")
   private String baseUrl;

   @Value("${rest.client.mock.endpoint.percentage}")
   private String percentageUri;

   @Value("${rest.client.mock.timeout}")
   private int timeout;

   public WebClient getWebClient() {
      return WebClient.builder().baseUrl(this.baseUrl).defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).build();
   }

   public BigDecimal getPercentage() throws PercentageServiceUnavailableException {
      try {
         return getWebClient()
               .get()
               .uri(percentageUri)
               .retrieve()
               .bodyToMono(PercentageDto.class)
               .timeout(Duration.ofMillis(timeout))
               .retryWhen(Retry.backoff(3, Duration.ofMillis(100)))
               .map(PercentageDto::getValue)
               .block();
      } catch (Exception e) {
         throw new PercentageServiceUnavailableException("Failed to retrieve percentage from external service.");
      }
   }

}
