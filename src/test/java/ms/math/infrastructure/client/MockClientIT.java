package ms.math.infrastructure.client;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import ms.math.domain.exception.PercentageServiceUnavailableException;

@SpringBootTest
@EnableWireMock({ @ConfigureWireMock(port = 8888) })
@ActiveProfiles("test")
class MockClientIT {

   @Autowired
   private MockClient mockClient;

   @Test
   void testGetPercentage_Success() throws PercentageServiceUnavailableException {
      stubFor(get(urlEqualTo("/percentage")).willReturn(okJson("{\"value\": 0.15}")));
      final BigDecimal percentage = mockClient.getPercentage();
      assertEquals(new BigDecimal("0.15"), percentage);
   }

   @Test
   void testGetPercentage_ServiceUnavailable() {
      stubFor(get(urlEqualTo("/percentage")).willReturn(serverError()));
      assertThrows(PercentageServiceUnavailableException.class, () -> mockClient.getPercentage());
   }

}
