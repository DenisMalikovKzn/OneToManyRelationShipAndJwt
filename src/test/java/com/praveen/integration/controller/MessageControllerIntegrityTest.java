package com.praveen.integration.controller;

import com.google.gson.Gson;
import com.praveen.cms.CmsApplication;
import com.praveen.cms.api.entity.Message;
import com.praveen.cms.api.request.MessageAddRequest;
import com.praveen.cms.api.response.BaseApiResponse;
import com.praveen.cms.api.response.MessageAddResponse;
import com.praveen.cms.api.response.MessageDetailResponse;
import com.praveen.cms.api.response.MessageListResponse;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {CmsApplication.class}
)
@AutoConfigureMockMvc
public class MessageControllerIntegrityTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private int id = Randomizer.generate(0, 10);

    @Test
    public void mustReturnListOnCallMessageList()
            throws Exception {

        MessageAddRequest messageAddRequest = MessageAddRequest.builder().email("user1").message("history 3").build();
        Gson gson = new Gson();
        String json = gson.toJson(messageAddRequest);
        HttpEntity<String> request = new HttpEntity<String>(json, getAuthenticatedHeaders());
        ResponseEntity<BaseApiResponse> response = restTemplate.exchange("http://localhost:" + port + "/cms/message/getMessages", HttpMethod.POST, request, BaseApiResponse.class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            System.out.println(response.getBody().getResponseData());
           Assert.assertEquals(3L, getCountItems(response.getBody().getResponseData().toString()));
        }

    }

    @Test
    public void mustReturnMessageIdOnCallSaveMessage()
            throws Exception {

        MessageAddRequest messageAddRequest = MessageAddRequest.builder().email("user1").message("16 message").build();
        Gson gson = new Gson();
        String json = gson.toJson(messageAddRequest);
        HttpEntity<String> request = new HttpEntity<String>(json, getAuthenticatedHeaders());
        ResponseEntity<BaseApiResponse> response = restTemplate.exchange("http://localhost:" + port + "/cms/message//addMessage", HttpMethod.POST, request, BaseApiResponse.class);
        try {
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                System.out.println(response.getBody().getResponseData());
                String str = response.getBody().getResponseData().toString();
                String strNum = str.substring(str.indexOf("="), str.indexOf("}"));
                System.out.println(strNum);
                assertTrue(Integer.parseInt(strNum.split("")[1]) > 0);
                Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
                assertNotNull(response.getBody().getResponseData());
            }
        } catch (Exception e) {
            throw new Exception(e);
        }

    }

    private MessageListResponse mockMessagePage() {
        Message message = Message.builder().message("1 message").build();
        List<MessageDetailResponse> messageList = new ArrayList<>();
        MessageDetailResponse messageDetailResponse = new MessageDetailResponse();
        messageDetailResponse.setMessageId(1L);
        messageDetailResponse.setMessage(message.getMessage());
        messageList.add(messageDetailResponse);
        MessageListResponse messageListResponse = new MessageListResponse();
        messageListResponse.setMessageResponseList(messageList);
        return messageListResponse;
    }

    private MessageAddRequest mockMessageDTO() {
        return MessageAddRequest.builder().email("q@mail.ru").message("Russia Orthodox").build();
    }

    private MessageAddResponse mockMessage() {
        Message message = Message.builder().message("Powerful Smarthphone").build();
        MessageAddResponse messageAddResponse = new MessageAddResponse(message.getId());
        return messageAddResponse;
    }

    public String getToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("email", "user1");
        personJsonObject.put("password", "user12345Q%");
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:"+port+"/cms/customer/signin", HttpMethod.POST, request, String.class);
        System.out.println(response.getHeaders().get("Authorization"));
        String[] token = response.getHeaders().get("Authorization").get(0).split(" ");
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return token[1];
        } else {
            return "токен не получен";
        }

    }

    private HttpHeaders getAuthenticatedHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + getToken());
        headers.add("Content-Type", "application/json");
        return headers;
    }

    private long getCountItems(String responseData) {
        String substr = "\\d\\s";
        Pattern p = Pattern.compile(substr);
        Matcher m = p.matcher(responseData);
        long counter = 0;
        while (m.find()) {
            counter++;
        }
        return counter;
    }
}

