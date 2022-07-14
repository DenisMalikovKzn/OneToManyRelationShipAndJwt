package com.praveen.cms.api.dao.Impl;

import com.praveen.cms.api.dao.MessageDao;
import com.praveen.cms.api.entity.Message;
import com.praveen.cms.api.repository.MessageRepository;
import com.praveen.cms.api.request.MessageAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDaoImpl implements MessageDao {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Page<Message> getMessageList(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    @Override
    public Message addNewMessageDao(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Page<Message> getLimitMessageList(Pageable pageable, MessageAddRequest messageAddRequest) {
        String email = messageAddRequest.getEmail();
        return messageRepository.findLimitMessageList(pageable, email);
    }
}
