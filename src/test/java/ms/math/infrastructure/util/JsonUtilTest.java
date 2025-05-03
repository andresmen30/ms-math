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

   @Test
   void minifyJson_NullString() {
      final String result = JsonUtil.minifyJson(null);
      assertEquals(StringUtils.EMPTY, result, "Minifying a null string should return an empty string.");
   }

   @Test
   void minifyJson_BlankString() {
      final String result = JsonUtil.minifyJson("   ");
      assertEquals(StringUtils.EMPTY, result, "Minifying a blank string should return an empty string.");
   }

   @Test
   void minifyJson_InvalidJson() {
      final String invalidJson = "{invalid: json}";
      final String result = JsonUtil.minifyJson(invalidJson);
      assertEquals(StringUtils.EMPTY, result, "Minifying an invalid JSON string should return an empty string.");
   }

   @Test
   void minifyJson_ValidJson() {
      final String validJson = "{\n  \"key1\": \"value1\",\n  \"key2\": \"value2\"\n}";
      final String expectedMinifiedJson = "{\"key1\":\"value1\",\"key2\":\"value2\"}";
      final String result = JsonUtil.minifyJson(validJson);
      assertEquals(expectedMinifiedJson, result, "Minifying a valid JSON string should return a minified JSON string.");
   }

   @Test
   void minifyJson_EmptyJsonObject() {
      final String emptyJson = "{}";
      final String result = JsonUtil.minifyJson(emptyJson);
      assertEquals("{}", result, "Minifying an empty JSON object should return the same JSON object.");
   }

}