package ms.math.domain.port.out;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ms.math.domain.model.CallHistoryModel;

public interface CallHistoryPort {

   void logCall(final CallHistoryModel callHistoryModel);

   Page<CallHistoryModel> findPageable(final Pageable pageable);

   Page<CallHistoryModel> findByTimestampBetweenPageable(final Pageable pageable, final LocalDateTime startDate, final LocalDateTime endDate);

}
