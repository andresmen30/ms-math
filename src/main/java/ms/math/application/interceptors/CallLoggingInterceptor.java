package ms.math.application.interceptors;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ms.math.application.response.interceptor.CachedBodyHttpServletResponse;
import ms.math.domain.model.CallHistoryModel;
import ms.math.domain.port.in.CallHistoryUseCase;

@Component
@RequiredArgsConstructor
public class CallLoggingInterceptor implements HandlerInterceptor {

   private final CallHistoryUseCase callHistoryUseCase;

   @Override
   public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
      final CallHistoryModel callHistoryModel = CallHistoryModel
            .builder()
            .endpoint(request.getRequestURI())
            .parameters(request.getQueryString())
            .request(getRequestBody(request))
            .response(getResponseBody(response))
            .status((short) response.getStatus())
            .timestamp(LocalDateTime.now())
            .build();

      callHistoryUseCase.logCall(callHistoryModel);
   }

   private String getRequestBody(final HttpServletRequest httpServletRequest) {
      final ContentCachingRequestWrapper cachedRequest = (ContentCachingRequestWrapper) httpServletRequest;
      final byte[] content = cachedRequest.getContentAsByteArray();
      return content.length > NumberUtils.INTEGER_ZERO ? StringUtils.trim(new String(content, StandardCharsets.UTF_8)) : null;
   }

   private String getResponseBody(final HttpServletResponse httpServletResponse) {
      final CachedBodyHttpServletResponse cachedResponse = (CachedBodyHttpServletResponse) httpServletResponse;
      return StringUtils.trim(new String(cachedResponse.getCachedContent(), StandardCharsets.UTF_8));
   }

}
