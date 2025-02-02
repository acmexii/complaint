package complaint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import complaint.config.kafka.KafkaProcessor;
import complaint.domain.*;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessage;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierObjectMapper;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeTypeUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMessageVerifier
public class PayTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayTest.class);

    @Autowired
    private KafkaProcessor processor;

    @Autowired
    private MessageCollector messageCollector;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MessageVerifier<Message<?>> messageVerifier;

    @Autowired
    public FeeRepository repository;

    @Test
    @SuppressWarnings("unchecked")
    public void test0() {
        //given:
        Fee existingEntity = new Fee();

        existingEntity.setId(1L);
        existingEntity.setApplicationNumber("A001");
        existingEntity.setCharge(100L);
        existingEntity.setUserId("user001");

        repository.save(existingEntity);

        //when:

        try {
            Fee newEntity = new Fee();

            newEntity.setId(1L);
            newEntity.setApplicationNumber("A001");
            newEntity.setCharge(100L);
            newEntity.setUserId("user001");

            repository.save(newEntity);

            this.messageVerifier.send(
                    MessageBuilder
                        .withPayload(newEntity)
                        .setHeader(
                            MessageHeaders.CONTENT_TYPE,
                            MimeTypeUtils.APPLICATION_JSON
                        )
                        .build(),
                    "complaint"
                );

            Message<?> receivedMessage =
                this.messageVerifier.receive(
                        "complaint",
                        5000,
                        TimeUnit.MILLISECONDS
                    );
            assertNotNull("Resulted event must be published", receivedMessage);

            //then:
            String receivedPayload = (String) receivedMessage.getPayload();

            PaymentCompleted outputEvent = objectMapper.readValue(
                receivedPayload,
                PaymentCompleted.class
            );

            LOGGER.info("Response received: {}", outputEvent);

            assertEquals(outputEvent.getId(), 1L);
            assertEquals(outputEvent.getApplicationNumber(), "A001");
            assertEquals(outputEvent.getCharge(), 100L);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            assertTrue(e.getMessage(), false);
        }
    }
}
