package com.praveen.cms.api.service;

import com.praveen.cms.api.request.MessageAddRequest;
import com.praveen.cms.api.response.MessageAddResponse;
import com.praveen.cms.api.response.MessageListResponse;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {
    public MessageListResponse getMessageList(int page, int limit);

    public MessageListResponse getLimitMessageList(MessageAddRequest messageAddRequest);

    public MessageAddResponse addNewMessage(MessageAddRequest messageAddRequest);   //MessageAddResponse
}
