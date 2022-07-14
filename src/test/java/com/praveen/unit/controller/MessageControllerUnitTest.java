package com.praveen.unit.controller;

import com.google.gson.Gson;
import com.praveen.cms.CmsApplication;
import com.praveen.cms.api.entity.Message;
import com.praveen.cms.api.request.MessageAddRequest;
import com.praveen.cms.api.response.MessageAddResponse;
import com.praveen.cms.api.response.MessageDetailResponse;
import com.praveen.cms.api.response.MessageListResponse;
import com.praveen.cms.api.service.MessageService;
import com.praveen.integration.controller.MessageControllerIntegrityTest;
import com.praveen.integration.controller.Randomizer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {CmsApplication.class}
)
@AutoConfigureMockMvc
public class MessageControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MessageService messageService;

    private int id = Randomizer.generate(0, 10);

    @Test
    public void mustReturnListOnCallMessageList()
            throws Exception {
        //MessageControllerIntegrityTest messageControllerIntegrityTest = new MessageControllerIntegrityTest();
        MessageAddRequest messageAddRequest = MessageAddRequest.builder().email("user1").message("history 1").build();
        given(messageService.getLimitMessageList(messageAddRequest)).willReturn(mockMessagePage());
        Gson gson = new Gson();
        String json = gson.toJson(messageAddRequest);
        //String token = messageControllerIntegrityTest.getToken();
        mvc.perform(MockMvcRequestBuilders.post("http://localhost:8081/cms/message/getMessages)")
                        //.header("authorization", "Bearer " + token)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void mustReturnMessageOnCallSaveMessage()
            throws Exception {

        MessageAddRequest messageAddRequest = mockMessageDTO();
        given(messageService.addNewMessage(messageAddRequest)).willReturn(mockMessage());

        Gson gson = new Gson();
        String json = gson.toJson(messageAddRequest);

        mvc.perform(post("https://localhost:8081/cms/message/addMessage")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Motorola X1")));
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
        return MessageAddRequest.builder().email("user1").message("Russia Orthodox").build();
    }

    private MessageAddResponse mockMessage() {
        Message message = Message.builder().message("Powerful Smarthphone").build();
        MessageAddResponse messageAddResponse = new MessageAddResponse(message.getId());
        return messageAddResponse;
    }
}
