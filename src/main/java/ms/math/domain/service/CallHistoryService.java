package ms.math.domain.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ms.math.application.response.ApiResponse;
import ms.math.domain.model.CallHistoryModel;
import ms.math.domain.port.in.CallHistoryUseCase;
import ms.math.domain.port.out.CallHistoryPort;
import ms.math.infrastructure.util.ResponseUtil;


@Service
@RequiredArgsConstructor
public class CallHistoryService implements CallHistoryUseCase {

   private final CallHistoryPort callHistoryPort;

   @Async
   public void logCall(final CallHistoryModel callHistoryModel) {
      callHistoryPort.logCall(callHistoryModel);
   }

   public ApiResponse getCallHistory(final LocalDateTime startDate, final LocalDateTime endDate, final int page, final int size) {
      final Pageable pageable = PageRequest.of(page, size);
      return ResponseUtil.response(HttpStatus.OK, callHistoryPort.findPageable(pageable, startDate, endDate));

   }
}
