package ms.math.infrastructure.client.adapter;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ms.math.domain.exception.PercentageServiceUnavailableException;
import ms.math.domain.port.out.PercentagePort;
import ms.math.infrastructure.client.MockClient;

@Component
@RequiredArgsConstructor
public class PercentageAdapter implements PercentagePort {

   private final MockClient mockClient;

   public BigDecimal getPercentage() throws PercentageServiceUnavailableException {
      return mockClient.getPercentage();
   }

}
