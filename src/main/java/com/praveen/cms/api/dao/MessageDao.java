package com.praveen.cms.api.dao;

import com.praveen.cms.api.entity.Message;
import com.praveen.cms.api.request.MessageAddRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface MessageDao {
    public Page<Message> getMessageList(Pageable pageable);

    //public Optional<Message> getProductById(Long messageId);

    public Message addNewMessageDao(Message message);

    public Page<Message> getLimitMessageList(Pageable pageable, MessageAddRequest messageAddRequest);
}
