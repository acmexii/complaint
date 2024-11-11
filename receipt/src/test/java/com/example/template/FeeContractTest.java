package com.example.template;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import complaint.ComplainmentApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = complaint.ComplainmentApplication.class)
@AutoConfigureMockMvc
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, 
                         ids = "complaint:fee:+:stubs:8090")
@ActiveProfiles("test")                      
public class FeeContractTest {

   @Autowired
   MockMvc mockMvc;

    @Test
    public void getFee_stub_test() throws Exception {

        MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders.get("/complainment/validateFee/1")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

        String responseString = result.getResponse().getContentAsString();
        DocumentContext parsedJson = JsonPath.parse(responseString);
        // and:
        // examples
        Assertions.assertThat(parsedJson.read("$.id", Long.class)).isGreaterThan(0L);
        Assertions.assertThat(parsedJson.read("$.applicationnumber", String.class)).matches("[\S\s]+");
        Assertions.assertThat(parsedJson.read("$.charge", Long.class)).isGreaterThan(0L);
    }

}
