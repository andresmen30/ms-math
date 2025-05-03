package ms.math.infrastructure.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import ms.math.application.request.PercentageRequest;
import ms.math.infrastructure.util.json.JsonUtil;
import ms.math.mock.PercentageMock;

class JsonUtilTest {

   @Test
   void objectToJsonString_NullObject() {
      final String jsonString = JsonUtil.objectToJsonString(null);
      assertEquals(StringUtils.EMPTY, jsonString);
   }

   @Test
   void objectToJsonString_InvalidObject() {
      final Object invalidObject = new Object();
      final String jsonString = JsonUtil.objectToJsonString(invalidObject);
      assertTrue(jsonString.isEmpty());
   }

   @Test
   void objectToJsonString_PercentageRequest() {
      final PercentageRequest percentageRequest = PercentageMock.createMockOk();
      final String jsonString = JsonUtil.objectToJsonString(percentageRequest);
      assertEquals("{\"num1\":10.5,\"num2\":20.3}", jsonString);
   }

}