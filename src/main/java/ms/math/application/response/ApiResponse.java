package ms.math.application.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public record ApiResponse(

      @Schema(description = "Mensaje descriptivo de la respuesta", example = "Operación realizada con éxito.") String message,

      @Schema(description = "Fecha y hora en que se generó la respuesta", example = "2025-04-28 21:30:00") @JsonFormat(shape =
            JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime time,

      @Schema(description = "Datos adicionales de la respuesta", example = "{\"key\": \"value\"}") Object data

) {

}
