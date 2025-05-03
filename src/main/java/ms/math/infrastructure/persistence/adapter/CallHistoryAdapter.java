package ms.math.infrastructure.persistence.adapter;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ms.math.domain.model.CallHistoryModel;
import ms.math.domain.port.out.CallHistoryPort;
import ms.math.infrastructure.persistence.mapper.CallHistoryMapper;
import ms.math.infrastructure.persistence.repository.CallHistoryRepository;

@Component
@RequiredArgsConstructor
public class CallHistoryAdapter implements CallHistoryPort {

   private final CallHistoryRepository callHistoryRepository;

   private final CallHistoryMapper callHistoryMapper;

   @Override
   public void logCall(final CallHistoryModel callHistoryModel) {
      callHistoryRepository.save(callHistoryMapper.toEntity(callHistoryModel));
   }

   @Override
   public Page<CallHistoryModel> findPageable(final Pageable pageable) {
      return callHistoryRepository.findAll(pageable).map(callHistoryMapper::toModel);
   }

   @Override
   public Page<CallHistoryModel> findByTimestampBetweenPageable(final Pageable pageable, final LocalDateTime startDate, final LocalDateTime endDate) {
      return callHistoryRepository.findByTimestampBetween(pageable, startDate, endDate).map(callHistoryMapper::toModel);
   }

}
