package ms.math.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import ms.math.application.response.ApiResponse;
import ms.math.domain.model.CallHistoryModel;
import ms.math.domain.port.out.CallHistoryPort;

@ExtendWith(MockitoExtension.class)
class CallHistoryServiceTest {

   @Mock
   private CallHistoryPort callHistoryPort;

   @InjectMocks
   private CallHistoryService callHistoryService;

   @Test
   void logCall_ShouldInvokePort() {
      final CallHistoryModel callHistoryModel = new CallHistoryModel();
      doNothing().when(callHistoryPort).logCall(callHistoryModel);

      callHistoryService.logCall(callHistoryModel);

      verify(callHistoryPort, times(1)).logCall(callHistoryModel);
   }

   @Test
   void getCallHistory_NoDates_ShouldReturnPagedResults() {
      final PageRequest pageable = PageRequest.of(0, 10);
      final Page<CallHistoryModel> mockPage = new PageImpl<>(Collections.emptyList());
      when(callHistoryPort.findPageable(pageable)).thenReturn(mockPage);

      final ApiResponse response = callHistoryService.getCallHistory(null, null, 0, 10);

      assertEquals(HttpStatus.OK.name(), response.message());
      assertEquals(mockPage, response.data());
      verify(callHistoryPort, times(1)).findPageable(pageable);
   }

   @Test
   void getCallHistory_WithDates_ShouldReturnFilteredResults() {
      final LocalDateTime startDate = LocalDateTime.now().minusDays(1);
      final LocalDateTime endDate = LocalDateTime.now();
      final PageRequest pageable = PageRequest.of(0, 10);
      final Page<CallHistoryModel> mockPage = new PageImpl<>(Collections.emptyList());
      when(callHistoryPort.findByTimestampBetweenPageable(pageable, startDate, endDate)).thenReturn(mockPage);

      final ApiResponse response = callHistoryService.getCallHistory(startDate, endDate, 0, 10);

      assertEquals(HttpStatus.OK.name(), response.message());
      assertEquals(mockPage, response.data());
      verify(callHistoryPort, times(1)).findByTimestampBetweenPageable(pageable, startDate, endDate);
   }
}