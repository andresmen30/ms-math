package ms.math.infrastructure.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ms.math.infrastructure.persistence.entity.CallHistory;

@Testcontainers
@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class CallHistoryRepositoryIT {

   @Container
   static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
         .withDatabaseName("testdb")
         .withUsername("test")
         .withPassword("test");

   @DynamicPropertySource
   static void configureProperties(DynamicPropertyRegistry registry) {
      registry.add("spring.datasource.url", postgres::getJdbcUrl);
      registry.add("spring.datasource.username", postgres::getUsername);
      registry.add("spring.datasource.password", postgres::getPassword);
   }

   @Autowired
   private CallHistoryRepository repository;

   @BeforeEach
   void setUp() {
      final CallHistory call = new CallHistory();
      call.setEndpoint("/math/add");
      call.setParameters("{\"a\":1,\"b\":2}");
      call.setRequest("GET");
      call.setResponse("{\"result\":3}");
      call.setStatus((short) 200);
      call.setTimestamp(LocalDateTime.now().minusDays(1));
      repository.save(call);
   }

   @Test
   void testFindByTimestampBetween() {
      final LocalDateTime start = LocalDateTime.now().minusDays(2);
      final LocalDateTime end = LocalDateTime.now();
      final var result = repository.findByTimestampBetween(Pageable.ofSize(10), start, end);
      assertThat(result).hasSize(1);
   }
}
