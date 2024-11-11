
package complaint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeTypeUtils;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;

import javax.inject.Inject;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessage;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import complaint.config.kafka.KafkaProcessor;
import complaint.domain.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMessageVerifier
public class ApplyTest {

   private static final Logger LOGGER = LoggerFactory.getLogger(ApplyTest.class);
   
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
   public ComplainmentRepository repository;

   @Test
   @SuppressWarnings("unchecked")
   public void test0() {

      //given:  
      Complainment existingEntity = new Complainment();

      existingEntity.setId(1L);
      existingEntity.setComplainId("C1001");
      existingEntity.setUserId("U1001");
      existingEntity.setComplainDetail([object Object]);

      repository.save(existingEntity);

      //when:  

  
      
      try {


      Complainment newEntity = new Complainment();

         newEntity.setId(1L);
         newEntity.setComplainId("C1001");
         newEntity.setUserId("U1001");
         newEntity.setComplainDetail([object Object]);

      repository.save(newEntity);


   
           

         this.messageVerifier.send(MessageBuilder
                .withPayload(newEntity)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build(), "complaint");

         Message<?> receivedMessage = this.messageVerifier.receive("complaint", 5000, TimeUnit.MILLISECONDS);
         assertNotNull("Resulted event must be published", receivedMessage);

      //then:
         String receivedPayload = (String) receivedMessage.getPayload();

         ComplaintReceived outputEvent = objectMapper.readValue(receivedPayload, ComplaintReceived.class);


         LOGGER.info("Response received: {}", outputEvent);

         assertEquals(outputEvent.getId(), 1L);
         assertEquals(outputEvent.getComplainId(), "C1001");
         assertEquals(outputEvent.getUserId(), "U1001");
         assertEquals(outputEvent.getComplainDetail(), [object Object]);


      } catch (JsonProcessingException e) {
         e.printStackTrace();
         assertTrue(e.getMessage(), false);
      }

     
   }

}
