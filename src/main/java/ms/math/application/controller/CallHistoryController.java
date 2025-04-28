package ms.math.application.controller;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.math.application.response.ApiResponse;
import ms.math.domain.port.in.CallHistoryUseCase;
import ms.math.infraestructure.util.ResourcePathUtil;

@Slf4j
@Tag(name = "History", description = "Historial de llamadas a la API")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ResourcePathUtil.BASE_PATH_API)
public class CallHistoryController {

   private final CallHistoryUseCase callHistoryUseCase;

   @Operation(
         summary = "Obtener historial de llamadas",
         description = "Devuelve el historial de llamadas registradas, filtrado opcionalmente por rango de fechas (`from`, `to`) y paginado (`page`, `size`)."
   )
   @GetMapping(value = ResourcePathUtil.HISTORY_ENDPOINT)
   public ApiResponse getHistory(
         @Parameter(
               description = "Fecha inicial para filtrar historial (opcional). Formato: yyyy-MM-dd'T'HH:mm:ss",
               in = ParameterIn.QUERY,
               example = "2024-04-01T00:00:00"
         )
         @RequestParam(required = false)
         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
         LocalDateTime from,

         @Parameter(
               description = "Fecha final para filtrar historial (opcional). Formato: yyyy-MM-dd'T'HH:mm:ss",
               in = ParameterIn.QUERY,
               example = "2024-04-28T23:59:59"
         )
         @RequestParam(required = false)
         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
         LocalDateTime to,

         @Parameter(
               description = "Número de página (empieza en 0)",
               in = ParameterIn.QUERY,
               example = "0"
         )
         @RequestParam(defaultValue = "0")
         int page,

         @Parameter(
               description = "Tamaño de página (cantidad de registros por página)",
               in = ParameterIn.QUERY,
               example = "10"
         )
         @RequestParam(defaultValue = "10")
         int size
   ) {
      log.info("Fetching history: from={}, to={}, page={}, size={}", from, to, page, size);
      return callHistoryUseCase.getCallHistory(from, to, page, size);
   }
}
