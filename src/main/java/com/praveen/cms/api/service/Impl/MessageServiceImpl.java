package com.praveen.cms.api.service.Impl;

import com.praveen.cms.api.dao.CustomerDao;
import com.praveen.cms.api.dao.MessageDao;
import com.praveen.cms.api.entity.Customer;
import com.praveen.cms.api.entity.Message;
import com.praveen.cms.api.request.MessageAddRequest;
import com.praveen.cms.api.response.CommonPaginationResponse;
import com.praveen.cms.api.response.MessageAddResponse;
import com.praveen.cms.api.response.MessageDetailResponse;
import com.praveen.cms.api.response.MessageListResponse;
import com.praveen.cms.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageDao messageDao;

    @Autowired
    CustomerDao customerDao;

    @Override
    public MessageListResponse getMessageList(int page, int limit) {
        if (page > 0)
            page = page - 1;
        Pageable pageable = PageRequest.of(page, limit);
        Page<Message> messagesPage = messageDao.getMessageList(pageable);
        List<Message> messages = messagesPage.getContent();
        List<MessageDetailResponse> messageList = new ArrayList<>();
        for (Message message : messages) {
            MessageDetailResponse messageDetailResponse = new MessageDetailResponse();
            messageDetailResponse.setMessageId(message.getId());
            messageDetailResponse.setMessage(message.getMessage());
            messageList.add(messageDetailResponse);
        }
        CommonPaginationResponse commonPaginationResponse = new CommonPaginationResponse();
        commonPaginationResponse.setTotalNumberOfPageAsPerGivenLimit(messagesPage.getTotalPages());
        MessageListResponse messageListResponse = new MessageListResponse();
        messageListResponse.setMessageResponseList(messageList);
        messageListResponse.setCommonPaginationResponse(commonPaginationResponse);
        return messageListResponse;
    }

    @Override
    public MessageListResponse getLimitMessageList(MessageAddRequest messageAddRequest) {
        String[] array = messageAddRequest.getMessage().split(" ");
        Pageable pageable = PageRequest.of(0, Integer.parseInt(array[1]));
        Page<Message> messagesPage = messageDao.getLimitMessageList(pageable, messageAddRequest);
        List<Message> messages = messagesPage.getContent();
        //List<Message> messages = messageDao.getLimitMessageList(messageAddRequest);
        List<MessageDetailResponse> messageList = new ArrayList<>();
        for (Message message : messages) {
            MessageDetailResponse messageDetailResponse = new MessageDetailResponse();
            messageDetailResponse.setMessageId(message.getId());
            messageDetailResponse.setMessage(message.getMessage());
            messageList.add(messageDetailResponse);
        }
        MessageListResponse messageListResponse = new MessageListResponse();
        messageListResponse.setMessageResponseList(messageList);

        return messageListResponse;
    }

    @Override
    public MessageAddResponse addNewMessage(MessageAddRequest messageAddRequest) {
        final Message message = new Message();
        message.setMessage(messageAddRequest.getMessage());
        Customer customer = customerDao.getCustomerByEmailDao(messageAddRequest.getEmail());
        customer.addMessageToUser(message);
        customer = customerDao.updateCustomer(customer);
        Optional<Message> optionalMessage = customer.getMessageList().stream().filter(p -> p.equals(message)).
                findFirst();
        Message message1 = optionalMessage.get();
        Long messageId = message1.getId();
        MessageAddResponse messageAddResponse = new MessageAddResponse(messageId);

        return messageAddResponse;
    }
}
