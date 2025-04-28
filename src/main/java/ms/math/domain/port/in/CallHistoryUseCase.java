package ms.math.domain.port.in;

import java.time.LocalDateTime;

import ms.math.application.response.ApiResponse;
import ms.math.domain.model.CallHistoryModel;

public interface CallHistoryUseCase {

   void logCall(final CallHistoryModel callHistoryModel);

   ApiResponse getCallHistory(final LocalDateTime startDate, final LocalDateTime endDate, final int page, final int size);

}
