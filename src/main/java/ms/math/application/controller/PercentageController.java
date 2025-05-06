package ms.math.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.math.application.request.PercentageRequest;
import ms.math.application.response.ApiResponse;
import ms.math.domain.port.in.PercentageUseCase;
import ms.math.infrastructure.util.ResourcePathUtil;

@Slf4j
@Tag(name = "Math", description = "Operaciones matemáticas con porcentaje dinámico")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ResourcePathUtil.BASE_PATH_API)
public class PercentageController {

   private final PercentageUseCase percentageUseCase;

   @Operation(summary = "Calcular suma con porcentaje dinámico", description = "Recibe dos números (`num1`, `num2`), los suma y aplica un "
         + "porcentaje adicional obtenido de un servicio externo."

   )
   @PostMapping(value = ResourcePathUtil.CALCULATE_ENDPOINT)
   public ApiResponse calculate(@RequestBody @Valid final PercentageRequest percentageRequest) {
      log.info("calculate: {}", percentageRequest);
      return percentageUseCase.calculatePercentage(percentageRequest.num1(), percentageRequest.num2());
   }
}
