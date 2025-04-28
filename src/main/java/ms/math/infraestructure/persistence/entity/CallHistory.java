package ms.math.infraestructure.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = { "parameters", "response" })
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "call_history")
public class CallHistory {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String endpoint;

   @Column(columnDefinition = "TEXT")
   private String parameters;

   @Column(columnDefinition = "TEXT")
   private String request;

   @Column(columnDefinition = "TEXT")
   private String response;

   private Short status;

   private LocalDateTime timestamp;

}
