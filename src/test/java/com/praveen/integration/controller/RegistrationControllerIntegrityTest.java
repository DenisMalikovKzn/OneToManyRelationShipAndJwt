package com.praveen.integration.controller;

import com.google.gson.Gson;
import com.praveen.cms.CmsApplication;
import com.praveen.cms.api.request.CustomerAddRequest;
import com.praveen.cms.api.response.BaseApiResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {CmsApplication.class}
)
@AutoConfigureMockMvc
public class RegistrationControllerIntegrityTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void mustRegisterUserWithSucess()
            throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(mockJsonBody(), headers);
        ResponseEntity<BaseApiResponse> response = restTemplate.exchange("http://localhost:" + port + "/cms/customer/signup", HttpMethod.POST, request, BaseApiResponse.class);
        System.out.println(response);          //String[] token = response.getHeaders().get("Authorization").get(0).split(" ");
        System.out.println(response.getBody().getResponseData());               //assertNotNull(token[0]);
        String str = response.getBody().getResponseData().toString();           //assertNotNull(token[1]);
        String strNum = str.substring(str.indexOf("="), str.indexOf("}"));

        assertTrue(Integer.parseInt(strNum.split("")[1]) > 0);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody().getResponseData());

        /*String payload = "{\r\n" +
                "  \"firstName\": \"ivan\",\r\n" +
                "  \"lastName\": \"Ivanov\",\r\n" +
                "  \"email\": \"Ivanov@mail.ru\",\r\n" +
                "  \"password\": \"Test@@123\"\r\n" +
                "}";

        request.header("Authorization","Bearer "+tokenGenerated)
                .header("Content-Type","application/json");*/
    }

    public String mockJsonBody() {
        Gson gson = new Gson();
        return gson.toJson(mockRegisterRequestDTO());
    }

    private CustomerAddRequest mockRegisterRequestDTO() {
        return CustomerAddRequest
                .builder()
                .firstName("Jhon")
                .lastName("Whick")
                .email("jhon@gmail.com")
                .password("123456")
                .build();
    }
}
