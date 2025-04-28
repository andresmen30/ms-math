package ms.math.application.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PercentageRequest(

      @NotNull @DecimalMin(value = "0.0", inclusive = false) @Schema(description = "Primer número para la operación de suma", example = "10.5",
            requiredMode = Schema.RequiredMode.REQUIRED) BigDecimal num1,

      @NotNull @DecimalMin(value = "0.0", inclusive = false) @Schema(description = "Segundo número para la operación de suma", example = "5.25",
            requiredMode = Schema.RequiredMode.REQUIRED) BigDecimal num2

) {

}
