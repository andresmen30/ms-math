package ms.math.application.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;
import ms.math.application.response.ApiResponse;
import ms.math.domain.port.in.CallHistoryUseCase;
import ms.math.infraestructure.util.ResourcePathUtil;
import ms.math.mock.ApiResponseMock;

@Slf4j
@WebMvcTest(CallHistoryController.class)
@AutoConfigureMockMvc
class CallHistoryControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @MockitoBean
   private CallHistoryUseCase callHistoryUseCase;

   private ApiResponse apiResponseMockSuccess;

   private ApiResponse apiResponseMockEmpty;

   @BeforeEach
   void setup() {
      apiResponseMockSuccess = ApiResponseMock.createCallHistorySuccessMock();
      apiResponseMockEmpty = ApiResponseMock.createCallHistoryEmptyMock();
   }

   @Test
   void getHistoryOk() throws Exception {
      log.info("(calculateOk)");
      when(callHistoryUseCase.getCallHistory(any(LocalDateTime.class), any(LocalDateTime.class), any(Integer.class), any(Integer.class))).thenReturn(
            apiResponseMockSuccess);
      mockMvc
            .perform(get(StringUtils.join(ResourcePathUtil.BASE_PATH_API, ResourcePathUtil.HISTORY_ENDPOINT))
                  .contentType(MediaType.APPLICATION_JSON)
                  .queryParam("from", "2025-01-01T00:00:00")
                  .queryParam("to", "2025-04-01T00:00:00")
                  .queryParam("page", "0")
                  .queryParam("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message", is(HttpStatus.OK.name())))
            .andExpect(jsonPath("$.time", notNullValue()))
            .andExpect(jsonPath("$.data", notNullValue()))
            .andExpect(jsonPath("$.data.content", notNullValue()))
            .andExpect(jsonPath("$.data.content").isArray())
            .andExpect(jsonPath("$.data.content").value(hasSize(2)));
      log.info("(calculateOk) [[end]]");
   }

   @Test
   void getHistoryEmpty() throws Exception {
      log.info("(getHistoryEmpty)");
      when(callHistoryUseCase.getCallHistory(any(LocalDateTime.class), any(LocalDateTime.class), any(Integer.class), any(Integer.class))).thenReturn(
            apiResponseMockEmpty);
      mockMvc
            .perform(get(StringUtils.join(ResourcePathUtil.BASE_PATH_API, ResourcePathUtil.HISTORY_ENDPOINT))
                  .contentType(MediaType.APPLICATION_JSON)
                  .queryParam("from", "2025-02-01T00:00:00")
                  .queryParam("to", "2025-04-01T00:00:00")
                  .queryParam("page", "4")
                  .queryParam("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message", is(HttpStatus.OK.name())))
            .andExpect(jsonPath("$.time", notNullValue()))
            .andExpect(jsonPath("$.data", notNullValue()))
            .andExpect(jsonPath("$.data.content", notNullValue()))
            .andExpect(jsonPath("$.data.content").isArray())
            .andExpect(jsonPath("$.data.content").value(hasSize(NumberUtils.INTEGER_ZERO)));
      log.info("(getHistoryEmpty) [[end]]");
   }
}