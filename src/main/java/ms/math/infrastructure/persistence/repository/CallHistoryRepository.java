package ms.math.infrastructure.persistence.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ms.math.infrastructure.persistence.entity.CallHistory;

public interface CallHistoryRepository extends JpaRepository<CallHistory, Long> {

   Page<CallHistory> findByTimestampBetween(final LocalDateTime startDate, final LocalDateTime endDate, final Pageable pageable);

}
