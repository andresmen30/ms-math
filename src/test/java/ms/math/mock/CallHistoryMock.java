package ms.math.mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import ms.math.domain.model.CallHistoryModel;
import ms.math.infrastructure.util.ResourcePathUtil;

public class CallHistoryMock {

   public static Page<CallHistoryModel> createMockPageable() {
      final List<CallHistoryModel> callHistoryList = List.of(CallHistoryModel
            .builder()
            .id(1L)
            .endpoint(ResourcePathUtil.CALCULATE_ENDPOINT)
            .parameters("num1=10.5&num2=20.3")
            .request("{\"num1\":10.5,\"num2\":20.3}")
            .response("{\"value\":30.8}")
            .status((short) 200)
            .timestamp(LocalDateTime.now())
            .build(), CallHistoryModel
            .builder()
            .id(2L)
            .endpoint(ResourcePathUtil.HISTORY_ENDPOINT)
            .parameters("startDate=2023-01-01&endDate=2023-01-31")
            .request("{\"startDate\":\"2023-01-01\",\"endDate\":\"2023-01-31\"}")
            .response("[{\"id\":1,\"endpoint\":\"/api/calculate\"}]")
            .status((short) 200)
            .timestamp(LocalDateTime.now())
            .build());
      final Pageable pageable = PageRequest.of(0, 10);
      return new PageImpl<>(callHistoryList, pageable, callHistoryList.size());
   }

   public static Page<CallHistoryModel> createMockPageableEmpty() {
      final Pageable pageable = PageRequest.of(0, 10);
      return new PageImpl<>(new ArrayList<>(), pageable, 0);
   }

}
