package ms.math.infrastructure.util.json;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {

   private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

   public static String objectToJsonString(final Object object) {
      if (object == null) {
         log.warn("Attempted to serialize a null object to JSON.");
         return StringUtils.EMPTY;
      }
      try {
         return OBJECT_MAPPER.writeValueAsString(object);
      } catch (JsonProcessingException e) {
         log.error("Failed to serialize object to JSON. Error: {}", e.getMessage(), e);
         return StringUtils.EMPTY;
      }
   }

   public static String minifyJson(final String jsonString) {
      if (StringUtils.isBlank(jsonString)) {
         log.warn("Attempted to minify a blank or null JSON string.");
         return StringUtils.EMPTY;
      }
      try {
         final JsonNode node = OBJECT_MAPPER.readTree(jsonString);
         return objectToJsonString(node);
      } catch (JsonProcessingException e) {
         log.error("Failed to minify JSON string. Error: {}", e.getMessage(), e);
         return StringUtils.EMPTY;
      }
   }
}