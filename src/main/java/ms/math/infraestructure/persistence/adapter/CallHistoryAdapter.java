package ms.math.infraestructure.persistence.adapter;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ms.math.domain.model.CallHistoryModel;
import ms.math.domain.port.out.CallHistoryPort;
import ms.math.infraestructure.persistence.entity.CallHistory;
import ms.math.infraestructure.persistence.mapper.CallHistoryMapper;
import ms.math.infraestructure.persistence.repository.CallHistoryRepository;

@Component
@RequiredArgsConstructor
public class CallHistoryAdapter implements CallHistoryPort {

   private final CallHistoryRepository callHistoryRepository;

   private final CallHistoryMapper callHistoryMapper;

   @Async
   public void logCall(final CallHistoryModel callHistoryModel) {
      final CallHistory callHistory = callHistoryMapper.toEntity(callHistoryModel);
      callHistoryRepository.save(callHistory);
   }

   @Override
   public Page<CallHistoryModel> findPageable(final Pageable pageable, final LocalDateTime startDate, final LocalDateTime endDate) {
      if (startDate == null || endDate == null) {
         return callHistoryRepository.findAll(pageable).map(callHistoryMapper::toModel);
      } else {
         return callHistoryRepository.findByTimestampBetween(startDate, endDate, pageable).map(callHistoryMapper::toModel);
      }
   }

}
