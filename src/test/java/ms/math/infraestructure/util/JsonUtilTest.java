package ms.math.infraestructure.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ms.math.application.request.PercentageRequest;
import ms.math.mock.PercentageMock;

class JsonUtilTest {

   @Test
   void objectToJsonString_NullObject() {
      String jsonString = JsonUtil.objectToJsonString(null);
      assertEquals("null", jsonString);
   }

   @Test
   void objectToJsonString_InvalidObject() {
      Object invalidObject = new Object() {

         public Object getSelfReference() {
            return selfReference;
         }

         private final Object selfReference = this;
      };
      String jsonString = JsonUtil.objectToJsonString(invalidObject);
      assertTrue(jsonString.isEmpty());
   }

   @Test
   void objectToJsonString_PercentageRequest() {
      final PercentageRequest percentageRequest = PercentageMock.createMockOk();
      final String jsonString = JsonUtil.objectToJsonString(percentageRequest);
      assertEquals("{\"num1\":10.5,\"num2\":20.3}", jsonString);
   }



}