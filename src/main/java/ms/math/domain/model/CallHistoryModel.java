package ms.math.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CallHistoryModel {

   private Long id;

   private String endpoint;

   private String parameters;

   private String request;

   private String response;

   private Short status;

   private LocalDateTime timestamp;

}
