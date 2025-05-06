package ms.math.application.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import lombok.extern.slf4j.Slf4j;
import ms.math.application.response.ApiResponse;
import ms.math.domain.port.in.PercentageUseCase;
import ms.math.infrastructure.exception.ExceptionHelper;
import ms.math.infrastructure.util.ResourcePathUtil;
import ms.math.infrastructure.util.json.JsonUtil;
import ms.math.mock.ApiResponseMock;
import ms.math.mock.PercentageMock;

@Slf4j
@ExtendWith(MockitoExtension.class)
class PercentageControllerTest {

   private MockMvc mockMvc;

   @InjectMocks
   private PercentageController percentageController;

   @InjectMocks
   private ExceptionHelper exceptionHelper;

   @Mock
   private PercentageUseCase percentageUseCase;

   private ApiResponse apiResponseMock;

   @BeforeEach
   void setup() {
      apiResponseMock = ApiResponseMock.createPercentageSuccessMock();
      mockMvc = MockMvcBuilders.standaloneSetup(percentageController, exceptionHelper).build();

   }

   @Test
   void calculateOk() throws Exception {
      log.info("(calculateOk)");
      when(percentageUseCase.calculatePercentage(any(BigDecimal.class), any(BigDecimal.class))).thenReturn(apiResponseMock);
      mockMvc
            .perform(post(StringUtils.join(ResourcePathUtil.BASE_PATH_API, ResourcePathUtil.CALCULATE_ENDPOINT))
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(JsonUtil.objectToJsonString(PercentageMock.createMockOk())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message", is(HttpStatus.OK.name())))
            .andExpect(jsonPath("$.time", notNullValue()))
            .andExpect(jsonPath("$.data", notNullValue()));
      log.info("(calculateOk) [[end]]");
   }

   @Test
   void calculateBadRequest() throws Exception {
      log.info("(calculateBadRequest)");
      mockMvc
            .perform(post(StringUtils.join(ResourcePathUtil.BASE_PATH_API, ResourcePathUtil.CALCULATE_ENDPOINT))
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(JsonUtil.objectToJsonString(PercentageMock.createMockBadRequest())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", is(HttpStatus.BAD_REQUEST.name())))
            .andExpect(jsonPath("$.time", notNullValue()))
            .andExpect(jsonPath("$.data", containsString("num2")));
      log.info("(calculateBadRequest) [[end]]");
   }

   @Test
   void calculateWithoutRequest() throws Exception {
      log.info("(calculateWithoutRequest)");
      mockMvc
            .perform(
                  post(StringUtils.join(ResourcePathUtil.BASE_PATH_API, ResourcePathUtil.CALCULATE_ENDPOINT)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", is(HttpStatus.BAD_REQUEST.name())))
            .andExpect(jsonPath("$.time", notNullValue()))
            .andExpect(jsonPath("$.data", containsString("Required request")));
      log.info("(calculateWithoutRequest) [[end]]");
   }

}