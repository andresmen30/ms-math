package ms.math.infrastructure.util;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {

   private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

   public static String objectToJsonString(final Object object) {
      try {
         return OBJECT_MAPPER.writeValueAsString(object);
      } catch (JsonProcessingException e) {
         log.error(e.getMessage(), e);
      }
      return StringUtils.EMPTY;
   }

}
