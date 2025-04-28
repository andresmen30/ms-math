package ms.math.domain.port.out;

import java.math.BigDecimal;

import ms.math.domain.exception.PercentageServiceUnavailableException;

public interface PercentagePort {

   BigDecimal getPercentage() throws PercentageServiceUnavailableException;
}
