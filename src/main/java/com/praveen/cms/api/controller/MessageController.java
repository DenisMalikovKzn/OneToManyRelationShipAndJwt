package com.praveen.cms.api.controller;

import com.praveen.cms.api.constant.AppConstants;
import com.praveen.cms.api.constant.RestMappingConstants;
import com.praveen.cms.api.request.MessageAddRequest;
import com.praveen.cms.api.response.BaseApiResponse;
import com.praveen.cms.api.response.MessageAddResponse;
import com.praveen.cms.api.response.MessageListResponse;
import com.praveen.cms.api.response.ResponseBuilder;
import com.praveen.cms.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(RestMappingConstants.MessageRequestUri.MESSAGE_BASE_URL)
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping(path = RestMappingConstants.MessageRequestUri.GET_MESSAGE_LIST)
    public ResponseEntity<BaseApiResponse> getMessageList(
            @RequestParam(value = AppConstants.Common.PAGE_NUMBER, defaultValue = "0") int page,
            @RequestParam(value = AppConstants.Common.PAGE_LIMIT, defaultValue = AppConstants.Common.PAGE_LIMIT_VALUE) int limit
    ) {
        MessageListResponse messageResponseList = messageService.getMessageList(page, limit);
        BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(messageResponseList);
        return new ResponseEntity<>(baseApiResponse, HttpStatus.OK);
    }

    @PostMapping(path = RestMappingConstants.MessageRequestUri.GET_MESSAGE_LIST)
    public ResponseEntity<BaseApiResponse> getLimitMessageList(
            @Valid @RequestBody(required = true) MessageAddRequest messageRequest){

        MessageListResponse messageResponseList = messageService.getLimitMessageList(messageRequest);
        BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(messageResponseList);

        return new ResponseEntity<>(baseApiResponse, HttpStatus.OK);
    }

    @PostMapping(path = RestMappingConstants.MessageRequestUri.ADD_MESSAGE)
    public ResponseEntity<BaseApiResponse> addMessage(
            @Valid @RequestBody(required = true) MessageAddRequest messageRequest) {
        MessageAddResponse messageAddResponse = messageService.addNewMessage(messageRequest);  // MessageAddResponse
        BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(messageAddResponse);
        return new ResponseEntity<>(baseApiResponse, HttpStatus.OK);
    }
}
