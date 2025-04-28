package ms.math.application.response;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record PercentageResponse(BigDecimal value) {

}
