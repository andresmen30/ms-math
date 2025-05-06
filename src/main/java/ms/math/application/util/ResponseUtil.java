package ms.math.application.util;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ms.math.application.response.ApiResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtil {

   public static ApiResponse response(final HttpStatus httpStatus, final Object data) {
      return ApiResponse.builder().message(httpStatus.name()).data(data).time(LocalDateTime.now()).build();
   }

}
